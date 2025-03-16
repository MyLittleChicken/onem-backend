package community.whatever.onembackendjava.exception;

public class CustomDuplicateKeyException extends RuntimeException {
    public CustomDuplicateKeyException(final String key) {
        super("key: " + key + " already exists");
    }
}
