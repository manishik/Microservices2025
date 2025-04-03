package manish.learn.bank.service;

import manish.learn.bank.exceptions.BankAlreadyExistsException;
import manish.learn.bank.exceptions.BankNotFoundException;
import manish.learn.bank.model.Bank;

import java.util.List;

public interface BankService {

    public Bank createBank(Bank bank) throws BankAlreadyExistsException;

    public Bank findBankById(Long bankId) throws BankNotFoundException;
    public List<Bank> findAllBanks() throws Exception;


    public Bank updateBank(Bank bank) throws BankNotFoundException;
    public Bank deleteBank(Long bankId) throws BankNotFoundException;

}
