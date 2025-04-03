package learn.manish.creditCard.service;


import learn.manish.creditCard.dao.CCCrudDao;
import learn.manish.creditCard.dao.CCValidateDao;
import learn.manish.creditCard.exceptions.CCInvalidException;
import learn.manish.creditCard.exceptions.CCNotFoundException;
import learn.manish.creditCard.model.CreditCard;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CCServiceImpl implements CCService {

    Logger logger = LoggerFactory.getLogger(CCServiceImpl.class);

    @Autowired
    CCValidateDao ccValidateDao;

    @Autowired
    CCCrudDao ccCrudDao;

    //@Cacheable(value = "AppServiceCCDetails", key = "#ccNo") //ClassCastException is being thrown
    public CreditCard validateCC(String ccNo) throws Exception {
        logger.info("Credit Card Number inside validateCC method of CCServiceImpl Layer = {}", ccNo);
        CreditCard creditCard = new CreditCard();

        if (ccNo.length() < 16 || ccNo.length() > 16) {
            throw new CCInvalidException("Credit Card number has to be 16 digits");
        }

        if (ccValidateDao.doesCCExistsInDB(ccNo)) {
            logger.info("Credit Card number is valid");
            creditCard.setMessage("Credit Card number is valid & in database");
            creditCard.setCcNumber(ccNo);
            return creditCard;
        } else {
            logger.info("validateCC: Credit Card Number not in database");
            throw new CCNotFoundException();
        }
    }

    @CachePut(value = "AppServiceCCDetails", key = "#creditCard.ccNumber")
    public int addCC(CreditCard creditCard) throws Exception {
        logger.info("Inside addCC method of CCServiceImpl");
        // Validate of Credit card is already in database
        try {
            validateCC(creditCard.getCcNumber());
        } catch (CCNotFoundException ccNotFoundException) {
            logger.info("Credit card number not in database, adding new Credit Card Number");
            return ccCrudDao.saveCC(creditCard);
        }
        //Credit card exists in database
        logger.info("Credit card number already exists in database");
        return 0;
    }

    //@Cacheable(value = "AppServiceCCDetails", key = "#ccNumber") //ClassCastException is being thrown
    public CreditCard getCreditCardDetails(String ccNumber) throws CCNotFoundException {
        logger.info("Inside getCC method of CCServiceImpl");
        CreditCard creditCard = ccCrudDao.findCCById(ccNumber);
        if (creditCard != null) {
            creditCard.setMessage("Credit Card Number found in database");
            return creditCard;
        }
        throw new CCNotFoundException();
    }

    public List<CreditCard> getAllCC() {
        logger.info("Inside getAllCC method of CCServiceImpl");
        return ccCrudDao.getAllCCDetails();
    }

    @CachePut(value = "AppServiceCCDetails", key = "#creditCard.ccNumber")
    public CreditCard modifyCC(CreditCard creditCard) throws Exception {
        logger.info("Inside modifyCC method of CCServiceImpl");
        //CreditCardDetails creditCardDetails1 = validateCC(creditCardDetails.getCcNumber());
        try {
            validateCC(creditCard.getCcNumber()); //Validate if CCNumber exists in DB
        } catch (CCNotFoundException ccNotFoundException) {
            logger.info("Credit card number not in database");
            throw ccNotFoundException;
        }
        //Credit card exists in database, so go ahead and update the credit card details
        logger.info("Credit card number exists in database, updating Credit Card Details");
        return ccCrudDao.updateCCDetails(creditCard);
    }

    @CacheEvict(value = "AppServiceCCDetails", key = "#ccNumber")
    public int removeCC(String ccNumber) throws Exception {
        logger.info("Inside removeCC method of CCServiceImpl");
        try {
            validateCC(ccNumber); //Validate if CCNumber exists in DB
        } catch (CCNotFoundException ccNotFoundException) {
            logger.info("Credit card number not in database");
            throw ccNotFoundException;
        }
        //Credit card exists in database, so go ahead and delete the credit card
        logger.info("Credit card number exists in database, Deleting Credit Card Number");
        return ccCrudDao.deleteCC(ccNumber);
    }

}
