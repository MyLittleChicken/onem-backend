package community.whatever.onembackendjava.infrastructure;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class InMemoryRepository implements UrlShortenRepository {

    private final Map<String, String> shortenUrls = new HashMap<>();

    @Override
    public Optional<String> findOriginUrlByKey(final String key) {
        return Optional.ofNullable(shortenUrls.get(key));
    }

    @Override
    public boolean existsByKey(final String key) {
        return shortenUrls.containsKey(key);
    }

    @Override
    public void createShortenUrl(final String originUrl, final String key) {
        shortenUrls.put(key, originUrl);
    }
}
