package manish.learn.bank.entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerAccount {
    private Long custId;
    private Long accId;
    private String accountName;
    private String accountType;

    @Override
    public String toString() {
        return super.toString();
    }
}
