package learn.manish.creditCard.model;

import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class CreditCard {

    private String ccNumber;
    private String ccType;
    private String ccName;
    private Date ccExpiryDate;
    private String ccCVV;
    private int creditLimit;
    private int availableCredit;
    private String message;

    public String getCcNumber() {
        return ccNumber;
    }

    public void setCcNumber(String ccNumber) {
        this.ccNumber = ccNumber;
    }

    public String getCcType() {
        return ccType;
    }

    public void setCcType(String ccType) {
        this.ccType = ccType;
    }

    public String getCcName() {
        return ccName;
    }

    public void setCcName(String ccName) {
        this.ccName = ccName;
    }

    public Date getCcExpiryDate() {
        return ccExpiryDate;
    }

    public void setCcExpiryDate(Date ccExpiryDate) {
        this.ccExpiryDate = ccExpiryDate;
    }

    public String getCcCVV() {
        return ccCVV;
    }

    public void setCcCVV(String ccCVV) {
        this.ccCVV = ccCVV;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(int creditLimit) {
        this.creditLimit = creditLimit;
    }

    public int getAvailableCredit() {
        return availableCredit;
    }

    public void setAvailableCredit(int availableCredit) {
        this.availableCredit = availableCredit;
    }

}
