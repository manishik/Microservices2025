package manish.learn.bank.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "customeraddress")
public class CustomerAddress {

    public CustomerAddress() {
    }

    @Id
    @Column(name = "customeremail")
    private String customerEmail;

    @Column(name = "doorno")
    private Integer doorNo;

    @Column(name = "street1")
    private String street1;

    @Column(name = "street2")
    private String street2;

    @Column(name = "area")
    private String area;

    @Column(name = "zipcode")
    private Integer zipCode;

    @Column(name = "state")
    private String state;

    @Column(name = "country")
    private String country;

    @Column(name = "addresstype")
    private String addresstype;

    @Column(name = "phonenumber")
    private String phonenumber;

    @Column(name = "email")
    private String email;

    @JsonBackReference
    @MapsId
    @ManyToOne
    @JoinColumn(name = "customeremail", referencedColumnName="custemail")
    private Customer customer;

}
