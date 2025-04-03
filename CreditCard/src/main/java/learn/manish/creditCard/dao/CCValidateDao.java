package learn.manish.creditCard.dao;

public interface CCValidateDao {

    public boolean doesCCExistsInDB(String CC) throws Exception;

}
