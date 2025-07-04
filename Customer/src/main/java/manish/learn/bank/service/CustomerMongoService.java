package manish.learn.bank.service;

import manish.learn.bank.exceptions.CustomerAlreadyExistsException;
import manish.learn.bank.exceptions.CustomerNotFoundException;
import manish.learn.bank.model.CustomerMongo;

import java.util.List;
import java.util.Optional;

public interface CustomerMongoService {

    public CustomerMongo createCustomer(CustomerMongo customer) throws CustomerAlreadyExistsException;

    public Optional<CustomerMongo> findCustomerById(Long customerId) throws CustomerNotFoundException;
    public List<CustomerMongo> findAllCustomers() throws Exception;

    public CustomerMongo updateCustomer(CustomerMongo customer) throws CustomerNotFoundException;
    public void deleteCustomer(Long customerId) throws CustomerNotFoundException;


}
