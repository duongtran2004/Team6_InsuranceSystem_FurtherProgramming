package Entity;

import jakarta.persistence.*;

import java.sql.Date;
import java.util.Collection;

/**
 * @author
 * @version ${}
 * @created 25/04/2024 12:59
 * @project InsuranceManagementApplication
 */
@Entity
@Table(name = "INSURANCE_CARD", schema = "public", catalog = "postgres")
public class InsuranceCard {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @OneToMany(mappedBy = "insuranceCardByCardNumber")
    private Collection<Beneficiaries> beneficiariesByCardNumber;
    @OneToMany(mappedBy = "insuranceCardByCardNumber")
    private Collection<Claim> claimsByCardNumber;
    @ManyToOne
    @JoinColumn(name = "card_holder_id", referencedColumnName = "id", nullable = false)
    private Beneficiaries beneficiariesByCardHolderId;

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

    public String getCardHolderId() {
        return cardHolderId;
    }

    public void setCardHolderId(String cardHolderId) {
        this.cardHolderId = cardHolderId;
    }

    public String getPolicyOwnerId() {
        return policyOwnerId;
    }

    public void setPolicyOwnerId(String policyOwnerId) {
        this.policyOwnerId = policyOwnerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        InsuranceCard that = (InsuranceCard) o;

        if (cardNumber != null ? !cardNumber.equals(that.cardNumber) : that.cardNumber != null) return false;
        if (expirationDate != null ? !expirationDate.equals(that.expirationDate) : that.expirationDate != null)
            return false;
        if (cardHolderId != null ? !cardHolderId.equals(that.cardHolderId) : that.cardHolderId != null) return false;
        if (policyOwnerId != null ? !policyOwnerId.equals(that.policyOwnerId) : that.policyOwnerId != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = cardNumber != null ? cardNumber.hashCode() : 0;
        result = 31 * result + (expirationDate != null ? expirationDate.hashCode() : 0);
        result = 31 * result + (cardHolderId != null ? cardHolderId.hashCode() : 0);
        result = 31 * result + (policyOwnerId != null ? policyOwnerId.hashCode() : 0);
        return result;
    }

    public Collection<Entity.Beneficiaries> getBeneficiariesByCardNumber() {
        return beneficiariesByCardNumber;
    }

    public void setBeneficiariesByCardNumber(Collection<Entity.Beneficiaries> beneficiariesByCardNumber) {
        this.beneficiariesByCardNumber = beneficiariesByCardNumber;
    }

    public Collection<Entity.Claim> getClaimsByCardNumber() {
        return claimsByCardNumber;
    }

    public void setClaimsByCardNumber(Collection<Entity.Claim> claimsByCardNumber) {
        this.claimsByCardNumber = claimsByCardNumber;
    }

    public Entity.Beneficiaries getBeneficiariesByCardHolderId() {
        return beneficiariesByCardHolderId;
    }

    public void setBeneficiariesByCardHolderId(Entity.Beneficiaries beneficiariesByCardHolderId) {
        this.beneficiariesByCardHolderId = beneficiariesByCardHolderId;
    }
}
