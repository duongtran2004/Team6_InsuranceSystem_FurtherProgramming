package Entity;

import jakarta.persistence.*;

import java.sql.Date;

/**
 * @author
 * @version ${}
 * @created 25/04/2024 12:59
 * @project InsuranceManagementApplication
 */
@Entity
public class Claim {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "claim_id")
    private String claimId;
    @Basic
    @Column(name = "creation_date")
    private Date creationDate;
    @Basic
    @Column(name = "settlement_date")
    private Date settlementDate;
    @Basic
    @Column(name = "status")
    private String status;
    @Basic
    @Column(name = "claim_amount")
    private float claimAmount;
    @Basic
    @Column(name = "insured_person_id")
    private String insuredPersonId;
    @Basic
    @Column(name = "policy_owner_id")
    private String policyOwnerId;
    @Basic
    @Column(name = "card_number")
    private String cardNumber;
    @Basic
    @Column(name = "insurance_surveyor_id")
    private String insuranceSurveyorId;
    @Basic
    @Column(name = "insurance_manager_id")
    private String insuranceManagerId;
    @ManyToOne
    @JoinColumn(name = "insured_person_id", referencedColumnName = "id", nullable = false)
    private Beneficiaries beneficiariesByInsuredPersonId;

    public String getClaimId() {
        return claimId;
    }

    public void setClaimId(String claimId) {
        this.claimId = claimId;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getSettlementDate() {
        return settlementDate;
    }

    public void setSettlementDate(Date settlementDate) {
        this.settlementDate = settlementDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public float getClaimAmount() {
        return claimAmount;
    }

    public void setClaimAmount(float claimAmount) {
        this.claimAmount = claimAmount;
    }

    public String getInsuredPersonId() {
        return insuredPersonId;
    }

    public void setInsuredPersonId(String insuredPersonId) {
        this.insuredPersonId = insuredPersonId;
    }

    public String getPolicyOwnerId() {
        return policyOwnerId;
    }

    public void setPolicyOwnerId(String policyOwnerId) {
        this.policyOwnerId = policyOwnerId;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getInsuranceSurveyorId() {
        return insuranceSurveyorId;
    }

    public void setInsuranceSurveyorId(String insuranceSurveyorId) {
        this.insuranceSurveyorId = insuranceSurveyorId;
    }

    public String getInsuranceManagerId() {
        return insuranceManagerId;
    }

    public void setInsuranceManagerId(String insuranceManagerId) {
        this.insuranceManagerId = insuranceManagerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Claim claim = (Claim) o;

        if (Float.compare(claimAmount, claim.claimAmount) != 0) return false;
        if (claimId != null ? !claimId.equals(claim.claimId) : claim.claimId != null) return false;
        if (creationDate != null ? !creationDate.equals(claim.creationDate) : claim.creationDate != null) return false;
        if (settlementDate != null ? !settlementDate.equals(claim.settlementDate) : claim.settlementDate != null)
            return false;
        if (status != null ? !status.equals(claim.status) : claim.status != null) return false;
        if (insuredPersonId != null ? !insuredPersonId.equals(claim.insuredPersonId) : claim.insuredPersonId != null)
            return false;
        if (policyOwnerId != null ? !policyOwnerId.equals(claim.policyOwnerId) : claim.policyOwnerId != null)
            return false;
        if (cardNumber != null ? !cardNumber.equals(claim.cardNumber) : claim.cardNumber != null) return false;
        if (insuranceSurveyorId != null ? !insuranceSurveyorId.equals(claim.insuranceSurveyorId) : claim.insuranceSurveyorId != null)
            return false;
        if (insuranceManagerId != null ? !insuranceManagerId.equals(claim.insuranceManagerId) : claim.insuranceManagerId != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = claimId != null ? claimId.hashCode() : 0;
        result = 31 * result + (creationDate != null ? creationDate.hashCode() : 0);
        result = 31 * result + (settlementDate != null ? settlementDate.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (claimAmount != 0.0f ? Float.floatToIntBits(claimAmount) : 0);
        result = 31 * result + (insuredPersonId != null ? insuredPersonId.hashCode() : 0);
        result = 31 * result + (policyOwnerId != null ? policyOwnerId.hashCode() : 0);
        result = 31 * result + (cardNumber != null ? cardNumber.hashCode() : 0);
        result = 31 * result + (insuranceSurveyorId != null ? insuranceSurveyorId.hashCode() : 0);
        result = 31 * result + (insuranceManagerId != null ? insuranceManagerId.hashCode() : 0);
        return result;
    }

    public Entity.Beneficiaries getBeneficiariesByInsuredPersonId() {
        return beneficiariesByInsuredPersonId;
    }

    public void setBeneficiariesByInsuredPersonId(Entity.Beneficiaries beneficiariesByInsuredPersonId) {
        this.beneficiariesByInsuredPersonId = beneficiariesByInsuredPersonId;
    }
}
