package community.whatever.onembackendjava.repository;

import java.util.Optional;

public interface UrlShortenRepository {

    Optional<String> findOriginUrlByKey(final String shortenUrl);

    boolean existsByKey(final String key);

    void createShortenUrl(final String originUrl, final String key);

}
