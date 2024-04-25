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
@Table(name = "INSURANCE_SURVEYOR", schema = "public", catalog = "postgres")
public class InsuranceSurveyor {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private String id;
    @Basic
    @Column(name = "full_Name")
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
    @Column(name = "insurance_manager_id")
    private String insuranceManagerId;
    @OneToMany(mappedBy = "insuranceSurveyorByInsuranceSurveyorId")
    private Collection<Claim> claimsById;
    @ManyToOne
    @JoinColumn(name = "insurance_manager_id", referencedColumnName = "id", nullable = false)
    private InsuranceManager insuranceManagerByInsuranceManagerId;

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

        InsuranceSurveyor that = (InsuranceSurveyor) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (fullName != null ? !fullName.equals(that.fullName) : that.fullName != null) return false;
        if (address != null ? !address.equals(that.address) : that.address != null) return false;
        if (phoneNumber != null ? !phoneNumber.equals(that.phoneNumber) : that.phoneNumber != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;
        if (insuranceManagerId != null ? !insuranceManagerId.equals(that.insuranceManagerId) : that.insuranceManagerId != null)
            return false;

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
        result = 31 * result + (insuranceManagerId != null ? insuranceManagerId.hashCode() : 0);
        return result;
    }

    public Collection<Entity.Claim> getClaimsById() {
        return claimsById;
    }

    public void setClaimsById(Collection<Entity.Claim> claimsById) {
        this.claimsById = claimsById;
    }

    public Entity.InsuranceManager getInsuranceManagerByInsuranceManagerId() {
        return insuranceManagerByInsuranceManagerId;
    }

    public void setInsuranceManagerByInsuranceManagerId(Entity.InsuranceManager insuranceManagerByInsuranceManagerId) {
        this.insuranceManagerByInsuranceManagerId = insuranceManagerByInsuranceManagerId;
    }
}
