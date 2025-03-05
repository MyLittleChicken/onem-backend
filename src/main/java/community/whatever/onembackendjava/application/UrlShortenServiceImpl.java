package community.whatever.onembackendjava.application;

import community.whatever.onembackendjava.repository.UrlShortenRepository;
import org.springframework.stereotype.Service;

@Service
public class UrlShortenServiceImpl implements UrlShortenService {

    private final UrlShortenRepository urlShortenRepository;
    private final RandomKeyGenerator randomKeyGenerator;

    public UrlShortenServiceImpl(
            final UrlShortenRepository urlShortenRepository,
            final RandomKeyGenerator randomKeyGenerator
    ) {
        this.urlShortenRepository = urlShortenRepository;
        this.randomKeyGenerator = randomKeyGenerator;
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
        String randomKey = randomKeyGenerator.getRandomKey();
        urlShortenRepository.createShortenUrl(originUrl, randomKey);
        return randomKey;
    }
}
