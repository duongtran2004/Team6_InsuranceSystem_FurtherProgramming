package Entity;

import jakarta.persistence.*;
import javafx.scene.control.Button;

import java.util.Collection;

/**
 * @author
 * @version ${}
 * @created 26/04/2024 10:58
 * @project InsuranceManagementTeamProject
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "user_type", discriminatorType = DiscriminatorType.STRING)
public class Beneficiaries extends Customer {

    @Transient
    private Button addClaim;

    public Button getAddClaim() {
        return addClaim;
    }

    public void setAddClaim(Button addClaim) {
        this.addClaim = addClaim;
    }
    @Transient
    private javafx.scene.control.Button addClaimButton;
    @Basic
    @Column(name = "policy_owner_id", insertable = false, updatable = false)
    private String policyOwnerId;

    @Basic
    @Column(name = "policy_holder_id", insertable = false, updatable = false)
    private String policyHolderId;

    @Basic
    @Column(name = "card_number", insertable = false, updatable = false)
    private String cardNumber;

    @Basic
    @Column(name = "user_type", insertable = false, updatable = false)
    private String type;
    @ManyToOne
    @JoinColumn(name = "policy_holder_id", referencedColumnName = "id")
    private Beneficiaries policyHolder;

    @ManyToOne
    @JoinColumn(name = "policy_owner_id", referencedColumnName = "id")
    private PolicyOwner policyOwner;
    @ManyToOne
    @JoinColumn(name = "card_number", referencedColumnName = "card_number")
    private InsuranceCard insuranceCard;
    @OneToMany(mappedBy = "insuredPerson")
    private Collection<Claim> listOfClaims;

    public String getPolicyOwnerId() {
        return policyOwnerId;
    }

    public void setPolicyOwnerId(String policyOwnerId) {
        this.policyOwnerId = policyOwnerId;
    }

    public String getPolicyHolderId() {
        return policyHolderId;
    }

    public void setPolicyHolderId(String policyHolderId) {
        this.policyHolderId = policyHolderId;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

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

    public Button getAddClaimButton() {
        return addClaimButton;
    }

    public void setAddClaimButton(Button addClaimButton) {
        this.addClaimButton = addClaimButton;
    }

}
