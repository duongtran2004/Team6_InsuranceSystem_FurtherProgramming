package Entity;

import jakarta.persistence.*;

import java.util.Collection;

/**
 * @author
 * @version ${}
 * @created 24/04/2024 05:08
 * @project InsuranceManagementApplication
 */
@Entity
public class Beneficiary {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "ID")
    private String id;
    @Basic
    @Column(name = "FullName")
    private String fullName;
    @Basic
    @Column(name = "Address")
    private String address;
    @Basic
    @Column(name = "Email")
    private String email;
    @Basic
    @Column(name = "Password")
    private String password;
    @Basic
    @Column(name = "Type")
    private String type;
    @ManyToOne
    @JoinColumn(name = "PolicyHolderID", referencedColumnName = "ID")
    private Beneficiary policyHolder;
    @OneToMany(mappedBy = "policyHolder")
    private Collection<Beneficiary> dependantList;
    @ManyToOne
    @JoinColumn(name = "PolicyOwnerID", referencedColumnName = "ID")
    private PolicyOwner policyOwner;
    @ManyToOne
    @JoinColumn(name = "CardNumber", referencedColumnName = "CardNumber")
    private InsuranceCard insuranceCard;
    @OneToMany(mappedBy = "insuredPerson")
    private Collection<Claims> claimList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Beneficiary that = (Beneficiary) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (fullName != null ? !fullName.equals(that.fullName) : that.fullName != null) return false;
        if (address != null ? !address.equals(that.address) : that.address != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (fullName != null ? fullName.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }

    public Beneficiary getPolicyHolder() {
        return policyHolder;
    }

    public void setPolicyHolder(Beneficiary policyHolder) {
        this.policyHolder = policyHolder;
    }

    public Collection<Beneficiary> getDependantList() {
        return dependantList;
    }

    public void setDependantList(Collection<Beneficiary> dependantList) {
        this.dependantList = dependantList;
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

    public Collection<Claims> getClaimList() {
        return claimList;
    }

    public void setClaimList(Collection<Claims> claimList) {
        this.claimList = claimList;
    }
}
