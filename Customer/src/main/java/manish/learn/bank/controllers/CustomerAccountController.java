package manish.learn.bank.controllers;

import manish.learn.bank.entities.CustomerAccount;
import manish.learn.bank.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/customer")
public class CustomerAccountController {

    Logger logger = LoggerFactory.getLogger(CustomerAccountController.class);

    @Autowired
    CustomerService customerService;

    @RequestMapping(path = "/createAccountForCustomer", method = RequestMethod.POST)
    public ResponseEntity<CustomerAccount> createAccountForCustomer(@RequestBody CustomerAccount customerAccount) {
        logger.info("Inside RestController createAccountForCustomer");
        CustomerAccount customerAccountResp = customerService.createAccount(customerAccount);
        return ResponseEntity.ok(customerAccountResp);
    }

}
