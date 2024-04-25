package Entity;

import jakarta.persistence.*;

import java.sql.Date;

/**
 * @author
 * @version ${}
 * @created 25/04/2024 14:10
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

        if (Float.compare(claimAmount, claim.claimAmount) != 0) return false;
        if (claimId != null ? !claimId.equals(claim.claimId) : claim.claimId != null) return false;
        if (creationDate != null ? !creationDate.equals(claim.creationDate) : claim.creationDate != null) return false;
        if (settlementDate != null ? !settlementDate.equals(claim.settlementDate) : claim.settlementDate != null)
            return false;
        if (status != null ? !status.equals(claim.status) : claim.status != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = claimId != null ? claimId.hashCode() : 0;
        result = 31 * result + (creationDate != null ? creationDate.hashCode() : 0);
        result = 31 * result + (settlementDate != null ? settlementDate.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (claimAmount != 0.0f ? Float.floatToIntBits(claimAmount) : 0);
        return result;
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
