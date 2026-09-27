package learn.manish.creditCard.dao;

import learn.manish.creditCard.model.CreditCard;

import java.util.List;

public interface CCCrudDao {

    int saveCC(CreditCard creditCard);

    CreditCard findCCById(String ccNumber);

    List<CreditCard> getAllCCDetails();

    CreditCard updateCCDetails(CreditCard creditCard);

    int deleteCC(String ccNumber);
}
