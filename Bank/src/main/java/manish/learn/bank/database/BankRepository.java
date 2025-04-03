package manish.learn.bank.database;

import manish.learn.bank.model.Bank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface BankRepository extends JpaRepository<Bank, Long> {

    Bank findByBankId(Long bankId);
}
