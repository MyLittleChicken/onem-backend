package community.whatever.onembackendjava;

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
        logger.error("Expected exception class: {}, message: {}", e.getClass().getName(), e.getMessage());
        return e.getMessage();
    }

}
