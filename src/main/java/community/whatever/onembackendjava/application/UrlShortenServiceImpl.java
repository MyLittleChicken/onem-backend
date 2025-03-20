package community.whatever.onembackendjava.application;

import community.whatever.onembackendjava.entity.ShortenUrlEntity;
import community.whatever.onembackendjava.exception.BlockDomainException;
import community.whatever.onembackendjava.BlockDomainProvider;
import community.whatever.onembackendjava.exception.CustomDuplicateKeyException;
import community.whatever.onembackendjava.exception.ExpiredEntityException;
import community.whatever.onembackendjava.infrastructure.UrlShortenRepository;
import community.whatever.onembackendjava.presentation.RequestDto;
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
        ShortenUrlEntity entity = urlShortenRepository.findShortenUrlByKey(shortUrl).orElseThrow();

        if (entity.isExpired()) {
            throw new ExpiredEntityException(shortUrl);
        }

        return entity.originUrl();
    }

    @Override
    public String createShortUrl(final RequestDto.CreateShortenUrl requestDto) throws CustomDuplicateKeyException {
        if (blockDomainProvider.isBlocked(URI.create(requestDto.originalUrl()))) {
            throw new BlockDomainException(requestDto.originalUrl());
        }

        String randomKey = randomKeyGenerator.getRandomKey();

        if (urlShortenRepository.existsByKey(randomKey)) {
            throw new CustomDuplicateKeyException(randomKey);
        }

        ShortenUrlEntity entity = ShortenUrlEntity.createEntity(
                randomKey,
                requestDto.originalUrl()
        );

        urlShortenRepository.save(entity);
        return randomKey;
    }
}
