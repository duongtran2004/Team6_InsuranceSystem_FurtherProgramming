package Entity;

import jakarta.persistence.*;

import java.sql.Date;
import java.util.Collection;
import java.util.Objects;

/**
 * @author
 * @version ${}
 * @created 26/04/2024 10:58
 * @project InsuranceManagementTeamProject
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

    @Basic
    @Column(name = "card_holder_id")
    private String cardHolderId;

    @Basic
    @Column(name = "policy_owner_id")
    private String policyOwnerId;

    @OneToMany(mappedBy = "insuranceCard")
    private Collection<Beneficiaries> listOfBeneficiaries;
    @OneToMany(mappedBy = "insuranceCard")
    private Collection<Claim> listOfClaims;
    @ManyToOne
    @JoinColumn(name = "card_holder_id", referencedColumnName = "id")
    private Beneficiaries cardHolder;
    @ManyToOne
    @JoinColumn(name = "policy_owner_id", referencedColumnName = "id")
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
        return Objects.equals(cardNumber, that.cardNumber) && Objects.equals(expirationDate, that.expirationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cardNumber, expirationDate);
    }

    public Collection<Beneficiaries> getListOfBeneficiaries() {
        return listOfBeneficiaries;
    }

    public void setListOfBeneficiaries(Collection<Beneficiaries> listOfBeneficiaries) {
        this.listOfBeneficiaries = listOfBeneficiaries;
    }

    public Collection<Claim> getListOfClaims() {
        return listOfClaims;
    }

    public void setListOfClaims(Collection<Claim> listOfClaims) {
        this.listOfClaims = listOfClaims;
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
