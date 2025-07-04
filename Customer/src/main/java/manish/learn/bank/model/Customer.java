package manish.learn.bank.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.NotNull;

@Setter
@Getter
@Entity
public class Customer {

    public Customer() {
    }

    public Customer(Long custid, String firstName, String middleName, String lastName, String email, Long phoneNumber, Long cellNumber, String address) {
        this.custId = custid;
        this.custFirstName = firstName;
        this.custMiddleName = middleName;
        this.custLastName = lastName;
        this.custEmail = email;
        this.custPhoneNumber = phoneNumber;
        this.custCellPhoneNumber = cellNumber;
        this.custAddress = address;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_seq")
    @SequenceGenerator(name = "customer_seq", sequenceName = "customer_seq", allocationSize = 1)
    @Column(name = "custid")
    private Long custId;

    @NotNull (message = "Cannot be null & minimum 2 characters")
    @Size(min=2, max=80)
    @Column(name = "custfirstname")
    private String custFirstName;

    @Column(name = "custmiddlename")
    private String custMiddleName;

    @NotNull(message = "Cannot be null & minimum 2 characters")
    @Size(min=2, max=80)
    @Column(name = "custlastname")
    private String custLastName;

    @Column(name = "custemail")
    private String custEmail;

    @Column(name = "custphonenumber")
    private Long custPhoneNumber;

    @Column(name = "custcellnumber")
    private Long custCellPhoneNumber;

    @Column(name = "custaddress")
    private String custAddress;
}
