package community.whatever.onembackendjava;

import community.whatever.onembackendjava.application.Base62Converter;
import community.whatever.onembackendjava.application.RandomKeyGenerator;
import community.whatever.onembackendjava.application.RandomKeyGeneratorImpl;
import community.whatever.onembackendjava.application.UrlShortenServiceImpl;
import community.whatever.onembackendjava.repository.UrlShortenRepository;
import io.micrometer.common.util.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.URI;
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

    // Repository 에서 ShortUrl 을 찾지 못한 경우 NoSuchElementException 이 발생하는지 테스트
    @Test
    void testGetOriginalUrl_WhenShortUrlNotFound_ShouldThrowException() {
        urlShortenService = new UrlShortenServiceImpl(urlShortenRepository, randomKeyGenerator, blockDomainProvider);

        String shortUrl = "1234";
        Mockito.when(urlShortenRepository.findOriginUrlByKey(shortUrl)).thenReturn(Optional.empty());

        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> {
            urlShortenService.getOriginalUrl(shortUrl);
        });

        assertEquals("No value present", exception.getMessage());
    }

    // Repository 에서 ShortUrl 을 찾은 경우, 정상적으로 OriginalUrl 을 반환하는지 테스트
    @Test
    void testGetOriginalUrl_WhenShortUrlFound_ShouldReturnOriginalUrl() {
        urlShortenService = new UrlShortenServiceImpl(urlShortenRepository, randomKeyGenerator, blockDomainProvider);

        String shortUrl = "1234";
        String originalUrl = "http://www.google.com";
        Mockito.when(urlShortenRepository.findOriginUrlByKey(shortUrl)).thenReturn(Optional.of(originalUrl));

        String result = urlShortenService.getOriginalUrl(shortUrl);

        assertEquals(originalUrl, result);
    }

    // 중복된 key 발생 시, AlreadyExistsKeyException 이 발생하는지 테스트
    @Test
    void testCreateShortUrl_existsCase_throw_AlreadyExistsKeyException() {
        urlShortenService = new UrlShortenServiceImpl(urlShortenRepository, randomKeyGenerator, blockDomainProvider);

        String originalUrl = "https://www.google.com";
        String randomKey = "z93jD80";

        Mockito.when(randomKeyGenerator.getRandomKey()).thenReturn(randomKey);
        Mockito.when(urlShortenRepository.existsByKey(randomKey)).thenReturn(true);

        CustomDuplicateKeyException exception = assertThrows(CustomDuplicateKeyException.class, () -> {
            urlShortenService.createShortUrl(originalUrl);
        });

        assertEquals("key: " + randomKey + " already exists", exception.getMessage());
    }

    @Test
    void testCreateShortUrl_blockDomain_throw_BlockDomainException() {
        urlShortenService = new UrlShortenServiceImpl(urlShortenRepository, randomKeyGenerator, blockDomainProvider);

        String originalUrl = "http://localhost";
        String randomKey = "A92kld8";

        Mockito.when(randomKeyGenerator.getRandomKey()).thenReturn(randomKey);
        Mockito.when(blockDomainProvider.isBlocked(URI.create(originalUrl))).thenReturn(true);

        BlockDomainException exception = assertThrows(BlockDomainException.class, () -> {
            urlShortenService.createShortUrl(originalUrl);
        });

        assertEquals("this domain is blocked: " + originalUrl, exception.getMessage());
    }

    // Base62Converter 를 이용한 encoding, decoding 테스트 (encoding 후 decoding 시 원래 값과 같아야 함)
    @Test
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

    // 생성된 shortUrl 의 길이가 7자리인지
    @Test
    void testCreateShortUrl_always_7digit() {
        randomKeyGenerator = new RandomKeyGeneratorImpl(new Base62Converter());
        urlShortenService = new UrlShortenServiceImpl(urlShortenRepository, randomKeyGenerator, blockDomainProvider);

        String[] originalUrls = {"https://www.google.com", "https://www.naver.com", "https://www.daum.net"};

        for (String originalUrl : originalUrls) {
            String result = urlShortenService.createShortUrl(originalUrl);
            assertEquals(7, result.length());
        }
    }

    // 생성된 shortUrl 이 8자리가 되는 시점 테스트
    @Test
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
    void testStringUtils() {
        assertTrue(StringUtils.isBlank(null));
        assertTrue(StringUtils.isBlank(""));
        assertTrue(StringUtils.isBlank("     "));
    }

}
