package learn.manish.creditCard.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class CCValidateDAOImpl implements CCValidateDao {

    private static final Logger logger = LoggerFactory.getLogger(CCValidateDAOImpl.class);

    private final JdbcTemplate postgresJdbcTemplateRepo;

    public CCValidateDAOImpl(DataSource pgDataSource) {
        this.postgresJdbcTemplateRepo = new JdbcTemplate(pgDataSource);
    }

    @Override
    public boolean doesCCExistsInDB(String ccNumber) {
        if (ccNumber == null || !ccNumber.matches("\\d+")) {
            return false;
        }

        try {
            logger.info("Checking credit card existence in DAO layer: {}", maskCcNumber(ccNumber));
            Integer count = postgresJdbcTemplateRepo.queryForObject(
                    "SELECT count(*) FROM creditcards WHERE ccnumber =?",
                    Integer.class,
                    Long.parseLong(ccNumber)
            );
            return count != null && count > 0;
        } catch (DataAccessException exception) {
            logger.warn("Unable to check credit card existence: {}", maskCcNumber(ccNumber), exception);
            return false;
        }
    }

    private static String maskCcNumber(String ccNumber) {
        if (ccNumber == null || ccNumber.length() < 4) {
            return "****";
        }
        return "****" + ccNumber.substring(ccNumber.length() - 4);
    }
}
