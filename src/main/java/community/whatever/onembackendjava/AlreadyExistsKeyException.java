package community.whatever.onembackendjava;

public class AlreadyExistsKeyException extends RuntimeException {
    public AlreadyExistsKeyException(final String message) {
        super(message);
    }
}
