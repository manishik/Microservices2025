package manish.learn.bank.controllers;

import manish.learn.bank.exceptions.CustomerAlreadyExistsException;
import manish.learn.bank.exceptions.CustomerNotFoundException;
import manish.learn.bank.model.CustomerMongo;
import manish.learn.bank.model.CustomerLoginRequest;
import manish.learn.bank.service.CustomerMongoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/customerM")
public class CustomerMongoController {

    Logger logger = LoggerFactory.getLogger(CustomerMongoController.class);

    /*@Autowired
    PasswordEncoder passwordEncoder;*/

    @Autowired
    CustomerMongoService customerMongoService;

    @RequestMapping(path = "/createCustomer", method = RequestMethod.POST)
    public ResponseEntity<CustomerMongo> createCustomer(@RequestBody CustomerMongo customerMongo) throws CustomerAlreadyExistsException {
        logger.info("CustomerId at CustomerMongoController Layer = {}", customerMongo.getCustId());
        CustomerMongo addedCustomer = customerMongoService.createCustomer(customerMongo);
        if (addedCustomer != null) {
            logger.info("Adding New Customer");
            return new ResponseEntity<>(addedCustomer, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
        //return new ResponseEntity<>(addedCustomer, HttpStatus.CREATED);
    }

    @RequestMapping(path = "/getCustomerById/{customerIdentityNumber}", method = RequestMethod.GET)
    public ResponseEntity<?> readCustomerById(@PathVariable("customerIdentityNumber") Long custId) throws CustomerNotFoundException {
        logger.info("Inside readCustomerById method of CustomerMongoController");
        return new ResponseEntity<>(customerMongoService.findCustomerById(custId), HttpStatus.OK);
    }

    @RequestMapping(path = "/listAllCustomers", method = RequestMethod.GET)
    public ResponseEntity<List<CustomerMongo>> readAllCustomers() throws Exception {
        logger.info("Inside readAllCustomers method of CustomerMongoController");
        List<CustomerMongo> customerList = customerMongoService.findAllCustomers();
        return new ResponseEntity<>(customerList, HttpStatus.OK);
    }

    @RequestMapping(path = "/updateCustomer", method = RequestMethod.PUT)
    public ResponseEntity<CustomerMongo> updateCustomer(@RequestBody CustomerMongo customerMongo) throws CustomerNotFoundException {
        logger.info("Inside updateCustomer method of CustomerMongoController");
        CustomerMongo customer1 = customerMongoService.updateCustomer(customerMongo);
        return new ResponseEntity<>(customer1, HttpStatus.OK);
    }

    @RequestMapping(path = "/deleteCustomer/{customerId}", method = RequestMethod.DELETE)
    public ResponseEntity<CustomerMongo> deleteCustomer(@PathVariable("customerId") Long customerId) throws CustomerNotFoundException {
        logger.info("Inside deleteCustomer method of CustomerMongoController");
        customerMongoService.deleteCustomer(customerId);
        logger.info("Customer successfully deleted from database");
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @PostMapping("/customerSignIn")
    public ResponseEntity<String> signIn(@RequestBody CustomerLoginRequest customerLoginRequest) {
        String username = customerLoginRequest.getEmail();
        String password = customerLoginRequest.getPassword();

        // Retrieve the user from the database based on the email
        CustomerMongo customerMongo = customerMongoService.findCustomerByEmail(username);

        if (customerMongo == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid email or password");
        }

        // Verify the password
        /*if (!passwordEncoder.matches(password, customerMongo.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid email or password");
        }*/

        // You can generate a token here and return it as part of the response
        // For simplicity, let's just return a success message for now
        return ResponseEntity.ok("Login successful");
    }

}
