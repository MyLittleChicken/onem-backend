package community.whatever.onembackendjava;

public class CustomDuplicateKeyException extends RuntimeException {
    public CustomDuplicateKeyException(final String key) {
        super("key: " + key + " already exists");
    }
}
