package community.whatever.onembackendjava.repository;

import java.util.Optional;

public interface UrlShortenRepository {

    Optional<String> getOriginUrl(final String shortenUrl);

    void createShortenUrl(final String originUrl, final String key);

}
