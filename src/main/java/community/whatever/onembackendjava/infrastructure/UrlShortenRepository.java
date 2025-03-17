package community.whatever.onembackendjava.infrastructure;

import java.util.Optional;

public interface UrlShortenRepository {

    Optional<String> findOriginalUrlByKey(final String shortenUrl);

    Optional<ShortenUrlRecord> findShortenUrlByKey(final String shortenUrl);

    boolean existsByKey(final String key);

    void save(final ShortenUrlRecord record);

}
