package manish.learn.bank.service;

import manish.learn.bank.exceptions.CustomerAlreadyExistsException;
import manish.learn.bank.exceptions.CustomerNotFoundException;
import manish.learn.bank.model.Customer;

import java.util.List;

public interface CustomerService {

    public Customer createCustomer(Customer customer) throws CustomerAlreadyExistsException;

    public Customer findCustomerById(Long customerId) throws CustomerNotFoundException;
    public List<Customer> findAllCustomers() throws Exception;


    public Customer updateCustomer(Customer customer) throws CustomerNotFoundException;
    public Customer deleteCustomer(Long customerId) throws CustomerNotFoundException;

}
