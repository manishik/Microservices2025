package manish.learn.bank.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Entity
public class Customer {

    public Customer() {
    }

    @Id
    @Column(name = "custemail")
    private String custEmail;

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

    @Column(name = "custphonenumber")
    private Long custPhoneNumber;

    @Column(name = "custcellnumber")
    private Long custCellPhoneNumber;

    /*@Column(name = "custaddress")
    private String custAddress;*/

    @OneToMany
    @JoinColumn(name = "customeremail")
    private List<CustomerAddress> customerAddress;
}
