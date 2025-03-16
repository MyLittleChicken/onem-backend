package community.whatever.onembackendjava.exception;

public class BlockDomainException extends RuntimeException {
    public BlockDomainException(final String message) {
        super("this domain is blocked: " + message);
    }
}
