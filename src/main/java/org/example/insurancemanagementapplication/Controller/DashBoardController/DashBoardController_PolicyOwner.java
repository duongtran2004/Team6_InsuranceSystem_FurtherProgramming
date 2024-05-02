package org.example.insurancemanagementapplication.Controller.DashBoardController;

import Entity.Beneficiaries;
import Entity.Claim;
import Entity.PolicyOwner;
import jakarta.persistence.EntityManager;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.example.insurancemanagementapplication.Interfaces.CustomerCreateRemove;

/**
 * @author Luong Thanh Trung
 * @version ${}
 * @created 27/04/2024 04:54
 * @project InsuranceManagementTeamProject
 */
public class DashBoardController_PolicyOwner implements CustomerCreateRemove {
    private  EntityManager entityManager;
    private  PolicyOwner user;
    private Beneficiaries beneficiary;
    private Claim claim;


    @FXML
    private TextField userIdField;
    @FXML
    private TextField fullNameField;
    @FXML
    private TextField addressField;
    @FXML
    private TextField phoneNumberField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField passwordField;
    @FXML
    private TextField passwordValidationField;

    public void FillingUserDetail(){
        userIdField.setText(user.getId());
        fullNameField.setText(user.getFullName());
        addressField.setText(user.getAddress());
        phoneNumberField.setText(user.getPhoneNumber());
        emailField.setText(user.getEmail());
        passwordField.setText(user.getPassword());
        passwordValidationField.setText(user.getPassword());
    }


    public DashBoardController_PolicyOwner(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public DashBoardController_PolicyOwner(PolicyOwner policyOwner, EntityManager entityManager) {
        this.user = policyOwner;
        this.entityManager = entityManager;
    }

    public PolicyOwner getUser() {
        return user;
    }

    public Beneficiaries getBeneficiary() {
        return beneficiary;
    }

    public void setBeneficiary(Beneficiaries beneficiary) {
        this.beneficiary = beneficiary;

    }

    public Claim getClaim() {
        return claim;
    }

    public void setClaim(Claim claim) {
        this.claim = claim;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }
}
