package manish.learn.bank.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "Account")
public class Account {

    public Account() {
    }

    public Account(Long accId, String accountName, String accountType, Bank bank) {
        this.accId = accId;
        this.accountName = accountName;
        this.accountType = accountType;
        this.bank = bank;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_seq")
    @SequenceGenerator(name = "account_seq", sequenceName = "account_seq", allocationSize = 1)
    @Column(name = "accid")
    private Long accId;
    @Column(name = "accountname")
    private String accountName;
    @Column(name = "accounttype")
    private String accountType;

    @ManyToOne
    @JoinColumn(name = "bankid")
    @JsonBackReference
    private Bank bank;
}
