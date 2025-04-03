package learn.manish.creditCard.dao;

import learn.manish.creditCard.model.CreditCard;

import java.util.List;

public interface CCCrudDao {

    public int saveCC(CreditCard creditCard);

    public CreditCard findCCById(String ccNumber);

    public List<CreditCard> getAllCCDetails();

    public CreditCard updateCCDetails(CreditCard creditCard);

    public int deleteCC(String ccNumber);
}
