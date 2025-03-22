package community.whatever.onembackendjava.infrastructure;

import community.whatever.onembackendjava.entity.ShortenUrlEntity;

import java.util.Optional;

public interface UrlShortenRepository {

    Optional<String> findOriginalUrlByKey(final String shortenUrl);

    Optional<ShortenUrlEntity> findShortenUrlByKey(final String shortenUrl);

    boolean existsByKey(final String key);

    void save(final ShortenUrlEntity entity);

}
