package learn.manish.creditCard.dao;

import learn.manish.creditCard.model.CreditCard;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class CCCrudDaoImpl implements CCCrudDao {

    Logger logger = LoggerFactory.getLogger(CCCrudDaoImpl.class);

    private int saveCCmethodCallCounter, findCCByIdmethodCallCounter,
            getAllCCDetailsmethodCallCounter, modifyCCDetailsmethodCallCounter, removeCCmethodCallCounter = 0;

    private JdbcTemplate postgresJdbcTemplateRepo;

    @Autowired
    public void init(DataSource pgDataSource) {
        this.postgresJdbcTemplateRepo = new JdbcTemplate(pgDataSource);
    }

    public int saveCC(CreditCard creditCard) {
        logger.info("saveCC: CCCrudDaoImpl Layer, CCNumber = {}", creditCard.getCcNumber());
        saveCCmethodCallCounter++;
        logger.info("Method saveCC is called " + saveCCmethodCallCounter + " times...");
        return postgresJdbcTemplateRepo.update("INSERT INTO creditcards (ccname, ccnumber, cctype) VALUES(?,?,?)",
                new Object[]{creditCard.getCcName(), Long.parseLong(creditCard.getCcNumber()), creditCard.getCcType()});
    }

    public CreditCard findCCById(String ccNumber) {
        logger.info("findCCById: CCCrudDaoImpl Layer, CCNumber = {}", ccNumber);
        findCCByIdmethodCallCounter++;
        logger.info("Method findCCById is called " + findCCByIdmethodCallCounter + " times...");
        try {
            return postgresJdbcTemplateRepo.queryForObject("SELECT * FROM creditcards WHERE ccnumber =?",
                    BeanPropertyRowMapper.newInstance(CreditCard.class), Long.parseLong(ccNumber));
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }

    public List<CreditCard> getAllCCDetails() {
        logger.info("getAllCCDetails: CCCrudDaoImpl Layer");
        getAllCCDetailsmethodCallCounter++;
        logger.info("Method getAllCCDetails is called " + getAllCCDetailsmethodCallCounter + " times...");
        return postgresJdbcTemplateRepo.query("SELECT * from creditcards", BeanPropertyRowMapper.newInstance(CreditCard.class));
    }

    public CreditCard updateCCDetails(CreditCard creditCard) {
        logger.info("updateCCDetails: CCCrudDaoImpl Layer..");
        modifyCCDetailsmethodCallCounter++;
        logger.info("Method updateCCDetails is called " + modifyCCDetailsmethodCallCounter + " times...");
        try {
            CreditCard creditCardFromDB = creditCard;
            postgresJdbcTemplateRepo.update("UPDATE creditcards SET ccname=?, cctype=? WHERE ccnumber=?",
                    new Object[]{creditCard.getCcName(), creditCard.getCcType(), Long.parseLong(creditCard.getCcNumber())});
            creditCardFromDB.setMessage(creditCard.getCcNumber() + " is updated Successfully");
            return creditCardFromDB;
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }

    public int deleteCC(String ccNumber) {
        logger.info("deleteCC: CCCrudDaoImpl Layer..");
        removeCCmethodCallCounter++;
        logger.info("Method deleteCC is called " + removeCCmethodCallCounter + " times...");
        try {
            //CreditCardDetails creditCardDetailsFromDB = creditCardDetails;
            return postgresJdbcTemplateRepo.update("DELETE FROM creditcards WHERE ccnumber=?", Long.parseLong(ccNumber));
            //creditCardDetailsFromDB.setMessage(creditCardDetails.getCcNumber() + " is Deleted Successfully");
            //return creditCardDetailsFromDB;
        } catch (IncorrectResultSizeDataAccessException e) {
            return 0;
        }
    }
}
