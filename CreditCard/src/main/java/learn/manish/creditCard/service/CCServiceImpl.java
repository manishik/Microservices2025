package learn.manish.creditCard.service;


import learn.manish.creditCard.dao.CCCrudDao;
import learn.manish.creditCard.dao.CCValidateDao;
import learn.manish.creditCard.exceptions.CCInvalidException;
import learn.manish.creditCard.exceptions.CCNotFoundException;
import learn.manish.creditCard.model.CreditCard;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CCServiceImpl implements CCService {

    private static final String CC_VALIDATION_CACHE = "AppServiceCCValidation";
    private static final String CC_DETAILS_CACHE = "AppServiceCCDetails";
    private static final String CC_LIST_CACHE = "AppServiceCCList";
    private static final Logger logger = LoggerFactory.getLogger(CCServiceImpl.class);

    private final CCValidateDao ccValidateDao;

    private final CCCrudDao ccCrudDao;

    public CCServiceImpl(CCValidateDao ccValidateDao, CCCrudDao ccCrudDao) {
        this.ccValidateDao = ccValidateDao;
        this.ccCrudDao = ccCrudDao;
    }

    @Override
    @Cacheable(cacheNames = {CC_VALIDATION_CACHE}, key = "#ccNumber")
    public CreditCard validateCC(String ccNumber) {
        logger.info("Validating credit card in service layer: {}", maskCcNumber(ccNumber));
        CreditCard creditCard = new CreditCard();

        validateCreditCardNumberFormat(ccNumber);

        if (ccValidateDao.doesCCExistsInDB(ccNumber)) {
            logger.info("Credit Card number is valid");
            creditCard.setMessage("Credit Card number is valid & in database");
            creditCard.setCcNumber(ccNumber);
            return creditCard;
        } else {
            logger.info("validateCC: Credit Card Number not in database");
            throw new CCNotFoundException();
        }
    }

    @Override
    @Caching(evict = {
            @CacheEvict(cacheNames = {CC_DETAILS_CACHE, CC_VALIDATION_CACHE}, key = "#creditCard.ccNumber", condition = "#result == 1"),
            @CacheEvict(cacheNames = {CC_LIST_CACHE}, allEntries = true, condition = "#result == 1")
    })
    public int addCC(CreditCard creditCard) {
        logger.info("Inside addCC method of CCServiceImpl");
        validateCreditCard(creditCard);

        if (!ccValidateDao.doesCCExistsInDB(creditCard.getCcNumber())) {
            logger.info("Credit card number not in database, adding new Credit Card Number");
            return ccCrudDao.saveCC(creditCard);
        }

        //Credit card exists in database
        logger.info("Credit card number already exists in database");
        return 0;
    }

    @Override
    @Cacheable(cacheNames = {CC_DETAILS_CACHE}, key = "#ccNumber")
    public CreditCard getCreditCardDetails(String ccNumber) throws CCNotFoundException {
        logger.info("Inside getCC method of CCServiceImpl");
        validateCreditCardNumberFormat(ccNumber);

        CreditCard creditCard = ccCrudDao.findCCById(ccNumber);
        if (creditCard != null) {
            creditCard.setMessage("Credit Card Number found in database");
            return creditCard;
        }
        throw new CCNotFoundException();
    }

    @Override
    @Cacheable(cacheNames = {CC_LIST_CACHE}, key = "'all'")
    public List<CreditCard> getAllCC() {
        logger.info("Inside getAllCC method of CCServiceImpl");
        return ccCrudDao.getAllCCDetails();
    }

    @Override
    @Caching(
            put = {
                    @CachePut(cacheNames = {CC_DETAILS_CACHE}, key = "#creditCard.ccNumber")
            },
            evict = {
                    @CacheEvict(cacheNames = {CC_LIST_CACHE}, allEntries = true)
            }
    )
    public CreditCard modifyCC(CreditCard creditCard) {
        logger.info("Inside modifyCC method of CCServiceImpl");
        validateCreditCard(creditCard);
        validateCC(creditCard.getCcNumber()); //Validate if CCNumber exists in DB
        //Credit card exists in database, so go ahead and update the credit card details
        logger.info("Credit card number exists in database, updating Credit Card Details");
        return ccCrudDao.updateCCDetails(creditCard);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(cacheNames = {CC_DETAILS_CACHE, CC_VALIDATION_CACHE}, key = "#ccNumber"),
            @CacheEvict(cacheNames = {CC_LIST_CACHE}, allEntries = true)
    })
    public int removeCC(String ccNumber) {
        logger.info("Inside removeCC method of CCServiceImpl");
        validateCC(ccNumber); //Validate if CCNumber exists in DB
        //Credit card exists in database, so go ahead and delete the credit card
        logger.info("Credit card number exists in database, Deleting Credit Card Number");
        return ccCrudDao.deleteCC(ccNumber);
    }

    @Override
    @CacheEvict(cacheNames = {CC_DETAILS_CACHE, CC_VALIDATION_CACHE, CC_LIST_CACHE}, allEntries = true, beforeInvocation = true)
    public void clearCache() {
        logger.info("Clearing credit card caches...");
    }

    private static void validateCreditCard(CreditCard creditCard) {
        if (creditCard == null) {
            throw new CCInvalidException("Credit card details are required");
        }
        validateCreditCardNumberFormat(creditCard.getCcNumber());
    }

    private static void validateCreditCardNumberFormat(String ccNumber) {
        if (ccNumber == null || !ccNumber.matches("\\d{16}")) {
            throw new CCInvalidException("Credit Card number has to be 16 digits");
        }
    }

    private static String maskCcNumber(String ccNumber) {
        if (ccNumber == null || ccNumber.length() < 4) {
            return "****";
        }
        return "****" + ccNumber.substring(ccNumber.length() - 4);
    }

}
