package community.whatever.onembackendjava.repository;

public interface UrlShortenRepository {

    String getOriginUrl(final String shortenUrl);

    void createShortenUrl(final String originUrl, final String key);

}
