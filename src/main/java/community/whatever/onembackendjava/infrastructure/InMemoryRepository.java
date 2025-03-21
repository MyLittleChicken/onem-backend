package community.whatever.onembackendjava.infrastructure;

import community.whatever.onembackendjava.entity.ShortenUrlEntity;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class InMemoryRepository implements UrlShortenRepository {

    private final Map<String, ShortenUrlEntity> shortenUrlStore = new ConcurrentHashMap<>();

    @Override
    public Optional<String> findOriginalUrlByKey(final String shortenUrl) {
        return Optional.ofNullable(shortenUrlStore.get(shortenUrl))
                .flatMap(entity -> entity.isDeleted() ? Optional.empty() : Optional.of(entity.originUrl()));
    }

    @Override
    public Optional<ShortenUrlEntity> findShortenUrlByKey(final String shortenUrl) {
        return Optional.ofNullable(shortenUrlStore.get(shortenUrl))
                .flatMap(entity -> entity.isDeleted() ? Optional.empty() : Optional.of(entity));
    }

    @Override
    public boolean existsByKey(final String key) {
        return shortenUrlStore.containsKey(key);
    }

    @Override
    public void save(final ShortenUrlEntity entity) {
        shortenUrlStore.put(entity.shortenUrl(), entity);
    }

}
