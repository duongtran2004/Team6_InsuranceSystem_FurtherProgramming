package Entity;

import jakarta.persistence.*;
import javafx.scene.control.TextField;

import java.util.Objects;

/**
 * @author Luong Thanh Trung
 * @version ${}
 * @created 26/04/2024 10:58
 * @project InsuranceManagementTeamProject
 */
@MappedSuperclass
public class User {

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

    @Transient
    private TextField addressTextField;
    @Transient
    private TextField phoneNumberTextField;
    @Transient
    private TextField emailTextField;
    @Transient
    private TextField passwordTextField;


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

    public TextField getAddressTextField() {
        return addressTextField;
    }

    public void setAddressTextField(TextField addressTextField) {
        this.addressTextField = addressTextField;
    }

    public TextField getPhoneNumberTextField() {
        return phoneNumberTextField;
    }

    public void setPhoneNumberTextField(TextField phoneNumberTextField) {
        this.phoneNumberTextField = phoneNumberTextField;
    }

    public TextField getEmailTextField() {
        return emailTextField;
    }

    public void setEmailTextField(TextField emailTextField) {
        this.emailTextField = emailTextField;
    }

    public TextField getPasswordTextField() {
        return passwordTextField;
    }

    public void setPasswordTextField(TextField passwordTextField) {
        this.passwordTextField = passwordTextField;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User that = (User) o;
        return Objects.equals(id, that.id) && Objects.equals(fullName, that.fullName) && Objects.equals(address, that.address) && Objects.equals(phoneNumber, that.phoneNumber) && Objects.equals(email, that.email) && Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fullName, address, phoneNumber, email, password);
    }
}
