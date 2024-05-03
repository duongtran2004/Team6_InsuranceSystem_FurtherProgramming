package org.example.insurancemanagementapplication.Controller.CreationPageController;

import Entity.InsuranceManager;
import Entity.User;
import jakarta.persistence.EntityManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.example.insurancemanagementapplication.Interfaces.Controller;
import org.example.insurancemanagementapplication.Interfaces.EmployeeCreateRemove;
import org.example.insurancemanagementapplication.Interfaces.EmployeeUpdate;
import org.example.insurancemanagementapplication.Utility.InputValidator;
import org.example.insurancemanagementapplication.Utility.RepeatedCode;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Luong Thanh Trung
 * @version ${}
 * @created 29/04/2024 08:53
 * @project InsuranceManagementTeamProject
 */
public class CreationPageController_InsuranceManager extends CreationPageController implements EmployeeCreateRemove, EmployeeUpdate, Initializable, Controller {
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
        super(entityManager, user);
    }

    public CreationPageController_InsuranceManager(EntityManager entityManager, User user, InsuranceManager insuranceManager) {
        super(entityManager, user);
        this.insuranceManager = insuranceManager;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setActionReturnButton();
        if (insuranceManager != null){
            fullNameField.setText(insuranceManager.getFullName());
            fullNameField.setDisable(true);
            addressField.setText(insuranceManager.getAddress());
            phoneNumberField.setText(insuranceManager.getPhoneNumber());
            emailField.setText(insuranceManager.getEmail());
            passwordField.setText(insuranceManager.getPassword());
            passwordValidationField.setText(insuranceManager.getPassword());
            submitButton.setOnAction(event -> {
                String message = InputValidator.validatingUser(emailField.getText(), passwordField.getText(), phoneNumberField.getText(), addressField.getText(), passwordValidationField.getText());
                if (message.equals("Success")){
                    EmployeeUpdate.updateInsuranceManager(entityManager, insuranceManager, addressField.getText(), phoneNumberField.getText(), passwordField.getText(), passwordValidationField.getText());
                }
                else {
                    errorContainer.setText(message);
                }
            });
        }
        else {
            submitButton.setOnAction(event -> {
                String message = InputValidator.validatingUser("Insurance Manager", entityManager, fullNameField.getText(), emailField.getText(), passwordField.getText(), phoneNumberField.getText(), addressField.getText(), passwordValidationField.getText());
                if (message.equals("Success")){
                    String id = RepeatedCode.idGenerate("IM");
                    EmployeeCreateRemove.createInsuranceManager(entityManager, id, fullNameField.getText(), addressField.getText(), phoneNumberField.getText(), emailField.getText(), passwordField.getText());
                }
            });
        }
    }
}
