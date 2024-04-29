package org.example.insurancemanagementapplication.Controller;

import Entity.PolicyHolder;
import Entity.PolicyOwner;
import Entity.User;
import jakarta.persistence.EntityManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.example.insurancemanagementapplication.Interfaces.CustomerCreateRemove;
import org.example.insurancemanagementapplication.Interfaces.CustomerUpdate;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author
 * @version ${}
 * @created 29/04/2024 11:50
 * @project InsuranceManagementTeamProject
 */
public class CreationPageController_PolicyHolder implements CustomerCreateRemove, CustomerUpdate, Initializable {
    private EntityManager entityManager;
    private User user;
    private PolicyOwner policyOwner;
    private PolicyHolder policyHolder;

    @FXML
    private TextField lengthOfContractField;
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
    @FXML
    private Label errorContainer;
    @FXML
    private Button submitButton;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (policyHolder != null){
            fullNameField.setText(policyHolder.getFullName());
            fullNameField.setDisable(true);
            lengthOfContractField.setDisable(true);
            addressField.setText(policyHolder.getAddress());
            phoneNumberField.setText(policyHolder.getPhoneNumber());
            emailField.setText(policyHolder.getEmail());
            passwordField.setText(policyHolder.getPassword());
            passwordValidationField.setText(policyHolder.getPassword());
            submitButton.setOnAction(event -> {
                CustomerUpdate.updatePolicyHolder(entityManager, policyHolder, errorContainer, addressField.getText(), phoneNumberField.getText(), emailField.getText(), passwordField.getText(), passwordValidationField.getText());
            });
        }
        else {
            submitButton.setOnAction(event -> {
                CustomerCreateRemove.createPolicyHolder(entityManager, errorContainer, lengthOfContractField.getText(), fullNameField.getText(), addressField.getText(), phoneNumberField.getText(), emailField.getText(), passwordField.getText(), passwordValidationField.getText(), policyOwner);
            });
        }


    }

    public CreationPageController_PolicyHolder(EntityManager entityManager, User user, PolicyOwner policyOwner) {
        this.entityManager = entityManager;
        this.user = user;
        this.policyOwner = policyOwner;
    }

    public CreationPageController_PolicyHolder(EntityManager entityManager, User user, PolicyHolder policyHolder) {
        this.entityManager = entityManager;
        this.user = user;
        this.policyOwner = policyOwner;
        this.policyHolder = policyHolder;
    }
}
