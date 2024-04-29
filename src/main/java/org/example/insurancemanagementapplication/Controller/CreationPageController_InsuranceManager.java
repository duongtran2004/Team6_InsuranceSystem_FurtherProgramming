package org.example.insurancemanagementapplication.Controller;

import Entity.InsuranceManager;
import Entity.User;
import jakarta.persistence.EntityManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.example.insurancemanagementapplication.Interfaces.EmployeeCreateRemove;
import org.example.insurancemanagementapplication.Interfaces.EmployeeUpdate;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Luong Thanh Trung
 * @version ${}
 * @created 29/04/2024 08:53
 * @project InsuranceManagementTeamProject
 */
public class CreationPageController_InsuranceManager implements EmployeeCreateRemove, EmployeeUpdate, Initializable {
    private User user;
    private InsuranceManager insuranceManager;
    private EntityManager entityManager;



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

    public CreationPageController_InsuranceManager(EntityManager entityManager, User user) {
        this.entityManager = entityManager;
        this.user = user;
    }

    public CreationPageController_InsuranceManager(EntityManager entityManager, User user, InsuranceManager insuranceManager) {
        this.entityManager = entityManager;
        this.insuranceManager = insuranceManager;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (insuranceManager != null){
            fullNameField.setText(insuranceManager.getFullName());
            fullNameField.setDisable(true);
            addressField.setText(insuranceManager.getAddress());
            phoneNumberField.setText(insuranceManager.getPhoneNumber());
            emailField.setText(insuranceManager.getEmail());
            passwordField.setText(insuranceManager.getPassword());
            passwordValidationField.setText(insuranceManager.getPassword());
        }
        submitButton.setOnAction(e ->{
            if (insuranceManager != null){
                if (insuranceManager != null){
                    EmployeeUpdate.updateInsuranceManager(entityManager, insuranceManager, errorContainer, addressField.getText(), phoneNumberField.getText(), emailField.getText(), passwordField.getText(), passwordValidationField.getText());
                }
                else {
                    EmployeeCreateRemove.createInsuranceManager(entityManager, errorContainer, fullNameField.getText(), addressField.getText(), phoneNumberField.getText(), emailField.getText(), passwordField.getText(), passwordValidationField.getText());
                }
            }
        });
    }
}
