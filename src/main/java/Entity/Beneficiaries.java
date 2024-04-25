package Entity;

import jakarta.persistence.*;

import java.util.Collection;

/**
 * @author
 * @version ${}
 * @created 25/04/2024 12:59
 * @project InsuranceManagementApplication
 */
@Entity
public class Beneficiaries {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private String id;
    @Basic
    @Column(name = "full_name")
    private String fullName;
    @Basic
    @Column(name = "address")
    private String address;
    @Basic
    @Column(name = "phone_number")
    private String phoneNumber;
    @Basic
    @Column(name = "email")
    private String email;
    @Basic
    @Column(name = "password")
    private String password;
    @Basic
    @Column(name = "policy_holder_id")
    private String policyHolderId;
    @Basic
    @Column(name = "type")
    private String type;
    @Basic
    @Column(name = "policy_owner_id")
    private String policyOwnerId;
    @Basic
    @Column(name = "card_number")
    private String cardNumber;
    @ManyToOne
    @JoinColumn(name = "policy_holder_id", referencedColumnName = "id", nullable = false)
    private Beneficiaries beneficiariesByPolicyHolderId;
    @OneToMany(mappedBy = "beneficiariesByPolicyHolderId")
    private Collection<Beneficiaries> beneficiariesById;

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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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

    public String getPolicyHolderId() {
        return policyHolderId;
    }

    public void setPolicyHolderId(String policyHolderId) {
        this.policyHolderId = policyHolderId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Beneficiaries that = (Beneficiaries) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (fullName != null ? !fullName.equals(that.fullName) : that.fullName != null) return false;
        if (address != null ? !address.equals(that.address) : that.address != null) return false;
        if (phoneNumber != null ? !phoneNumber.equals(that.phoneNumber) : that.phoneNumber != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;
        if (policyHolderId != null ? !policyHolderId.equals(that.policyHolderId) : that.policyHolderId != null)
            return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;
        if (policyOwnerId != null ? !policyOwnerId.equals(that.policyOwnerId) : that.policyOwnerId != null)
            return false;
        if (cardNumber != null ? !cardNumber.equals(that.cardNumber) : that.cardNumber != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (fullName != null ? fullName.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (phoneNumber != null ? phoneNumber.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (policyHolderId != null ? policyHolderId.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (policyOwnerId != null ? policyOwnerId.hashCode() : 0);
        result = 31 * result + (cardNumber != null ? cardNumber.hashCode() : 0);
        return result;
    }

    public Entity.Beneficiaries getBeneficiariesByPolicyHolderId() {
        return beneficiariesByPolicyHolderId;
    }

    public void setBeneficiariesByPolicyHolderId(Entity.Beneficiaries beneficiariesByPolicyHolderId) {
        this.beneficiariesByPolicyHolderId = beneficiariesByPolicyHolderId;
    }

    public Collection<Entity.Beneficiaries> getBeneficiariesById() {
        return beneficiariesById;
    }

    public void setBeneficiariesById(Collection<Entity.Beneficiaries> beneficiariesById) {
        this.beneficiariesById = beneficiariesById;
    }
}
