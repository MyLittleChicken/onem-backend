package community.whatever.onembackendjava.infrastructure;

import community.whatever.onembackendjava.entity.ShortenUrlEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class RelationDatabaseRepository implements UrlShortenRepository{

    private final JdbcClient jdbcClient;
    private final SqlProvider sqlProvider;

    @Override
    public Optional<String> findOriginalUrlByKey(String shortenUrl) {
        return jdbcClient.sql(sqlProvider.findOriginalUrlByKey())
                .param(shortenUrl)
                .query(String.class)
                .optional();
    }

    @Override
    public Optional<ShortenUrlEntity> findShortenUrlByKey(String shortenUrl) {
        return jdbcClient.sql(sqlProvider.findShortenUrlByKey())
                .param(shortenUrl)
                .query(ShortenUrlEntity.class)
                .optional();
    }

    @Override
    public boolean existsByKey(String key) {
        return jdbcClient.sql(sqlProvider.existsByKey())
                .param(key)
                .query(Boolean.class)
                .single();
    }

    @Override
    public void save(ShortenUrlEntity entity) {
        jdbcClient.sql(sqlProvider.save())
                .params(entity.shortenUrl(), entity.originUrl(), entity.isDeleted(), entity.createdAt(), entity.expiredAt())
                .update();
    }
}
