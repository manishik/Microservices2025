package manish.learn.bank.exceptions;

public class BankServiceUnavailableException extends RuntimeException {

    public BankServiceUnavailableException(String message, Throwable cause) {
        super(message, cause);
    }
}
