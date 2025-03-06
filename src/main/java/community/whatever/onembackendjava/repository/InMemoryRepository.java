package community.whatever.onembackendjava.repository;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class InMemoryRepository implements UrlShortenRepository {

    private final Map<String, String> shortenUrls = new HashMap<>();

    @Override
    public Optional<String> getOriginUrl(final String shortenUrl) {
        return Optional.ofNullable(shortenUrls.get(shortenUrl));
    }

    @Override
    public void createShortenUrl(final String originUrl, final String key) {
        shortenUrls.put(key, originUrl);
    }
}
