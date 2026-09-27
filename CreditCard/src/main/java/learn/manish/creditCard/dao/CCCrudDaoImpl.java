package learn.manish.creditCard.dao;

import learn.manish.creditCard.model.CreditCard;
import learn.manish.creditCard.exceptions.CCNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class CCCrudDaoImpl implements CCCrudDao {

    private static final Logger logger = LoggerFactory.getLogger(CCCrudDaoImpl.class);

    private final JdbcTemplate postgresJdbcTemplateRepo;

    public CCCrudDaoImpl(DataSource pgDataSource) {
        this.postgresJdbcTemplateRepo = new JdbcTemplate(pgDataSource);
    }

    @Override
    public int saveCC(CreditCard creditCard) {
        logger.info("Saving credit card in DAO layer: {}", maskCcNumber(creditCard.getCcNumber()));
        return postgresJdbcTemplateRepo.update("INSERT INTO creditcards (ccname, ccnumber, cctype) VALUES(?,?,?)",
                creditCard.getCcName(), Long.parseLong(creditCard.getCcNumber()), creditCard.getCcType());
    }

    @Override
    public CreditCard findCCById(String ccNumber) {
        logger.info("Finding credit card in DAO layer: {}", maskCcNumber(ccNumber));
        try {
            return postgresJdbcTemplateRepo.queryForObject("SELECT * FROM creditcards WHERE ccnumber =?",
                    BeanPropertyRowMapper.newInstance(CreditCard.class), Long.parseLong(ccNumber));
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<CreditCard> getAllCCDetails() {
        logger.info("getAllCCDetails: CCCrudDaoImpl Layer");
        return postgresJdbcTemplateRepo.query("SELECT * from creditcards", BeanPropertyRowMapper.newInstance(CreditCard.class));
    }

    @Override
    public CreditCard updateCCDetails(CreditCard creditCard) {
        logger.info("Updating credit card in DAO layer: {}", maskCcNumber(creditCard.getCcNumber()));
        int rowsUpdated = postgresJdbcTemplateRepo.update("UPDATE creditcards SET ccname=?, cctype=? WHERE ccnumber=?",
                creditCard.getCcName(), creditCard.getCcType(), Long.parseLong(creditCard.getCcNumber()));

        if (rowsUpdated == 0) {
            throw new CCNotFoundException();
        }

        creditCard.setMessage("Credit Card Details Updated Successfully");
        return creditCard;
    }

    @Override
    public int deleteCC(String ccNumber) {
        logger.info("Deleting credit card in DAO layer: {}", maskCcNumber(ccNumber));
        return postgresJdbcTemplateRepo.update("DELETE FROM creditcards WHERE ccnumber=?", Long.parseLong(ccNumber));
    }

    private static String maskCcNumber(String ccNumber) {
        if (ccNumber == null || ccNumber.length() < 4) {
            return "****";
        }
        return "****" + ccNumber.substring(ccNumber.length() - 4);
    }
}
