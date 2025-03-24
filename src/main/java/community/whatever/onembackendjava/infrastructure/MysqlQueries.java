package community.whatever.onembackendjava.infrastructure;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MysqlQueries {

    FIND_ORIGINAL_URL_BY_KEY(
            "SELECT su.origin_url " +
                    "FROM url_shortener.short_url AS su " +
                    "WHERE su.shorten_url = ?"
    ),
    FIND_SHORTEN_URL_BY_KEY(
            "SELECT su.shorten_url, su.origin_url, su.is_deleted, su.created_at, su.expired_at " +
                    "FROM url_shortener.short_url AS su " +
                    "WHERE su.shorten_url = ?"
    ),
    EXISTS_BY_KEY(
            "SELECT EXISTS " +
                    "(SELECT 1 " +
                    "FROM url_shortener.short_url AS su " +
                    "WHERE su.shorten_url = ?) " +
                    "AS isExists"
    ),
    SAVE(
            "INSERT INTO url_shortener.short_url " +
                    "(shorten_url, origin_url, is_deleted, created_at, expired_at) " +
                    "VALUES (?, ?, ?, ?, ?)"
    );

    private final String query;
}
