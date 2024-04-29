package org.example.insurancemanagementapplication.Controller;

import Entity.Dependant;
import Entity.PolicyHolder;
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
 * @author Luong Thanh Trung
 * @version ${}
 * @created 29/04/2024 11:50
 * @project InsuranceManagementTeamProject
 */
public class CreationPageController_Dependant implements CustomerCreateRemove, CustomerUpdate, Initializable {
    private EntityManager entityManager;
    private User user;
    private PolicyHolder policyHolder;
    private Dependant dependant;

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
        if (dependant != null){
            fullNameField.setText(dependant.getFullName());
            fullNameField.setDisable(true);
            addressField.setText(dependant.getAddress());
            phoneNumberField.setText(dependant.getPhoneNumber());
            emailField.setText(dependant.getEmail());
            passwordField.setText(dependant.getPassword());
            passwordValidationField.setText(dependant.getPassword());
            submitButton.setOnAction(event -> {
                CustomerUpdate.updateDependant(entityManager, dependant, errorContainer, addressField.getText(), phoneNumberField.getText(), emailField.getText(), passwordField.getText(), passwordValidationField.getText());
            });
        }
        else {
            submitButton.setOnAction(event -> {
                CustomerCreateRemove.createDependant(entityManager, errorContainer, fullNameField.getText(), addressField.getText(), phoneNumberField.getText(), emailField.getText(), passwordField.getText(), passwordValidationField.getText(), policyHolder);
            });
        }
    }

    public CreationPageController_Dependant(EntityManager entityManager, User user, PolicyHolder policyHolder) {
        this.entityManager = entityManager;
        this.user = user;
        this.policyHolder = policyHolder;
    }

    public CreationPageController_Dependant(EntityManager entityManager, User user, Dependant dependant) {
        this.entityManager = entityManager;
        this.user = user;
        this.dependant = dependant;
    }
}
