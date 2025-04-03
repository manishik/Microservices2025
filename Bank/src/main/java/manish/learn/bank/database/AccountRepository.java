package manish.learn.bank.database;

import manish.learn.bank.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Account findAccountByAccId(Long accId);

}
