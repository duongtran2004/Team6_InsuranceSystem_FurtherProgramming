package Entity;

import jakarta.persistence.*;

import java.sql.Date;
import java.util.Objects;

/**
 * @author
 * @version ${}
 * @created 26/04/2024 10:58
 * @project InsuranceManagementTeamProject
 */
@Entity
public class Claim {
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
    @Column(name = "insurance_manager_id")
    private String insuranceManagerId;
    @Basic
    @Column(name = "insurance_surveyor_id")
    private String insuranceSurveyorId;

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

    public String getInsuranceManagerId() {
        return insuranceManagerId;
    }

    public void setInsuranceManagerId(String insuranceManagerId) {
        this.insuranceManagerId = insuranceManagerId;
    }

    public String getInsuranceSurveyorId() {
        return insuranceSurveyorId;
    }

    public void setInsuranceSurveyorId(String insuranceSurveyorId) {
        this.insuranceSurveyorId = insuranceSurveyorId;
    }

    @ManyToOne
    @JoinColumn(name = "insured_person_id", referencedColumnName = "id", nullable = false)
    private Beneficiaries insuredPerson;
    @ManyToOne
    @JoinColumn(name = "policy_owner_id", referencedColumnName = "id", nullable = false)
    private PolicyOwner policyOwner;
    @ManyToOne
    @JoinColumn(name = "card_number", referencedColumnName = "card_number", nullable = false)
    private InsuranceCard insuranceCard;
    @ManyToOne
    @JoinColumn(name = "insurance_surveyor_id", referencedColumnName = "id", nullable = false)
    private InsuranceSurveyor insuranceSurveyor;
    @ManyToOne
    @JoinColumn(name = "insurance_manager_id", referencedColumnName = "id", nullable = false)
    private InsuranceManager insuranceManager;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Claim claim = (Claim) o;
        return Float.compare(claimAmount, claim.claimAmount) == 0 && Objects.equals(claimId, claim.claimId) && Objects.equals(creationDate, claim.creationDate) && Objects.equals(settlementDate, claim.settlementDate) && Objects.equals(status, claim.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(claimId, creationDate, settlementDate, status, claimAmount);
    }

    public Beneficiaries getInsuredPerson() {
        return insuredPerson;
    }

    public void setInsuredPerson(Beneficiaries insuredPerson) {
        this.insuredPerson = insuredPerson;
    }

    public PolicyOwner getPolicyOwner() {
        return policyOwner;
    }

    public void setPolicyOwner(PolicyOwner policyOwner) {
        this.policyOwner = policyOwner;
    }

    public InsuranceCard getInsuranceCard() {
        return insuranceCard;
    }

    public void setInsuranceCard(InsuranceCard insuranceCard) {
        this.insuranceCard = insuranceCard;
    }

    public InsuranceSurveyor getInsuranceSurveyor() {
        return insuranceSurveyor;
    }

    public void setInsuranceSurveyor(InsuranceSurveyor insuranceSurveyor) {
        this.insuranceSurveyor = insuranceSurveyor;
    }

    public InsuranceManager getInsuranceManager() {
        return insuranceManager;
    }

    public void setInsuranceManager(InsuranceManager insuranceManager) {
        this.insuranceManager = insuranceManager;
    }
}
