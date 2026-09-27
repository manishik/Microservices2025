package manish.learn.bank.service;

import manish.learn.bank.entities.CustomerAccount;
import manish.learn.bank.exceptions.BankServiceUnavailableException;
import manish.learn.bank.exceptions.GlobalExceptionHandler;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CustomerServiceImplTest {

    @Test
    void fallbackReturnsServiceUnavailableInsteadOfFakeSuccess() {
        CustomerServiceImpl customerService = new CustomerServiceImpl();
        CustomerAccount request = new CustomerAccount();
        RuntimeException bankFailure = new RuntimeException("Bank is down");

        BankServiceUnavailableException exception = assertThrows(
                BankServiceUnavailableException.class,
                () -> customerService.createAccountFallback(request, bankFailure)
        );

        ResponseEntity<String> response = new GlobalExceptionHandler()
                .handleBankServiceUnavailableException(exception);

        assertSame(bankFailure, exception.getCause());
        assertEquals(HttpStatus.SERVICE_UNAVAILABLE, response.getStatusCode());
        assertEquals("Bank service is currently unavailable. Account was not created.", response.getBody());
    }
}
