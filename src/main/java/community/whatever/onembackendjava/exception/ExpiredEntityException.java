package community.whatever.onembackendjava.exception;

public class ExpiredEntityException extends RuntimeException {
    public ExpiredEntityException(String message) {
        super("key: " + message + " is already expired");
    }
}
