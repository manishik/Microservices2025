package manish.learn.bank.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
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
/*import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;*/
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/customer")
//@CrossOrigin(maxAge = 3600)
public class CustomerController {

    Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    CustomerService customerService;

    @Autowired
    private ObjectMapper jacksonObjectMapper;

    @RequestMapping(path = "/createCustomer", method = RequestMethod.POST)
    public ResponseEntity<Customer> createCustomer(@Valid @RequestBody Customer customer) throws CustomerAlreadyExistsException {
        logger.info("CustomerEmailID at CustomerController Layer = {}", customer.getCustEmail());
        Customer addedCustomer = customerService.createCustomer(customer);
        return new ResponseEntity<>(addedCustomer, HttpStatus.CREATED);
    }

    @RequestMapping(path = "/getCustomerByEmail/{customerEmail}", method = RequestMethod.GET)
    public ResponseEntity<?> readCustomerByEmail(@PathVariable("customerEmail") String customerEmail) throws CustomerNotFoundException {
        logger.info("Inside readCustomerById method of CustomerController");
        return new ResponseEntity<>(customerService.findCustomerByEmail(customerEmail), HttpStatus.OK);
    }

    @RequestMapping(path = "/listAllCustomers", method = RequestMethod.GET)
    public ResponseEntity<List<Customer>> readAllCustomers() throws Exception {
        //Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //logger.info(jacksonObjectMapper.writeValueAsString(authentication));
        logger.info("Inside readAllCustomers method of CustomerController");
        List<Customer> customerList = customerService.findAllCustomers();
        return new ResponseEntity<>(customerList, HttpStatus.OK);
    }

    @RequestMapping(path = "/getAllCustomersCSVFile", method = RequestMethod.GET)
    public ResponseEntity<?> retrieveAllCustomersInCSVFile() throws Exception {
        logger.info("Inside retrieveAllCustomersInCSVFile method of CustomerController");
        byte[] customersZipFileByteArray = customerService.findAllCustomersFromDBasCSV();
        return new ResponseEntity<>(customersZipFileByteArray, HttpStatus.OK);
    }

    @RequestMapping(path = "/updateCustomer", method = RequestMethod.PUT)
    public ResponseEntity<Customer> updateCustomer(@RequestBody Customer customer) throws CustomerNotFoundException {
        logger.info("Inside updateCustomer method of CustomerController");
        Customer customer1 = customerService.updateCustomer(customer);
        return new ResponseEntity<>(customer1, HttpStatus.OK);
    }

    @RequestMapping(path = "/deleteCustomerByEmail/{customerEmail}", method = RequestMethod.DELETE)
    public ResponseEntity<Customer> deleteCustomerByEmail(@PathVariable("customerEmail") String customerEmail) throws CustomerNotFoundException {
        logger.info("Inside deleteCustomer method of CustomerController");
        Customer deletedCustomer = customerService.deleteCustomerByEmail(customerEmail);
        logger.info("Customer successfully deleted from database");
        return new ResponseEntity<>(deletedCustomer, HttpStatus.OK);
    }

}
