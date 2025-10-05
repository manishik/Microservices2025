package manish.learn.bank.service;

import manish.learn.bank.entities.CustomerAccount;
import manish.learn.bank.exceptions.CustomerAlreadyExistsException;
import manish.learn.bank.exceptions.CustomerNotFoundException;
import manish.learn.bank.model.Customer;

import java.util.List;

public interface CustomerService {

    public Customer createCustomer(Customer customer) throws CustomerAlreadyExistsException;

    public Customer findCustomerByEmail(String customerEmail) throws CustomerNotFoundException;
    public List<Customer> findAllCustomers() throws Exception;
    public byte[] findAllCustomersFromDBasCSV() throws Exception;

    public Customer updateCustomer(Customer customer) throws CustomerNotFoundException;
    public Customer deleteCustomerByEmail(String customerEmail) throws CustomerNotFoundException;

    public CustomerAccount createAccount(CustomerAccount customerAccount);

}
