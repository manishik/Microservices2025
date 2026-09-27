package learn.manish.creditCard.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // Handles CCInvalidException
    @ExceptionHandler(CCInvalidException.class)
    public ResponseEntity<String> handleCCInvalidException(CCInvalidException ccInvalidException) {
        return new ResponseEntity<>(ccInvalidException.getMessage(), HttpStatus.BAD_REQUEST);
    }

    // Handles CCNotFoundException
    @ExceptionHandler(CCNotFoundException.class)
    public ResponseEntity<String> handleCCNotAvailableException(CCNotFoundException ex) {
        return new ResponseEntity<>("Credit card not found", HttpStatus.NOT_FOUND);
    }

    // Handles CCAlreadyExistsException
    @ExceptionHandler(CCAlreadyExistsException.class)
    public ResponseEntity<String> handleCCAlreadyExistsException(CCAlreadyExistsException ex) {
        return new ResponseEntity<>("Credit card already exists in the system", HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ClassCastException.class)
    public ResponseEntity<String> handleClassCastException(Exception ex) {
        logger.error("Class cast error", ex);
        return new ResponseEntity<>("Invalid request data", HttpStatus.BAD_REQUEST);
    }

    // Handles generic Exception (fallback)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGlobalException(Exception ex) {
        logger.error("Unhandled application error", ex);
        return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Handles IllegalArgumentException
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    // Handles NullPointerException
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<String> handleNullPointerException(NullPointerException ex) {
        return new ResponseEntity<>("Null value encountered", HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
