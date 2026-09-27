package learn.manish.creditCard.service;

import learn.manish.creditCard.model.CreditCard;

import java.util.List;

public interface CCService {

    CreditCard validateCC(String ccNo);

    int addCC(CreditCard creditCard);

    CreditCard getCreditCardDetails(String ccNumber);

    List<CreditCard> getAllCC();

    CreditCard modifyCC(CreditCard creditCard);

    int removeCC(String ccNo);

    void clearCache();

}
