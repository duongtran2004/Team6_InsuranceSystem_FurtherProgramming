package Entity;

import jakarta.persistence.*;

import java.sql.Date;

/**
 * @author
 * @version ${}
 * @created 24/04/2024 05:08
 * @project InsuranceManagementApplication
 */
@Entity
public class Claims {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "ClaimID")
    private String claimId;
    @Basic
    @Column(name = "DateOfCreation")
    private Date dateOfCreation;
    @Basic
    @Column(name = "DateOfSettlement")
    private Date dateOfSettlement;
    @Basic
    @Column(name = "SettlementAmount")
    private Float settlementAmount;
    @Basic
    @Column(name = "Status")
    private String status;
    @Basic
    @Column(name = "BankName")
    private String bankName;
    @Basic
    @Column(name = "BankAccountName")
    private String bankAccountName;
    @Basic
    @Column(name = "BankAccountNumber")
    private String bankAccountNumber;
    @ManyToOne
    @JoinColumn(name = "InsuredPersonID", referencedColumnName = "ID")
    private Beneficiary insuredPerson;
    @ManyToOne
    @JoinColumn(name = "CardNumber", referencedColumnName = "CardNumber")
    private InsuranceCard cardNumber;
    @ManyToOne
    @JoinColumn(name = "InsuranceSurveyorID", referencedColumnName = "ID")
    private InsuranceSurveyor insuranceSurveyor;
    @ManyToOne
    @JoinColumn(name = "InsurfanceManagerID", referencedColumnName = "ID")
    private InsuranceManager insuranceManager;

    public String getClaimId() {
        return claimId;
    }

    public void setClaimId(String claimId) {
        this.claimId = claimId;
    }

    public Date getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(Date dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    public Date getDateOfSettlement() {
        return dateOfSettlement;
    }

    public void setDateOfSettlement(Date dateOfSettlement) {
        this.dateOfSettlement = dateOfSettlement;
    }

    public Float getSettlementAmount() {
        return settlementAmount;
    }

    public void setSettlementAmount(Float settlementAmount) {
        this.settlementAmount = settlementAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankAccountName() {
        return bankAccountName;
    }

    public void setBankAccountName(String bankAccountName) {
        this.bankAccountName = bankAccountName;
    }

    public String getBankAccountNumber() {
        return bankAccountNumber;
    }

    public void setBankAccountNumber(String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Claims claims = (Claims) o;

        if (claimId != null ? !claimId.equals(claims.claimId) : claims.claimId != null) return false;
        if (dateOfCreation != null ? !dateOfCreation.equals(claims.dateOfCreation) : claims.dateOfCreation != null)
            return false;
        if (dateOfSettlement != null ? !dateOfSettlement.equals(claims.dateOfSettlement) : claims.dateOfSettlement != null)
            return false;
        if (settlementAmount != null ? !settlementAmount.equals(claims.settlementAmount) : claims.settlementAmount != null)
            return false;
        if (status != null ? !status.equals(claims.status) : claims.status != null) return false;
        if (bankName != null ? !bankName.equals(claims.bankName) : claims.bankName != null) return false;
        if (bankAccountName != null ? !bankAccountName.equals(claims.bankAccountName) : claims.bankAccountName != null)
            return false;
        if (bankAccountNumber != null ? !bankAccountNumber.equals(claims.bankAccountNumber) : claims.bankAccountNumber != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = claimId != null ? claimId.hashCode() : 0;
        result = 31 * result + (dateOfCreation != null ? dateOfCreation.hashCode() : 0);
        result = 31 * result + (dateOfSettlement != null ? dateOfSettlement.hashCode() : 0);
        result = 31 * result + (settlementAmount != null ? settlementAmount.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (bankName != null ? bankName.hashCode() : 0);
        result = 31 * result + (bankAccountName != null ? bankAccountName.hashCode() : 0);
        result = 31 * result + (bankAccountNumber != null ? bankAccountNumber.hashCode() : 0);
        return result;
    }

    public Beneficiary getInsuredPerson() {
        return insuredPerson;
    }

    public void setInsuredPerson(Beneficiary insuredPerson) {
        this.insuredPerson = insuredPerson;
    }

    public InsuranceCard getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(InsuranceCard cardNumber) {
        this.cardNumber = cardNumber;
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
