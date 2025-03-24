package community.whatever.onembackendjava.infrastructure;

import community.whatever.onembackendjava.entity.ShortenUrlEntity;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;

@Component
public class UrlShortenRowMapper implements RowMapper<ShortenUrlEntity> {

    @Override
    public ShortenUrlEntity mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        Instant createdAt = rs.getTimestamp("created_at") == null
                ? null : rs.getTimestamp("created_at").toInstant();

        Instant expiredAt = rs.getTimestamp("expired_at") == null
                ? null : rs.getTimestamp("expired_at").toInstant();

        return new ShortenUrlEntity(
                rs.getString("shorten_url"),
                rs.getString("origin_url"),
                rs.getBoolean("is_deleted"),
                createdAt,
                expiredAt
        );
    }
}