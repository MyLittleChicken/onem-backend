package community.whatever.onembackendjava.infrastructure;

public interface SqlProvider {
    String getFindOriginalUrlByKeySql();
    String getFindShortenUrlByKeySql();
    String getExistsByKeySql();
    String getSaveSql();
}
