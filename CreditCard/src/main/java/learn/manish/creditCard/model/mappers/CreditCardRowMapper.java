package learn.manish.creditCard.model.mappers;

import learn.manish.creditCard.model.CreditCard;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CreditCardRowMapper implements RowMapper<CreditCard> {

    @Override
    public CreditCard mapRow(ResultSet rs, int rowNum) throws SQLException {
        CreditCard creditCard = new CreditCard();
        creditCard.setCcNumber(rs.getString("ccnumber"));
        creditCard.setCcName(rs.getString("ccname"));
        creditCard.setCcType(rs.getString("cctype"));
        creditCard.setCcExpiryDate(rs.getDate("ccexpirydate"));
        creditCard.setCcCVV(rs.getString("cccvv"));
        creditCard.setCreditLimit(rs.getInt("creditlimit"));
        creditCard.setAvailableCredit(rs.getInt("availablecredit"));
        return creditCard;
    }
}
