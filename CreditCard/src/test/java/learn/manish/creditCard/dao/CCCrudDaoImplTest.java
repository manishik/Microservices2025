package learn.manish.creditCard.dao;

import learn.manish.creditCard.exceptions.CCNotFoundException;
import learn.manish.creditCard.model.CreditCard;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CCCrudDaoImplTest {

    private static final String CARD_NUMBER = "1234567890123456";

    private CCCrudDaoImpl dao;
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setURL("jdbc:h2:mem:crud_" + UUID.randomUUID()
                + ";MODE=PostgreSQL;DATABASE_TO_LOWER=TRUE;DB_CLOSE_DELAY=-1");
        dataSource.setUser("sa");
        dataSource.setPassword("");
        jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.execute("""
                CREATE TABLE creditcards (
                    ccnumber BIGINT PRIMARY KEY,
                    ccname VARCHAR(100),
                    cctype VARCHAR(100),
                    ccexpirydate DATE,
                    cccvv VARCHAR(10),
                    creditlimit INTEGER DEFAULT 0 NOT NULL,
                    availablecredit INTEGER DEFAULT 0 NOT NULL
                )
                """);
        dao = new CCCrudDaoImpl(dataSource);
    }

    @Test
    void saveCCInsertsCard() {
        assertEquals(1, dao.saveCC(card(CARD_NUMBER, "Visa")));

        assertEquals("Test Bank", jdbcTemplate.queryForObject(
                "SELECT ccname FROM creditcards WHERE ccnumber=?", String.class, Long.parseLong(CARD_NUMBER)));
    }

    @Test
    void findCCByIdMapsStoredCard() {
        insert(CARD_NUMBER, "Test Bank", "Visa");

        CreditCard result = dao.findCCById(CARD_NUMBER);

        assertEquals(CARD_NUMBER, result.getCcNumber());
        assertEquals("Test Bank", result.getCcName());
        assertEquals("Visa", result.getCcType());
    }

    @Test
    void findCCByIdReturnsNullWhenCardDoesNotExist() {
        assertNull(dao.findCCById(CARD_NUMBER));
    }

    @Test
    void getAllCCDetailsReturnsEveryStoredCard() {
        insert(CARD_NUMBER, "First Bank", "Visa");
        insert("2345678901234567", "Second Bank", "MasterCard");

        List<CreditCard> result = dao.getAllCCDetails();

        assertEquals(2, result.size());
        assertEquals(List.of("First Bank", "Second Bank"),
                result.stream().map(CreditCard::getCcName).sorted().toList());
    }

    @Test
    void updateCCDetailsUpdatesAndReturnsCard() {
        insert(CARD_NUMBER, "Old Bank", "Visa");
        CreditCard card = card(CARD_NUMBER, "MasterCard");
        card.setCcName("New Bank");

        CreditCard result = dao.updateCCDetails(card);

        assertEquals("Credit Card Details Updated Successfully", result.getMessage());
        assertEquals("New Bank", jdbcTemplate.queryForObject(
                "SELECT ccname FROM creditcards WHERE ccnumber=?", String.class, Long.parseLong(CARD_NUMBER)));
        assertEquals("MasterCard", jdbcTemplate.queryForObject(
                "SELECT cctype FROM creditcards WHERE ccnumber=?", String.class, Long.parseLong(CARD_NUMBER)));
    }

    @Test
    void updateCCDetailsThrowsWhenCardDoesNotExist() {
        assertThrows(CCNotFoundException.class, () -> dao.updateCCDetails(card(CARD_NUMBER, "Visa")));
    }

    @Test
    void deleteCCDeletesExistingCardAndReturnsZeroForMissingCard() {
        insert(CARD_NUMBER, "Test Bank", "Visa");

        assertEquals(1, dao.deleteCC(CARD_NUMBER));
        assertEquals(0, dao.deleteCC(CARD_NUMBER));
    }

    @Test
    void numericOperationsRejectNonNumericCardNumber() {
        CreditCard card = card("not-a-number", "Visa");

        assertThrows(NumberFormatException.class, () -> dao.saveCC(card));
        assertThrows(NumberFormatException.class, () -> dao.findCCById("not-a-number"));
        assertThrows(NumberFormatException.class, () -> dao.deleteCC("not-a-number"));
    }

    private void insert(String number, String name, String type) {
        jdbcTemplate.update("INSERT INTO creditcards (ccnumber, ccname, cctype) VALUES (?, ?, ?)",
                Long.parseLong(number), name, type);
    }

    private static CreditCard card(String number, String type) {
        CreditCard card = new CreditCard();
        card.setCcNumber(number);
        card.setCcName("Test Bank");
        card.setCcType(type);
        return card;
    }
}
