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
    private final UrlShortenRowMapper urlShortenRowMapper;

    @Override
    public Optional<String> findOriginalUrlByKey(String shortenUrl) {
        return jdbcClient.sql(sqlProvider.getFindOriginalUrlByKeySql())
                .param(shortenUrl)
                .query(String.class)
                .optional();
    }

    @Override
    public Optional<ShortenUrlEntity> findShortenUrlByKey(String shortenUrl) {
        return jdbcClient.sql(sqlProvider.getFindShortenUrlByKeySql())
                .param(shortenUrl)
                .query(urlShortenRowMapper)
                .optional();
    }

    @Override
    public boolean existsByKey(String key) {
        return jdbcClient.sql(sqlProvider.getExistsByKeySql())
                .param(key)
                .query(Boolean.class)
                .single();
    }

    @Override
    public void save(ShortenUrlEntity entity) {
        jdbcClient.sql(sqlProvider.getSaveSql())
                .params(entity.shortenUrl(), entity.originUrl(), entity.isDeleted(), entity.createdAt(), entity.expiredAt())
                .update();
    }
}
