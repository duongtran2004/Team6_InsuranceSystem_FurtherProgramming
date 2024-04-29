package org.example.insurancemanagementapplication.Controller;

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
 * @created 29/04/2024 11:49
 * @project InsuranceManagementTeamProject
 */
public class CreationPageController_PolicyOwner implements CustomerCreateRemove, CustomerUpdate, Initializable {
    private EntityManager entityManager;
    private User user;
    private PolicyOwner policyOwner;

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
        if (policyOwner != null){
            fullNameField.setText(policyOwner.getFullName());
            fullNameField.setDisable(true);
            addressField.setText(policyOwner.getAddress());
            phoneNumberField.setText(policyOwner.getPhoneNumber());
            emailField.setText(policyOwner.getEmail());
            passwordField.setText(policyOwner.getPassword());
            passwordValidationField.setText(policyOwner.getPassword());
            submitButton.setOnAction(event -> {
                CustomerUpdate.updatePolicyOwner(entityManager, policyOwner, errorContainer, addressField.getText(), phoneNumberField.getText(), emailField.getText(), passwordField.getText(), passwordValidationField.getText());
            });
        }
        else {
            submitButton.setOnAction(event -> {
                CustomerCreateRemove.createPolicyOwner(entityManager, errorContainer, fullNameField.getText(), addressField.getText(), phoneNumberField.getText(), emailField.getText(), passwordField.getText(), passwordValidationField.getText());
            });
        }


    }
}
