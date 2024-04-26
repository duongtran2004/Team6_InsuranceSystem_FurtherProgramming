package Entity;

import jakarta.persistence.*;

import java.sql.Date;
import java.util.Collection;

/**
 * @author
 * @version ${}
 * @created 25/04/2024 14:10
 * @project InsuranceManagementApplication
 */
@Entity
@Table(name = "insurance_card", schema = "public", catalog = "postgres")
public class InsuranceCard {
    @Id
    @Column(name = "card_number")
    private String cardNumber;
    @Basic
    @Column(name = "expiration_date")
    private Date expirationDate;
    @OneToMany(mappedBy = "insuranceCard")
    private Collection<Beneficiaries> listOfInsuredPeople;
    @OneToMany(mappedBy = "insuranceCard")
    private Collection<Claim> insuranceCard;
    @ManyToOne
    @JoinColumn(name = "card_holder_id", referencedColumnName = "id", nullable = false)
    private Beneficiaries cardHolder;
    @ManyToOne
    @JoinColumn(name = "policy_owner_id", referencedColumnName = "id", nullable = false)
    private PolicyOwner policyOwner;

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        InsuranceCard that = (InsuranceCard) o;

        if (cardNumber != null ? !cardNumber.equals(that.cardNumber) : that.cardNumber != null) return false;
        if (expirationDate != null ? !expirationDate.equals(that.expirationDate) : that.expirationDate != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = cardNumber != null ? cardNumber.hashCode() : 0;
        result = 31 * result + (expirationDate != null ? expirationDate.hashCode() : 0);
        return result;
    }

    public Collection<Beneficiaries> getListOfInsuredPeople() {
        return listOfInsuredPeople;
    }

    public void setListOfInsuredPeople(Collection<Beneficiaries> listOfInsuredPeople) {
        this.listOfInsuredPeople = listOfInsuredPeople;
    }

    public Collection<Claim> getInsuranceCard() {
        return insuranceCard;
    }

    public void setInsuranceCard(Collection<Claim> insuranceCard) {
        this.insuranceCard = insuranceCard;
    }

    public Beneficiaries getCardHolder() {
        return cardHolder;
    }

    public void setCardHolder(Beneficiaries cardHolder) {
        this.cardHolder = cardHolder;
    }

    public PolicyOwner getPolicyOwner() {
        return policyOwner;
    }

    public void setPolicyOwner(PolicyOwner policyOwner) {
        this.policyOwner = policyOwner;
    }
}
