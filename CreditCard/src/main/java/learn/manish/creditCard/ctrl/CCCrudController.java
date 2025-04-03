package learn.manish.creditCard.ctrl;

import learn.manish.creditCard.exceptions.CCAlreadyExistsException;
import learn.manish.creditCard.exceptions.CCNotFoundException;
import learn.manish.creditCard.model.CreditCard;
import learn.manish.creditCard.service.CCService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/CrudCC")
public class CCCrudController {

    Logger logger = LoggerFactory.getLogger(CCCrudController.class);

    @Autowired
    CCService ccService;

    @RequestMapping(path = "/createCC", method = RequestMethod.POST)
    public ResponseEntity<CreditCard> createCC(@RequestBody CreditCard creditCard) throws Exception {
        logger.info("Credit Card Number at CCCrudController Layer = {}", creditCard.getCcNumber());
        CreditCard creditCard1 = creditCard;
        int success = ccService.addCC(creditCard);
        if (success == 1) { // Validated and successfully added
            creditCard1.setMessage("Credit Card Number successfully added to database");
            return new ResponseEntity<>(creditCard1, HttpStatus.CREATED);
        }
        //creditCardDetails.setMessage("Credit Card Already Exist in Database");
        //return new ResponseEntity<>(creditCardDetails, HttpStatus.CONFLICT);
        throw new CCAlreadyExistsException();
    }

    @RequestMapping(path = "/getCCbyId/{ccNumber}", method = RequestMethod.GET)
    public ResponseEntity<?> getCC(@PathVariable("ccNumber") String ccNumber) throws CCNotFoundException {
        logger.info("Inside getCCbyId method of CCCrudController");
        CreditCard creditCard = ccService.getCreditCardDetails(ccNumber);
        return new ResponseEntity<>(creditCard, HttpStatus.OK);
    }

    @RequestMapping(path = "/listAllCC", method = RequestMethod.GET)
    public ResponseEntity<List<CreditCard>> listAllCC() {
        logger.info("Inside listAllCC method of CCCrudController");
        List<CreditCard> creditCardList = ccService.getAllCC();
        return new ResponseEntity<>(creditCardList, HttpStatus.OK);
    }

    @RequestMapping(path = "/updateCC", method = RequestMethod.PUT)
    public ResponseEntity<CreditCard> updateCC(@RequestBody CreditCard creditCard) throws Exception {
        logger.info("Inside updateCC method of CCCrudController");
        CreditCard cCDetails = ccService.modifyCC(creditCard);
        cCDetails.setMessage("Credit Card Details Updated Successfully..");
        return new ResponseEntity<>(cCDetails, HttpStatus.OK);
    }

    @RequestMapping(path = "/delCC/{ccNumber}", method = RequestMethod.DELETE)
    public ResponseEntity<String> removeCC(@PathVariable("ccNumber") String ccNum) throws Exception {
        logger.info("Inside deleteCC method of CCCrudController");
        int success = ccService.removeCC(ccNum);
        if (success == 1) { // Validated and successfully deleted
            logger.info("Credit Card Number successfully deleted from database");
            return new ResponseEntity<>("Credit Card Number successfully deleted", HttpStatus.OK);
        }
        //throw new CCNotFoundException();
        logger.info("Not Reachable Code... ");
        return new ResponseEntity<>("", HttpStatus.CONFLICT);
    }

    // Evict all cache entries
    /*@CacheEvict(value = "CreditCardDetails", allEntries = true)
    @RequestMapping(path = "/clearCache", method = RequestMethod.GET)
    public String clearCache() {
        logger.info("Clearing entire cache...");
        return "Cache cleared successfully";
    }*/

}
