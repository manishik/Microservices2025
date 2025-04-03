package manish.learn.bank.service;

import manish.learn.bank.database.BankRepository;
import manish.learn.bank.exceptions.BankAlreadyExistsException;
import manish.learn.bank.exceptions.BankNotFoundException;
import manish.learn.bank.model.Bank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BankServiceImpl implements BankService {

    Logger logger = LoggerFactory.getLogger(BankServiceImpl.class);

    @Autowired
    private BankRepository bankRepository;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Throwable.class })
    public Bank createBank(Bank bank) throws BankAlreadyExistsException {
        Bank bank1 = bankRepository.save(bank);
        //bankRepository.flush();
        return bank1;
    }


    public Bank findBankById(Long bankId) throws BankNotFoundException {
        logger.info("Inside findBankById method of BankController: BankId = " + bankId);
        Bank bank1 =  bankRepository.findByBankId(bankId);
        if (bank1 == null) {
            throw new BankNotFoundException();
        }
        logger.info("Inside findBankById method of BankController: BankName = " + bank1.getBankName());
        return bank1;
    }

    public List<Bank> findAllBanks() throws Exception {
        return (List<Bank>) bankRepository.findAll();
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Throwable.class })
    public Bank updateBank(Bank bank) throws BankNotFoundException {
        return bankRepository.save(bank);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Throwable.class })
    public Bank deleteBank(Long bankId) {
        Bank bank = new Bank();
        bank.setBankId(bankId);
        bankRepository.deleteById(bankId);
        return bank;
    }
}
