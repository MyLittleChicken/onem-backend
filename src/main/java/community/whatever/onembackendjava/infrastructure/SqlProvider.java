package community.whatever.onembackendjava.infrastructure;

public interface SqlProvider {
    String findOriginalUrlByKey();
    String findShortenUrlByKey();
    String existsByKey();
    String save();
}
