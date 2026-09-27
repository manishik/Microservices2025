package learn.manish.creditCard.model.mappers;

import learn.manish.creditCard.model.CreditCard;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.sql.ResultSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CreditCardRowMapperTest {

    @Test
    void mapRowMapsEveryCreditCardColumn() throws Exception {
        ResultSet resultSet = mock(ResultSet.class);
        Date expiry = Date.valueOf("2030-12-31");
        when(resultSet.getString("ccnumber")).thenReturn("1234567890123456");
        when(resultSet.getString("ccname")).thenReturn("Test Bank");
        when(resultSet.getString("cctype")).thenReturn("Visa");
        when(resultSet.getDate("ccexpirydate")).thenReturn(expiry);
        when(resultSet.getString("cccvv")).thenReturn("123");
        when(resultSet.getInt("creditlimit")).thenReturn(10_000);
        when(resultSet.getInt("availablecredit")).thenReturn(7_500);

        CreditCard card = new CreditCardRowMapper().mapRow(resultSet, 0);

        assertEquals("1234567890123456", card.getCcNumber());
        assertEquals("Test Bank", card.getCcName());
        assertEquals("Visa", card.getCcType());
        assertEquals(expiry, card.getCcExpiryDate());
        assertEquals("123", card.getCcCVV());
        assertEquals(10_000, card.getCreditLimit());
        assertEquals(7_500, card.getAvailableCredit());
        verify(resultSet).getString("ccnumber");
    }
}
