package manish.learn.bank.database;

import manish.learn.bank.model.CustomerMongo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface CustomerMongoRepository extends MongoRepository<CustomerMongo, String> {

    @Query("{custEmail :?0}")
    CustomerMongo findCustomerByEmail(String email);
}
