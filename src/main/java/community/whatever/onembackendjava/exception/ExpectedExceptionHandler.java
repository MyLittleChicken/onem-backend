package community.whatever.onembackendjava.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@RestControllerAdvice
public class ExpectedExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(ExpectedExceptionHandler.class);

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(exception = NoSuchElementException.class)
    public String handleNoSuchElementException(final NoSuchElementException e) {
        loggingError(e);
        return e.getMessage();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(exception = CustomDuplicateKeyException.class)
    public String handleDuplicateKeyException(final CustomDuplicateKeyException e) {
        loggingError(e);
        return e.getMessage();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(exception = BlockDomainException.class)
    public String handleBlockDomainException(final BlockDomainException e) {
        loggingError(e);
        return e.getMessage();
    }

    private void loggingError(final Exception e) {
        logger.error("Expected exception class: {}, message: {}", e.getClass().getName(), e.getMessage());
    }

}
