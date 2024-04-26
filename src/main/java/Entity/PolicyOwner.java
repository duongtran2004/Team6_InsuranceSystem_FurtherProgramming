package Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

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

    @OneToMany(mappedBy = "policyOwner")
    private Collection<Beneficiaries> listOfBeneficiaries;
    @OneToMany(mappedBy = "policyOwner")
    private Collection<Claim> listOfClaims;
    @OneToMany(mappedBy = "policyOwner")
    private Collection<InsuranceCard> listOfInsuranceCards;



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
}
