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
    private final UrlShortenRowMapper urlShortenRowMapper;

    @Override
    public Optional<String> findOriginalUrlByKey(String shortenUrl) {
        return jdbcClient.sql(MysqlQueries.FIND_ORIGINAL_URL_BY_KEY.getQuery())
                .param(shortenUrl)
                .query(String.class)
                .optional();
    }

    @Override
    public Optional<ShortenUrlEntity> findShortenUrlByKey(String shortenUrl) {
        return jdbcClient.sql(MysqlQueries.FIND_SHORTEN_URL_BY_KEY.getQuery())
                .param(shortenUrl)
                .query(urlShortenRowMapper)
                .optional();
    }

    @Override
    public boolean existsByKey(String key) {
        return jdbcClient.sql(MysqlQueries.EXISTS_BY_KEY.getQuery())
                .param(key)
                .query(Boolean.class)
                .single();
    }

    @Override
    public void save(ShortenUrlEntity entity) {
        jdbcClient.sql(MysqlQueries.SAVE.getQuery())
                .params(entity.shortenUrl(), entity.originUrl(), entity.isDeleted(), entity.createdAt(), entity.expiredAt())
                .update();
    }
}
