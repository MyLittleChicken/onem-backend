package community.whatever.onembackendjava;

import community.whatever.onembackendjava.application.Base62Converter;
import community.whatever.onembackendjava.application.RandomKeyGenerator;
import community.whatever.onembackendjava.application.RandomKeyGeneratorImpl;
import community.whatever.onembackendjava.application.UrlShortenServiceImpl;
import community.whatever.onembackendjava.repository.UrlShortenRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OnemBackendApplicationTests {

    private UrlShortenRepository urlShortenRepository;
    private RandomKeyGenerator randomKeyGenerator;
    private UrlShortenServiceImpl urlShortenService;
    private Base62Converter base62Converter;

    @BeforeEach
    void setUp() {
        urlShortenRepository = Mockito.mock(UrlShortenRepository.class);
        base62Converter = new Base62Converter();
        randomKeyGenerator = new RandomKeyGeneratorImpl(base62Converter);
        urlShortenService = new UrlShortenServiceImpl(urlShortenRepository, randomKeyGenerator);
    }

    // Repository 에서 ShortUrl 을 찾지 못한 경우 NoSuchElementException 이 발생하는지 테스트
    @Test
    void testGetOriginalUrl_WhenShortUrlNotFound_ShouldThrowException() {
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
        String shortUrl = "1234";
        String originalUrl = "http://www.google.com";
        Mockito.when(urlShortenRepository.findOriginUrlByKey(shortUrl)).thenReturn(Optional.of(originalUrl));

        String result = urlShortenService.getOriginalUrl(shortUrl);

        assertEquals(originalUrl, result);
    }

    // 중복된 key 발생 시, AlreadyExistsKeyException 이 발생하는지 테스트
    @Test
    void testCreateShortUrl_existsCase_throw_AlreadyExistsKeyException() {
        urlShortenService = new UrlShortenServiceImpl(urlShortenRepository, randomKeyGenerator);

        String originalUrl = "https://www.google.com";
        String randomKey = "z93jD80";

        Mockito.when(randomKeyGenerator.getRandomKey()).thenReturn(randomKey);
        Mockito.when(urlShortenRepository.existsByKey(randomKey)).thenReturn(true);

        CustomDuplicateKeyException exception = assertThrows(CustomDuplicateKeyException.class, () -> {
            urlShortenService.createShortUrl(originalUrl);
        });

        assertEquals("key: " + randomKey + " already exists", exception.getMessage());
    }

    // Base62Converter 를 이용한 encoding, decoding 테스트 (encoding 후 decoding 시 원래 값과 같아야 함)
    @Test
    void testBase62EncodingAndDecoding() {
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
        String[] originalUrls = {"https://www.google.com", "https://www.naver.com", "https://www.daum.net"};

        for (String originalUrl : originalUrls) {
            String result = urlShortenService.createShortUrl(originalUrl);
            assertEquals(7, result.length());
        }
    }

    // 생성된 shortUrl 이 8자리가 되는 시점 테스트
    @Test
    void testCreateShortUrl_8digit() {
        long value = 3521614606208L; // Tue Aug 05 2081 10:16:46 GMT+0000
        String encoded = base62Converter.encode(value);

        System.out.printf("original: %d, encoded: %s, length: %d%n", value, encoded, encoded.length());
        assertEquals(8, encoded.length());
    }

}
