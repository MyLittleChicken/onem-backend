package community.whatever.onembackendjava.application;

import community.whatever.onembackendjava.AlreadyExistsKeyException;
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
        return urlShortenRepository.getOriginUrl(shortUrl)
                .orElseThrow();
    }

    @Override
    public String createShortUrl(final String originUrl) throws AlreadyExistsKeyException {
        String randomKey = randomKeyGenerator.getRandomKey();

        if (urlShortenRepository.getIsExistKey(randomKey)) {
            throw new AlreadyExistsKeyException("key: " + randomKey + " already exists");
        }

        urlShortenRepository.createShortenUrl(originUrl, randomKey);
        return randomKey;
    }
}
