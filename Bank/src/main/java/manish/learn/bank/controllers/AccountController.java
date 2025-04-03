package manish.learn.bank.controllers;

import manish.learn.bank.exceptions.AccountAlreadyExistsException;
import manish.learn.bank.exceptions.AccountNotFoundException;
import manish.learn.bank.model.Account;
import manish.learn.bank.service.AccountService;
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
@RequestMapping(path = "/account")
public class AccountController {

    Logger logger = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    AccountService accountService;

    @RequestMapping(path = "/createAccount", method = RequestMethod.POST)
    public ResponseEntity<Account> createAccount(@RequestBody Account account) throws AccountAlreadyExistsException {
        logger.info("AccountId at AccountController Layer = {}", account.getAccId());
        Account addedAccount = accountService.createAccount(account);
        return new ResponseEntity<>(addedAccount, HttpStatus.CREATED);
    }

    @RequestMapping(path = "/getAccountById/{AccountIdentityNumber}", method = RequestMethod.GET)
    public ResponseEntity<?> readAccountById(@PathVariable("AccountIdentityNumber") Long accountId) throws AccountNotFoundException {
        logger.info("Inside readAccountById method of AccountController");
        return new ResponseEntity<>(accountService.findAccountById(accountId), HttpStatus.OK);
    }

    @RequestMapping(path = "/listAllAccounts", method = RequestMethod.GET)
    public ResponseEntity<List<Account>> readAllAccounts() throws Exception {
        logger.info("Inside readAllAccounts method of AccountController");
        List<Account> AccountList = accountService.findAllAccounts();
        return new ResponseEntity<>(AccountList, HttpStatus.OK);
    }

    @RequestMapping(path = "/updateAccount", method = RequestMethod.PUT)
    public ResponseEntity<Account> updateAccount(@RequestBody Account Account) throws AccountNotFoundException {
        logger.info("Inside updateAccount method of AccountController");
        Account Account1 = accountService.updateAccount(Account);
        return new ResponseEntity<>(Account1, HttpStatus.OK);
    }

    @RequestMapping(path = "/deleteAccount/{AccountId}", method = RequestMethod.DELETE)
    public ResponseEntity<Account> deleteAccount(@PathVariable("AccountId") Long accountId) throws AccountNotFoundException {
        logger.info("Inside deleteAccount method of AccountController");
        Account deletedAccount = accountService.deleteAccount(accountId);
        logger.info("Account successfully deleted from database");
        return new ResponseEntity<>(deletedAccount, HttpStatus.OK);
    }

}
