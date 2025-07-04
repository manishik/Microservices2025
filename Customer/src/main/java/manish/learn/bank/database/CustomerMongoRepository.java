package manish.learn.bank.database;

import manish.learn.bank.model.CustomerMongo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CustomerMongoRepository extends MongoRepository<CustomerMongo, String> {
}
