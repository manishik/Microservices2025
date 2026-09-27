package learn.manish.creditCard.dao;

import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CCValidateDAOImplTest {

    private static final String CARD_NUMBER = "1234567890123456";

    private CCValidateDAOImpl dao;
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setURL("jdbc:h2:mem:validate_" + UUID.randomUUID()
                + ";MODE=PostgreSQL;DATABASE_TO_LOWER=TRUE;DB_CLOSE_DELAY=-1");
        dataSource.setUser("sa");
        dataSource.setPassword("");
        jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.execute("CREATE TABLE creditcards (ccnumber BIGINT PRIMARY KEY)");
        dao = new CCValidateDAOImpl(dataSource);
    }

    @Test
    void returnsTrueWhenCardExists() {
        jdbcTemplate.update("INSERT INTO creditcards (ccnumber) VALUES (?)", Long.parseLong(CARD_NUMBER));

        assertTrue(dao.doesCCExistsInDB(CARD_NUMBER));
    }

    @Test
    void returnsFalseWhenCardDoesNotExist() {
        assertFalse(dao.doesCCExistsInDB(CARD_NUMBER));
    }

    @Test
    void returnsFalseForNullOrNonNumericNumberWithoutQueryingDatabase() {
        jdbcTemplate.execute("DROP TABLE creditcards");

        assertFalse(dao.doesCCExistsInDB(null));
        assertFalse(dao.doesCCExistsInDB("1234x"));
    }

    @Test
    void returnsFalseWhenDatabaseQueryFails() {
        jdbcTemplate.execute("DROP TABLE creditcards");

        assertFalse(dao.doesCCExistsInDB(CARD_NUMBER));
    }
}
