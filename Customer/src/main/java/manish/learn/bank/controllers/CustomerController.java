package manish.learn.bank.controllers;

import jakarta.validation.Valid;
import manish.learn.bank.exceptions.CustomerAlreadyExistsException;
import manish.learn.bank.exceptions.CustomerNotFoundException;
import manish.learn.bank.model.Customer;
import manish.learn.bank.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/customer")
@CrossOrigin(maxAge = 3600)
public class CustomerController {

    Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    CustomerService customerService;

    @RequestMapping(path = "/createCustomer", method = RequestMethod.POST)
    public ResponseEntity<Customer> createCustomer(@Valid @RequestBody Customer customer) throws CustomerAlreadyExistsException {
        logger.info("CustomerId at CustomerController Layer = {}", customer.getCustId());
        Customer addedCustomer = customerService.createCustomer(customer);
        return new ResponseEntity<>(addedCustomer, HttpStatus.CREATED);
    }

    @RequestMapping(path = "/getCustomerById/{customerIdentityNumber}", method = RequestMethod.GET)
    public ResponseEntity<?> readCustomerById(@PathVariable("customerIdentityNumber") Long personId) throws CustomerNotFoundException {
        logger.info("Inside readCustomerById method of CustomerController");
        return new ResponseEntity<>(customerService.findCustomerById(personId), HttpStatus.OK);
    }

    @RequestMapping(path = "/listAllCustomers", method = RequestMethod.GET)
    public ResponseEntity<List<Customer>> readAllCustomers() throws Exception {
        logger.info("Inside readAllCustomers method of CustomerController");
        List<Customer> customerList = customerService.findAllCustomers();
        return new ResponseEntity<>(customerList, HttpStatus.OK);
    }

    @RequestMapping(path = "/updateCustomer", method = RequestMethod.PUT)
    public ResponseEntity<Customer> updateCustomer(@RequestBody Customer customer) throws CustomerNotFoundException {
        logger.info("Inside updateCustomer method of CustomerController");
        Customer customer1 = customerService.updateCustomer(customer);
        return new ResponseEntity<>(customer1, HttpStatus.OK);
    }

    @RequestMapping(path = "/deleteCustomer/{customerId}", method = RequestMethod.DELETE)
    public ResponseEntity<Customer> deleteCustomer(@PathVariable("customerId") Long customerId) throws CustomerNotFoundException {
        logger.info("Inside deleteCustomer method of CustomerController");
        Customer deletedCustomer = customerService.deleteCustomer(customerId);
        logger.info("Customer successfully deleted from database");
        return new ResponseEntity<>(deletedCustomer, HttpStatus.OK);
    }

}
