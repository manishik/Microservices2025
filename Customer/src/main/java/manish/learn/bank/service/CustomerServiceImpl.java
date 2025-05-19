package manish.learn.bank.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
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
    AccountRest accountRest;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Throwable.class })
    public Customer createCustomer(Customer customer) throws CustomerAlreadyExistsException {
        Customer customer1 = customerRepository.save(customer);
        customerRepository.flush();
        return customer1;
    }


    public Customer findCustomerById(Long customerId) throws CustomerNotFoundException {
        Customer customer1 =  customerRepository.findCustomerByCustId(customerId);
        return customer1;
    }

    public List<Customer> findAllCustomers() throws Exception {
        return customerRepository.findAll();
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Throwable.class })
    public Customer updateCustomer(Customer customer) throws CustomerNotFoundException {
        return customerRepository.save(customer);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Throwable.class })
    public Customer deleteCustomer(Long customerId) {
        Customer customer = new Customer();
        customer.setCustId(customerId);
        customerRepository.deleteById(customerId);
        return customer;
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
