package learn.manish.creditCard.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashSet;
import java.util.Set;

@Repository
public class CCValidateDAOImpl implements CCValidateDao {

    Logger logger = LoggerFactory.getLogger(CCValidateDAOImpl.class);

    // Local Set for adding valid CC's (no database)
    Set validCCs = new HashSet<String>();

    String sqlCCNumbers = "SELECT * FROM CREDITCARDS where CCNUMBER = ?";

    private JdbcTemplate postgresJdbcTemplateRepo;

    @Autowired
    public void init(DataSource pgDataSource) {
        this.postgresJdbcTemplateRepo = new JdbcTemplate(pgDataSource);
    }

    public boolean doesCCExistsInDB(String ccNumber) throws Exception {
        try {
            logger.info("Inside doesCCExistsInDB method of CCValidateDAOImpl, CCNumber = {}", ccNumber);
            //Remember below statement can return null if no rows matching.
            //return postgresJdbcTemplateRepo.queryForObject(sqlCCNumbers, new Object[]{CCNumber}, new CreditCardRowMapper());

            // If full information about CC is needed (whole object needs to be pulled from DB)
            //CreditCardDetails validCCDetails = postgresJdbcTemplateRepo.queryForObject("SELECT * FROM creditcards WHERE ccnumber =?",
            //        BeanPropertyRowMapper.newInstance(CreditCardDetails.class), CCNumber);
            //return validCCDetails;

            String sql = "SELECT count(*) FROM creditcards WHERE ccnumber =?";
            boolean result = false;

            Long longCcNumber = 0l;

            // Validation - CCnumber should only contain digits
            // \\d+ makes sure that only digits are accepted
            if (ccNumber != null && ccNumber.matches("\\d+")) {
                longCcNumber = Long.parseLong(ccNumber);
            }

            int count = postgresJdbcTemplateRepo.queryForObject(sql, new Object[]{longCcNumber}, Integer.class);

            if (count > 0) {
                result = true;
            }
            return result;
        } catch (Exception exception) {
            logger.info(exception.getMessage());
            exception.printStackTrace();
            //Log invalid tries
            return false;
        }

        //Use below code to validate CC without hitting Database
        /*createValidCCs();   // Comment the try catch above to resolve this method
        if(validCCs.contains(CCNumber.toString())){
            //return "Valid Credit Card Number";
            logger.info("CC number is valid");
            return true;
        }
        logger.info("CC number is in-valid");
        return false;*/
    }

    // Hard coded CC values to validate without hitting database
    public void createValidCCs() {
        validCCs.add("0123456789012345");
        validCCs.add("1234567890123456");
        validCCs.add("2345678901234561");
        validCCs.add("3456789012345612");
        validCCs.add("4567890123456123");
        validCCs.add("5678901234561234");
    }
}
