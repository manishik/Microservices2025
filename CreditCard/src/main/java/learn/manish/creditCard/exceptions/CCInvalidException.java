package learn.manish.creditCard.exceptions;

public class CCInvalidException extends RuntimeException {

    public CCInvalidException() {
        super();
    }

    public CCInvalidException(String message) {
        super(message);
    }

}
