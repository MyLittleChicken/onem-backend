package community.whatever.onembackendjava;

import community.whatever.onembackendjava.application.RandomKeyGenerator;
import community.whatever.onembackendjava.application.UrlShortenServiceImpl;
import community.whatever.onembackendjava.repository.UrlShortenRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OnemBackendApplicationTests {

    private UrlShortenRepository urlShortenRepository;
    private RandomKeyGenerator randomKeyGenerator;
    private UrlShortenServiceImpl urlShortenService;

    @BeforeEach
    void setUp() {
        urlShortenRepository = Mockito.mock(UrlShortenRepository.class);
        randomKeyGenerator = Mockito.mock(RandomKeyGenerator.class);
        urlShortenService = new UrlShortenServiceImpl(urlShortenRepository, randomKeyGenerator);
    }

    // Repository 에서 ShortUrl 을 찾지 못한 경우 NoSuchElementException 이 발생하는지 테스트
    @Test
    void testGetOriginalUrl_WhenShortUrlNotFound_ShouldThrowException() {
        String shortUrl = "1234";
        Mockito.when(urlShortenRepository.getOriginUrl(shortUrl)).thenReturn(Optional.empty());

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
        Mockito.when(urlShortenRepository.getOriginUrl(shortUrl)).thenReturn(Optional.of(originalUrl));

        String result = urlShortenService.getOriginalUrl(shortUrl);

        assertEquals(originalUrl, result);
    }

}
