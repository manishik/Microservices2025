package learn.manish.creditCard.ctrl;

import learn.manish.creditCard.exceptions.CCAlreadyExistsException;
import learn.manish.creditCard.exceptions.CCNotFoundException;
import learn.manish.creditCard.model.CreditCard;
import learn.manish.creditCard.service.CCService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/CrudCC")
public class CCCrudController {

    private static final Logger logger = LoggerFactory.getLogger(CCCrudController.class);

    private final CCService ccService;

    public CCCrudController(CCService ccService) {
        this.ccService = ccService;
    }

    @PostMapping(path = "/createCC")
    public ResponseEntity<CreditCard> createCC(@RequestBody CreditCard creditCard) {
        logger.info("Creating credit card: {}", maskCcNumber(creditCard.getCcNumber()));
        int success = ccService.addCC(creditCard);
        if (success == 1) { // Validated and successfully added
            creditCard.setMessage("Credit Card Number successfully added to database");
            return new ResponseEntity<>(creditCard, HttpStatus.CREATED);
        }
        //creditCardDetails.setMessage("Credit Card Already Exist in Database");
        //return new ResponseEntity<>(creditCardDetails, HttpStatus.CONFLICT);
        throw new CCAlreadyExistsException();
    }

    @GetMapping(path = "/getCCbyId/{ccNumber}")
    public ResponseEntity<CreditCard> getCC(@PathVariable("ccNumber") String ccNumber) {
        logger.info("Inside getCCbyId method of CCCrudController");
        CreditCard creditCard = ccService.getCreditCardDetails(ccNumber);
        return new ResponseEntity<>(creditCard, HttpStatus.OK);
    }

    @GetMapping(path = "/listAllCC")
    public ResponseEntity<List<CreditCard>> listAllCC() {
        logger.info("Inside listAllCC method of CCCrudController");
        List<CreditCard> creditCardList = ccService.getAllCC();
        return new ResponseEntity<>(creditCardList, HttpStatus.OK);
    }

    @PutMapping(path = "/updateCC")
    public ResponseEntity<CreditCard> updateCC(@RequestBody CreditCard creditCard) {
        logger.info("Inside updateCC method of CCCrudController");
        CreditCard cCDetails = ccService.modifyCC(creditCard);
        cCDetails.setMessage("Credit Card Details Updated Successfully..");
        return new ResponseEntity<>(cCDetails, HttpStatus.OK);
    }

    @DeleteMapping(path = "/delCC/{ccNumber}")
    public ResponseEntity<String> removeCC(@PathVariable("ccNumber") String ccNum) {
        logger.info("Deleting credit card: {}", maskCcNumber(ccNum));
        int success = ccService.removeCC(ccNum);
        if (success == 1) { // Validated and successfully deleted
            logger.info("Credit Card Number successfully deleted from database");
            return new ResponseEntity<>("Credit Card Number successfully deleted", HttpStatus.OK);
        }
        logger.info("Credit Card Number not found for deletion: {}", maskCcNumber(ccNum));
        throw new CCNotFoundException();
    }

    private static String maskCcNumber(String ccNumber) {
        if (ccNumber == null || ccNumber.length() < 4) {
            return "****";
        }
        return "****" + ccNumber.substring(ccNumber.length() - 4);
    }

    @DeleteMapping(path = "/clearCache")
    public ResponseEntity<String> clearCache() {
        logger.info("Received request to clear credit card caches");
        ccService.clearCache();
        return new ResponseEntity<>("Cache cleared successfully", HttpStatus.OK);
    }

}
