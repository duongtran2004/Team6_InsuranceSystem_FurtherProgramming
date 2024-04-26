package Entity;

import jakarta.persistence.*;

import java.util.Collection;

/**
 * @author
 * @version ${}
 * @created 25/04/2024 14:10
 * @project InsuranceManagementApplication
 */
@Entity
@Table(name = "beneficiaries")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)

@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
public class Beneficiaries extends User {
    @Basic
    @Column(name = "type", insertable = false, updatable = false)
    private String type;
    @ManyToOne
    @JoinColumn(name = "policy_holder_id", referencedColumnName = "id", nullable = false)
    private Beneficiaries policyHolder;

    @ManyToOne
    @JoinColumn(name = "policy_owner_id", referencedColumnName = "id", nullable = false)
    private PolicyOwner policyOwner;
    @ManyToOne
    @JoinColumn(name = "card_number", referencedColumnName = "card_number", nullable = false)
    private InsuranceCard insuranceCard;
    @OneToMany(mappedBy = "insuredPerson")
    private Collection<Claim> listOfClaims;



    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public Beneficiaries getPolicyHolder() {
        return policyHolder;
    }

    public void setPolicyHolder(Beneficiaries policyHolder) {
        this.policyHolder = policyHolder;
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

    public Collection<Claim> getListOfClaims() {
        return listOfClaims;
    }

    public void setListOfClaims(Collection<Claim> listOfClaims) {
        this.listOfClaims = listOfClaims;
    }
}
