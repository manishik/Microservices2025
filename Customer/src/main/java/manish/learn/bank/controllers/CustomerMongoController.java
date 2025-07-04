package manish.learn.bank.controllers;

import manish.learn.bank.exceptions.CustomerAlreadyExistsException;
import manish.learn.bank.exceptions.CustomerNotFoundException;
import manish.learn.bank.model.CustomerMongo;
import manish.learn.bank.service.CustomerMongoService;
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
@RequestMapping(path = "/customerM")
public class CustomerMongoController {

    Logger logger = LoggerFactory.getLogger(CustomerMongoController.class);

    @Autowired
    CustomerMongoService customerMongoService;

    @RequestMapping(path = "/createCustomer", method = RequestMethod.POST)
    public ResponseEntity<CustomerMongo> createCustomer(@RequestBody CustomerMongo customerMongo) throws CustomerAlreadyExistsException {
        logger.info("CustomerId at CustomerMongoController Layer = {}", customerMongo.getCustId());
        CustomerMongo addedCustomer = customerMongoService.createCustomer(customerMongo);
        return new ResponseEntity<>(addedCustomer, HttpStatus.CREATED);
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

}
