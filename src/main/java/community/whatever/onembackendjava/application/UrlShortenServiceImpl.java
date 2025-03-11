package community.whatever.onembackendjava.application;

import community.whatever.onembackendjava.CustomDuplicateKeyException;
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
    public String createShortUrl(final String originUrl) throws CustomDuplicateKeyException {
        String randomKey = randomKeyGenerator.getRandomKey();

        if (urlShortenRepository.getIsExistKey(randomKey)) {
            throw new CustomDuplicateKeyException(randomKey);
        }

        urlShortenRepository.createShortenUrl(originUrl, randomKey);
        return randomKey;
    }
}
