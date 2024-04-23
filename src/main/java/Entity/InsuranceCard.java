package Entity;

import jakarta.persistence.*;

import java.sql.Date;
import java.util.Collection;

/**
 * @author
 * @version ${}
 * @created 24/04/2024 05:08
 * @project InsuranceManagementApplication
 */
@Entity
public class InsuranceCard {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "CardNumber")
    private String cardNumber;
    @Basic
    @Column(name = "ExpiryDate")
    private Date expiryDate;
    @OneToMany(mappedBy = "insuranceCard")
    private Collection<Beneficiary> insuredPeople;
    @ManyToOne
    @JoinColumn(name = "InsuredPersonID", referencedColumnName = "ID")
    private Beneficiary cardHolder;
    @ManyToOne
    @JoinColumn(name = "PolicyOwnerID", referencedColumnName = "ID")
    private PolicyOwner policyOwner;

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        InsuranceCard that = (InsuranceCard) o;

        if (cardNumber != null ? !cardNumber.equals(that.cardNumber) : that.cardNumber != null) return false;
        if (expiryDate != null ? !expiryDate.equals(that.expiryDate) : that.expiryDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = cardNumber != null ? cardNumber.hashCode() : 0;
        result = 31 * result + (expiryDate != null ? expiryDate.hashCode() : 0);
        return result;
    }

    public Collection<Beneficiary> getInsuredPeople() {
        return insuredPeople;
    }

    public void setInsuredPeople(Collection<Beneficiary> insuredPeople) {
        this.insuredPeople = insuredPeople;
    }

    public Beneficiary getCardHolder() {
        return cardHolder;
    }

    public void setCardHolder(Beneficiary cardHolder) {
        this.cardHolder = cardHolder;
    }

    public PolicyOwner getPolicyOwner() {
        return policyOwner;
    }

    public void setPolicyOwner(PolicyOwner policyOwner) {
        this.policyOwner = policyOwner;
    }
}
