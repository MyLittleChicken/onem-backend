package community.whatever.onembackendjava.application;

import community.whatever.onembackendjava.exception.BlockDomainException;
import community.whatever.onembackendjava.BlockDomainProvider;
import community.whatever.onembackendjava.exception.CustomDuplicateKeyException;
import community.whatever.onembackendjava.infrastructure.UrlShortenRepository;
import org.springframework.stereotype.Service;

import java.net.URI;

@Service
public class UrlShortenServiceImpl implements UrlShortenService {

    private final UrlShortenRepository urlShortenRepository;
    private final RandomKeyGenerator randomKeyGenerator;
    private final BlockDomainProvider blockDomainProvider;

    public UrlShortenServiceImpl(
            final UrlShortenRepository urlShortenRepository,
            final RandomKeyGenerator randomKeyGenerator,
            final BlockDomainProvider blockDomainProvider
    ) {
        this.urlShortenRepository = urlShortenRepository;
        this.randomKeyGenerator = randomKeyGenerator;
        this.blockDomainProvider = blockDomainProvider;
    }

    @Override
    public String getOriginalUrl(final String shortUrl) throws IllegalArgumentException {
        return urlShortenRepository.findOriginUrlByKey(shortUrl)
                .orElseThrow();
    }

    @Override
    public String createShortUrl(final String originUrl) throws CustomDuplicateKeyException {
        if (blockDomainProvider.isBlocked(URI.create(originUrl))) {
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
