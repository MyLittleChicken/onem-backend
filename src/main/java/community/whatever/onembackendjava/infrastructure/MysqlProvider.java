package community.whatever.onembackendjava.infrastructure;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "spring.datasource.driver-class-name", havingValue = "com.mysql.cj.jdbc.Driver")
public class MysqlProvider implements SqlProvider{
    @Override
    public String getFindOriginalUrlByKeySql() {
        return "SELECT su.origin_url" +
                "FROM url_shortener.short_url AS su " +
                "WHERE su.shorten_url = ?";
    }

    @Override
    public String getFindShortenUrlByKeySql() {
        return "SELECT su.shorten_url, su.origin_url, su.is_deleted, su.created_at, su.expired_at " +
                "FROM url_shortener.short_url AS su " +
                "WHERE su.shorten_url = ?";
    }

    @Override
    public String getExistsByKeySql() {
        return "SELECT EXISTS" +
                "(SELECT 1 " +
                "FROM url_shortener.short_url AS su " +
                "WHERE su.shorten_url = ?) " +
                "AS isExists";
    }

    @Override
    public String getSaveSql() {
        return "INSERT INTO url_shortener.short_url" +
                "(shorten_url, origin_url, is_deleted, created_at, expired_at) " +
                "VALUES (?, ?, ?, ?, ?)";
    }
}
