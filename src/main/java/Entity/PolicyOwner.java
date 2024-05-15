package Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import javafx.scene.control.Button;

import java.util.Collection;

/**
 * @author
 * @version ${}
 * @created 26/04/2024 10:58
 * @project InsuranceManagementTeamProject
 */
@Entity
@Table(name = "policy_owner", schema = "public", catalog = "postgres")
public class PolicyOwner extends Customer {
//    @Basic
//    @Column (name = "yearly_rate")
//    private int yearlyRate;

    @Transient
    private Button addPolicyButton;
    @Transient
    private Button removeClaimButton;
    @OneToMany(mappedBy = "policyOwner")
    private Collection<Beneficiaries> listOfBeneficiaries;
    @OneToMany(mappedBy = "policyOwner")
    private Collection<Claim> listOfClaims;
    @OneToMany(mappedBy = "policyOwner")
    private Collection<InsuranceCard> listOfInsuranceCards;

    @Transient
    private int totalYearlyRate;

    @Transient
    private int totalSuccessfulClaimAmount;


    public Button getAddPolicyButton() {
        return addPolicyButton;
    }

    public void setAddPolicyButton(Button addPolicyButton) {
        this.addPolicyButton = addPolicyButton;
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

    public Collection<InsuranceCard> getListOfInsuranceCards() {
        return listOfInsuranceCards;
    }

    public void setListOfInsuranceCards(Collection<InsuranceCard> listOfInsuranceCards) {
        this.listOfInsuranceCards = listOfInsuranceCards;
    }

    public int getTotalYearlyRate() {
        return totalYearlyRate;
    }

    public void setTotalYearlyRate(int totalYearlyRate) {
        this.totalYearlyRate = totalYearlyRate;
    }
}
