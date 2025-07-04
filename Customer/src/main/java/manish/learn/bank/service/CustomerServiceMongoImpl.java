package manish.learn.bank.service;

import manish.learn.bank.database.CustomerMongoRepository;
import manish.learn.bank.exceptions.CustomerAlreadyExistsException;
import manish.learn.bank.exceptions.CustomerNotFoundException;
import manish.learn.bank.model.CustomerMongo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceMongoImpl implements CustomerMongoService {

    Logger logger = LoggerFactory.getLogger(CustomerServiceMongoImpl.class);

    @Autowired
    private CustomerMongoRepository customerMongoRepository;

    @Override
    public CustomerMongo createCustomer(CustomerMongo customerMongo) throws CustomerAlreadyExistsException {
        return customerMongoRepository.save(customerMongo);
    }

    @Override
    public Optional<CustomerMongo> findCustomerById(Long customerId) throws CustomerNotFoundException {
        return customerMongoRepository.findById(customerId.toString());
    }

    @Override
    public List<CustomerMongo> findAllCustomers() throws Exception {
        return customerMongoRepository.findAll();
    }

    @Override
    public CustomerMongo updateCustomer(CustomerMongo customerMongo) throws CustomerNotFoundException {
        return customerMongoRepository.save(customerMongo);
    }

    @Override
    public void deleteCustomer(Long customerId) throws CustomerNotFoundException {
        customerMongoRepository.deleteById(customerId.toString());
    }
}
