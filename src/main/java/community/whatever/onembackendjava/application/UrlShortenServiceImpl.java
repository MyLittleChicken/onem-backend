package community.whatever.onembackendjava.application;

import community.whatever.onembackendjava.BlockDomainException;
import community.whatever.onembackendjava.BlockDomains;
import community.whatever.onembackendjava.CustomDuplicateKeyException;
import community.whatever.onembackendjava.repository.UrlShortenRepository;
import org.springframework.stereotype.Service;

import java.net.URI;

@Service
public class UrlShortenServiceImpl implements UrlShortenService {

    private final UrlShortenRepository urlShortenRepository;
    private final RandomKeyGenerator randomKeyGenerator;
    private final BlockDomains blockDomains;

    public UrlShortenServiceImpl(
            final UrlShortenRepository urlShortenRepository,
            final RandomKeyGenerator randomKeyGenerator,
            final BlockDomains blockDomains
    ) {
        this.urlShortenRepository = urlShortenRepository;
        this.randomKeyGenerator = randomKeyGenerator;
        this.blockDomains = blockDomains;
    }

    @Override
    public String getOriginalUrl(final String shortUrl) throws IllegalArgumentException {
        return urlShortenRepository.findOriginUrlByKey(shortUrl)
                .orElseThrow();
    }

    @Override
    public String createShortUrl(final String originUrl) throws CustomDuplicateKeyException {
        if (blockDomains.isBlocked(URI.create(originUrl))) {
            throw new BlockDomainException(originUrl);
        }

        String randomKey = randomKeyGenerator.getRandomKey();

        if (urlShortenRepository.existsByKey(randomKey)) {
            throw new CustomDuplicateKeyException(randomKey);
        }

        urlShortenRepository.createShortenUrl(originUrl, randomKey);
        return randomKey;
    }
}
