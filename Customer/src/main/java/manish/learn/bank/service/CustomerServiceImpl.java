package manish.learn.bank.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import manish.learn.bank.database.CustomerRepo;
import manish.learn.bank.database.CustomerRepository;
import manish.learn.bank.entities.CustomerAccount;
import manish.learn.bank.exceptions.CustomerAlreadyExistsException;
import manish.learn.bank.exceptions.CustomerNotFoundException;
import manish.learn.bank.feignClient.AccountRest;
import manish.learn.bank.model.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    AccountRest accountRest;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Throwable.class})
    public Customer createCustomer(Customer customer) throws CustomerAlreadyExistsException {
        Customer customer1 = customerRepository.save(customer);
        customerRepository.flush();
        return customer1;
    }


    public Customer findCustomerByEmail(String customerEmail) throws CustomerNotFoundException {
        Customer customer1 = customerRepository.findCustomerByCustEmail(customerEmail);
        return customer1;
    }

    public List<Customer> findAllCustomers() throws Exception {
        return customerRepository.findAll();
    }

    public byte[] findAllCustomersFromDBasCSV() throws Exception {
        logger.info("Inside findAllCustomersFromDBasCSV method of CustomerServiceImpl");
        return customerRepo.findAllCustomersFromDBasCSV();
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Throwable.class})
    public Customer updateCustomer(Customer customer) throws CustomerNotFoundException {
        return customerRepository.save(customer);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Throwable.class})
    public Customer deleteCustomerByEmail(String customerEmail) {
        return customerRepository.deleteCustomerByCustEmail(customerEmail);
        //return customer;
    }

    @CircuitBreaker(name = "createAccountCircuitBreaker", fallbackMethod = "createAccountFallback")
    public CustomerAccount createAccount(CustomerAccount customerAccount) {
        logger.info("Inside CustomerServiceImpl createAccountForCustomer");
        CustomerAccount customerAccountResponse = accountRest.createAccount(customerAccount);
        return customerAccountResponse;
    }

    // Fallback method must match signature and add Throwable as the last parameter
    public CustomerAccount createAccountFallback(CustomerAccount customerAccount, Throwable throwable) {
        logger.info("Inside createAccountFallback method...");
        CustomerAccount customerAccountResponse = customerAccount;
        return customerAccountResponse;
    }
}
