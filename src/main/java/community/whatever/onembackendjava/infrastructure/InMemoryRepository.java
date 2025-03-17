package community.whatever.onembackendjava.infrastructure;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class InMemoryRepository implements UrlShortenRepository {

    private final Map<String, ShortenUrlRecord> shortenUrlStore = new ConcurrentHashMap<>();

    @Override
    public Optional<String> findOriginalUrlByKey(final String shortenUrl) {
        return Optional.ofNullable(shortenUrlStore.get(shortenUrl))
                .flatMap(record -> record.isDeleted() ? Optional.empty() : Optional.of(record.originUrl()));
    }

    @Override
    public Optional<ShortenUrlRecord> findShortenUrlByKey(final String shortenUrl) {
        return Optional.ofNullable(shortenUrlStore.get(shortenUrl))
                .flatMap(record -> record.isDeleted() ? Optional.empty() : Optional.of(record));
    }

    @Override
    public boolean existsByKey(final String key) {
        return shortenUrlStore.containsKey(key);
    }

    @Override
    public void save(final ShortenUrlRecord record) {
        shortenUrlStore.put(record.shortenUrl(), record);
    }

}
