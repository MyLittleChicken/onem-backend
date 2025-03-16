package community.whatever.onembackendjava;

public class BlockDomainException extends RuntimeException {
    public BlockDomainException(final String message) {
        super("this domain is blocked: " + message);
    }
}
