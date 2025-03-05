package community.whatever.onembackendjava.repository;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class InMemoryRepository implements UrlShortenRepository {

    private final Map<String, String> shortenUrls = new HashMap<>();

    @Override
    public String getOriginUrl(final String shortenUrl) {
        return shortenUrls.get(shortenUrl);
    }

    @Override
    public void createShortenUrl(final String originUrl, final String key) {
        shortenUrls.put(key, originUrl);
    }
}
