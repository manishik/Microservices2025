package manish.learn.bank.database;

import manish.learn.bank.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Customer findCustomerByCustId(Long custId);

}
