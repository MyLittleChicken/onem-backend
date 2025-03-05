package community.whatever.onembackendjava.application;

import community.whatever.onembackendjava.repository.UrlShortenRepository;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class UrlShortenServiceImpl implements UrlShortenService {

    private final UrlShortenRepository urlShortenRepository;
    private final Random random = new Random();

    public UrlShortenServiceImpl(final UrlShortenRepository urlShortenRepository) {
        this.urlShortenRepository = urlShortenRepository;
    }

    @Override
    public String getOriginalUrl(final String shortUrl) throws IllegalArgumentException {
        try {
            return urlShortenRepository.getOriginUrl(shortUrl);
        } catch (NullPointerException ne) {
            throw new IllegalArgumentException("Invalid key");
        }
    }

    @Override
    public String createShortUrl(final String originUrl) {
        String randomKey = String.valueOf(random.nextInt(10000));
        urlShortenRepository.createShortenUrl(originUrl, randomKey);
        return randomKey;
    }
}
