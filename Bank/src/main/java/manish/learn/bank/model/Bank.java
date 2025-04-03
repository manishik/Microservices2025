package manish.learn.bank.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "Bank")
public class Bank {

    public Bank() {
    }

    public Bank(Long bankId, String bankName, String bankEmail, Long bankPhoneNumber, String bankAddress, List<Account> accountList) {
        this.bankId = bankId;
        this.bankName = bankName;
        this.bankEmail = bankEmail;
        this.bankPhoneNumber = bankPhoneNumber;
        this.bankAddress = bankAddress;
        this.accountList = accountList;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "bankid")
    private Long bankId;
    @Column(name = "bankname")
    private String bankName;
    @Column(name = "bankemail")
    private String bankEmail;
    @Column(name = "bankphonenumber")
    private Long bankPhoneNumber;
    @Column(name = "bankaddress")
    private String bankAddress;

    @OneToMany(mappedBy = "bank")
    @JsonManagedReference
    private List<Account> accountList;

}
