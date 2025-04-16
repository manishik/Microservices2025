package manish.learn.bank.entities;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class CustomerAccount {
    // Linking Customer with Customer Id
    private Long custId;

    // Account Details below
    private Long accId;
    private String accountName;
    private String accountType;

    private double amount;

    private Date createDate;

    private Date trasanctionDate;

    private Date endDate;

    @Override
    public String toString() {
        return super.toString();
    }
}
