package learn.manish.creditCard.service;

import learn.manish.creditCard.model.CreditCard;

import java.util.List;

public interface CCService {

    public CreditCard validateCC(String ccNo) throws Exception;

    public int addCC(CreditCard creditCard) throws Exception;

    public CreditCard getCreditCardDetails(String ccNumber);

    public List<CreditCard> getAllCC();

    public CreditCard modifyCC(CreditCard creditCard) throws Exception;

    public int removeCC(String ccNo) throws Exception;

}
