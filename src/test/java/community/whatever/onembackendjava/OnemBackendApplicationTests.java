package community.whatever.onembackendjava;

import community.whatever.onembackendjava.application.Base62Converter;
import community.whatever.onembackendjava.application.RandomKeyGenerator;
import community.whatever.onembackendjava.application.RandomKeyGeneratorImpl;
import community.whatever.onembackendjava.application.UrlShortenServiceImpl;
import community.whatever.onembackendjava.exception.BlockDomainException;
import community.whatever.onembackendjava.exception.CustomDuplicateKeyException;
import community.whatever.onembackendjava.infrastructure.UrlShortenRepository;
import community.whatever.onembackendjava.presentation.RequestDto;
import community.whatever.onembackendjava.utils.BlockDomainProvider;
import io.micrometer.common.util.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.URI;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OnemBackendApplicationTests {

    private UrlShortenRepository urlShortenRepository;
    private RandomKeyGenerator randomKeyGenerator;
    private UrlShortenServiceImpl urlShortenService;
    private Base62Converter base62Converter;
    private BlockDomainProvider blockDomainProvider;

    @BeforeEach
    void setUp() {
        urlShortenRepository = Mockito.mock(UrlShortenRepository.class);
        base62Converter = Mockito.mock(Base62Converter.class);
        randomKeyGenerator = Mockito.mock(RandomKeyGenerator.class);
        urlShortenService = Mockito.mock(UrlShortenServiceImpl.class);
        blockDomainProvider = Mockito.mock(BlockDomainProvider.class);
    }

    @Test
    @DisplayName("shortUrl 이 존재하지 않는 경우, NoSuchElementException 발생")
    void testGetOriginalUrl_WhenShortUrlNotFound_ShouldThrowException() {
        urlShortenService = new UrlShortenServiceImpl(urlShortenRepository, randomKeyGenerator, blockDomainProvider);

        String shortUrl = "1234";
        Mockito.when(urlShortenRepository.findOriginalUrlByKey(shortUrl)).thenReturn(Optional.empty());

        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> {
            urlShortenService.getOriginalUrl(shortUrl);
        });

        assertEquals("No value present", exception.getMessage());
    }

    @Test
    @DisplayName("ShortUrl 이 존재하는 경우, OriginalUrl 반환")
    void testGetOriginalUrl_WhenShortUrlFound_ShouldReturnOriginalUrl() {
        urlShortenService = new UrlShortenServiceImpl(urlShortenRepository, randomKeyGenerator, blockDomainProvider);

        String shortUrl = "1234";
        String originalUrl = "http://www.google.com";
        Mockito.when(urlShortenRepository.findOriginalUrlByKey(shortUrl)).thenReturn(Optional.of(originalUrl));

        String result = urlShortenService.getOriginalUrl(shortUrl);

        assertEquals(originalUrl, result);
    }

    @Test
    @DisplayName("중복된 key 생성 . CustomDuplicateKeyException 발생")
    void testCreateShortUrl_existsCase_throw_AlreadyExistsKeyException() {
        urlShortenService = new UrlShortenServiceImpl(urlShortenRepository, randomKeyGenerator, blockDomainProvider);

        String originalUrl = "https://www.google.com";
        String randomKey = "z93jD80";

        Mockito.when(randomKeyGenerator.getRandomKey()).thenReturn(randomKey);
        Mockito.when(urlShortenRepository.existsByKey(randomKey)).thenReturn(true);

        RequestDto.CreateShortenUrl requestDto = new RequestDto.CreateShortenUrl(originalUrl);

        CustomDuplicateKeyException exception = assertThrows(CustomDuplicateKeyException.class, () -> {
            urlShortenService.createShortUrl(requestDto);
        });

        assertEquals("key: " + randomKey + " already exists", exception.getMessage());
    }

    @Test
    @DisplayName("차단 된 도메인 요청 시 BlockDomainException 발생")
    void testCreateShortUrl_blockDomain_throw_BlockDomainException() {
        urlShortenService = new UrlShortenServiceImpl(urlShortenRepository, randomKeyGenerator, blockDomainProvider);

        String originalUrl = "http://localhost";
        String randomKey = "A92kld8";

        Mockito.when(randomKeyGenerator.getRandomKey()).thenReturn(randomKey);
        Mockito.when(blockDomainProvider.isBlocked(URI.create(originalUrl))).thenReturn(true);

        RequestDto.CreateShortenUrl requestDto = new RequestDto.CreateShortenUrl(originalUrl);

        BlockDomainException exception = assertThrows(BlockDomainException.class, () -> {
            urlShortenService.createShortUrl(requestDto);
        });

        assertEquals("this domain is blocked: " + originalUrl, exception.getMessage());
    }

    @Test
    @DisplayName("Base62 인코딩, 디코딩 테스트")
    void testBase62EncodingAndDecoding() {
        base62Converter = new Base62Converter();

        long value = System.currentTimeMillis();
        long[] testValues = {value, value + 1, value + 10, value + 100, value + 1000, value + 10000, value + 100000, value + 1000000};

        for (long testValue : testValues) {
            String encoded = base62Converter.encode(testValue);
            long decoded = base62Converter.decode(encoded);

            System.out.printf("original: %d, encoded: %s, decoded: %d%n", testValue, encoded, decoded);
            assertEquals(testValue, decoded);
        }
    }

    @Test
    @DisplayName("shortUrl 이 7자리로 생성되는지 확인")
    void testCreateShortUrl_always_7digit() {
        randomKeyGenerator = new RandomKeyGeneratorImpl(new Base62Converter());
        urlShortenService = new UrlShortenServiceImpl(urlShortenRepository, randomKeyGenerator, blockDomainProvider);

        String[] originalUrls = {"https://www.google.com", "https://www.naver.com", "https://www.daum.net"};

        for (String originalUrl : originalUrls) {
            RequestDto.CreateShortenUrl request = new RequestDto.CreateShortenUrl(originalUrl);
            String result = urlShortenService.createShortUrl(request);
            assertEquals(7, result.length());
        }
    }

    @Test
    @DisplayName("shortUrl 이 8자리가 되는 시점")
    void testCreateShortUrl_8digit() {
        base62Converter = new Base62Converter();

        long value = 3521614606207L; // Tue Aug 05 2081 10:16:46 GMT+0000
        String encoded7digit = base62Converter.encode(value);
        String encoded8digit = base62Converter.encode(value + 1);

        System.out.printf("original: %d, encoded: %s, length: %d%n", value, encoded7digit, encoded7digit.length());
        System.out.printf("original: %d, encoded: %s, length: %d%n", value, encoded8digit, encoded8digit.length());
        assertEquals(7, encoded7digit.length());
        assertEquals(8, encoded8digit.length());
    }

    @Test
    @DisplayName("StringUtils 테스트")
    void testStringUtils() {
        assertTrue(StringUtils.isBlank(null));
        assertTrue(StringUtils.isBlank(""));
        assertTrue(StringUtils.isBlank("     "));
    }

    @Test
    @DisplayName("LocalDateTime <-> Instant 변환 테스트")
    void testTimeClassConvert() {
        long epochSeconds = System.currentTimeMillis() / 1000;
        Instant fixedInstant = Instant.ofEpochSecond(epochSeconds);

        ZoneId zoneId = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(fixedInstant, zoneId);

        Instant convertInstant = localDateTime.atZone(zoneId).toInstant();

        // 출력 확인
        System.out.println("epochSeconds: " + fixedInstant);
        System.out.println("localDateTime: " + localDateTime);
        System.out.println("fixedInstant: " + fixedInstant);
        System.out.println("convertInstant: " + convertInstant);

        assertEquals(fixedInstant, convertInstant, "변환된 Instant가 원본과 다름");
    }

}
