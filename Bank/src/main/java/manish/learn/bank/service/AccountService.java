package manish.learn.bank.service;

import manish.learn.bank.exceptions.AccountAlreadyExistsException;
import manish.learn.bank.exceptions.AccountNotFoundException;
import manish.learn.bank.model.Account;

import java.util.List;

public interface AccountService {

    public Account createAccount(Account account) throws AccountAlreadyExistsException;

    public Account findAccountById(Long bankId) throws AccountNotFoundException;
    public List<Account> findAllAccounts() throws Exception;


    public Account updateAccount(Account account) throws AccountNotFoundException;
    public Account deleteAccount(Long bankId) throws AccountNotFoundException;

}
