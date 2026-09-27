package learn.manish.creditCard.model;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

class CreditCardTest {

    @Test
    void gettersReturnValuesAssignedBySetters() {
        CreditCard card = new CreditCard();
        Date expiry = new Date();

        card.setCcNumber("1234567890123456");
        card.setCcType("Visa");
        card.setCcName("Test Bank");
        card.setCcExpiryDate(expiry);
        card.setCcCVV("123");
        card.setCreditLimit(10_000);
        card.setAvailableCredit(7_500);
        card.setMessage("ready");

        assertEquals("1234567890123456", card.getCcNumber());
        assertEquals("Visa", card.getCcType());
        assertEquals("Test Bank", card.getCcName());
        assertSame(expiry, card.getCcExpiryDate());
        assertEquals("123", card.getCcCVV());
        assertEquals(10_000, card.getCreditLimit());
        assertEquals(7_500, card.getAvailableCredit());
        assertEquals("ready", card.getMessage());
    }
}
