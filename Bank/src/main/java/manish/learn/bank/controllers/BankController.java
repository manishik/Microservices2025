package manish.learn.bank.controllers;

import manish.learn.bank.exceptions.BankAlreadyExistsException;
import manish.learn.bank.exceptions.BankNotFoundException;
import manish.learn.bank.model.Bank;
import manish.learn.bank.service.BankService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/bank")
public class BankController {

    Logger logger = LoggerFactory.getLogger(BankController.class);

    @Autowired
    BankService bankService;

    @RequestMapping(path = "/createBank", method = RequestMethod.POST)
    public ResponseEntity<Bank> createBank(@RequestBody Bank bank) throws BankAlreadyExistsException {
        logger.info("BankId at BankController Layer = {}", bank.getBankId());
        Bank addedBank = bankService.createBank(bank);
        return new ResponseEntity<>(addedBank, HttpStatus.CREATED);
    }

    @RequestMapping(path = "/getBankById/{bankIdentityNumber}", method = RequestMethod.GET)
    public ResponseEntity<?> readBankById(@PathVariable("bankIdentityNumber") Long bankId) throws BankNotFoundException {
        logger.info("Inside readBankById method of BankController");
        return new ResponseEntity<>(bankService.findBankById(bankId), HttpStatus.OK);
    }

    @RequestMapping(path = "/listAllBanks", method = RequestMethod.GET)
    public ResponseEntity<List<Bank>> readAllBanks() throws Exception {
        logger.info("Inside readAllBanks method of BankController");
        List<Bank> bankList = bankService.findAllBanks();
        return new ResponseEntity<>(bankList, HttpStatus.OK);
    }

    @RequestMapping(path = "/updateBank", method = RequestMethod.PUT)
    public ResponseEntity<Bank> updateBank(@RequestBody Bank bank) throws BankNotFoundException {
        logger.info("Inside updateBank method of BankController");
        Bank bank1 = bankService.updateBank(bank);
        return new ResponseEntity<>(bank1, HttpStatus.OK);
    }

    @RequestMapping(path = "/deleteBank/{bankId}", method = RequestMethod.DELETE)
    public ResponseEntity<Bank> deleteBank(@PathVariable("bankId") Long bankId) throws BankNotFoundException {
        logger.info("Inside deleteBank method of BankController");
        Bank deletedBank = bankService.deleteBank(bankId);
        logger.info("Bank successfully deleted from database");
        return new ResponseEntity<>(deletedBank, HttpStatus.OK);
    }

}
