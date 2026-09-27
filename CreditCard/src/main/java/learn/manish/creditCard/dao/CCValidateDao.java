package learn.manish.creditCard.dao;

public interface CCValidateDao {

    boolean doesCCExistsInDB(String ccNumber);

}
