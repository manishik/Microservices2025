package manish.learn.bank.service;

import manish.learn.bank.database.AccountRepository;
import manish.learn.bank.exceptions.AccountAlreadyExistsException;
import manish.learn.bank.exceptions.AccountNotFoundException;
import manish.learn.bank.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Throwable.class })
    public Account createAccount(Account account) throws AccountAlreadyExistsException {
        Account account1= accountRepository.save(account);
        return account1;
    }


    public Account findAccountById(Long accountId) throws AccountNotFoundException {
        Account account =  accountRepository.findAccountByAccId(accountId);
        return account;
    }

    public List<Account> findAllAccounts() throws Exception {
        return (List<Account>) accountRepository.findAll();
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Throwable.class })
    public Account updateAccount(Account account) throws AccountNotFoundException {
        return accountRepository.save(account);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Throwable.class })
    public Account deleteAccount(Long accountId) {
        Account account = new Account();
        account.setAccId(accountId);
        accountRepository.deleteById(accountId);
        return account;
    }
}
