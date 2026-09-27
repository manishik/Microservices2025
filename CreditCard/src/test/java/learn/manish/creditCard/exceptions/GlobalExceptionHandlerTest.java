package learn.manish.creditCard.exceptions;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void mapsInvalidCardToBadRequest() {
        assertResponse(handler.handleCCInvalidException(new CCInvalidException("invalid card")),
                HttpStatus.BAD_REQUEST, "invalid card");
    }

    @Test
    void mapsMissingCardToNotFound() {
        assertResponse(handler.handleCCNotAvailableException(new CCNotFoundException()),
                HttpStatus.NOT_FOUND, "Credit card not found");
    }

    @Test
    void mapsExistingCardToConflict() {
        assertResponse(handler.handleCCAlreadyExistsException(new CCAlreadyExistsException()),
                HttpStatus.CONFLICT, "Credit card already exists in the system");
    }

    @Test
    void mapsClassCastToBadRequestWithoutExposingDetails() {
        assertResponse(handler.handleClassCastException(new ClassCastException("secret")),
                HttpStatus.BAD_REQUEST, "Invalid request data");
    }

    @Test
    void mapsGenericExceptionToInternalServerErrorWithoutExposingDetails() {
        assertResponse(handler.handleGlobalException(new Exception("secret")),
                HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong");
    }

    @Test
    void mapsIllegalArgumentToBadRequest() {
        assertResponse(handler.handleIllegalArgumentException(new IllegalArgumentException("bad argument")),
                HttpStatus.BAD_REQUEST, "bad argument");
    }

    @Test
    void mapsNullPointerToInternalServerError() {
        assertResponse(handler.handleNullPointerException(new NullPointerException()),
                HttpStatus.INTERNAL_SERVER_ERROR, "Null value encountered");
    }

    private static void assertResponse(ResponseEntity<String> response, HttpStatus status, String body) {
        assertEquals(status, response.getStatusCode());
        assertEquals(body, response.getBody());
    }
}
