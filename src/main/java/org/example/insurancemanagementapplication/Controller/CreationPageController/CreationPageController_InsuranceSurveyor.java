package org.example.insurancemanagementapplication.Controller.CreationPageController;

import Entity.*;
import jakarta.persistence.EntityManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.insurancemanagementapplication.Controller.DashBoardController.*;
import org.example.insurancemanagementapplication.Interfaces.EmployeeCreateRemove;
import org.example.insurancemanagementapplication.Interfaces.EmployeeUpdate;
import org.example.insurancemanagementapplication.MainEntryPoint;
import org.example.insurancemanagementapplication.Utility.InputValidator;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author
 * @version ${}
 * @created 29/04/2024 11:48
 * @project InsuranceManagementTeamProject
 */
public class CreationPageController_InsuranceSurveyor implements Initializable, EmployeeCreateRemove, EmployeeUpdate {
    private EntityManager entityManager;
    private User user;
    private InsuranceSurveyor insuranceSurveyor;
    private InsuranceManager manager;
    private boolean managerReassign = false;

    @FXML
    private TextField managerIdField;
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
    @FXML
    private Button returnButton;
    @FXML
    private Button managerReassignButton;

    public CreationPageController_InsuranceSurveyor(EntityManager entityManager, User user, InsuranceSurveyor insuranceSurveyor) {
        this.entityManager = entityManager;
        this.user = user;
        this.insuranceSurveyor = insuranceSurveyor;
    }

    public CreationPageController_InsuranceSurveyor(EntityManager entityManager, User user, InsuranceManager manager) {
        this.entityManager = entityManager;
        this.user = user;
        this.manager = manager;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (insuranceSurveyor != null){
            managerIdField.setText(insuranceSurveyor.getInsuranceManagerId());
            managerIdField.setDisable(true);
            fullNameField.setText(insuranceSurveyor.getFullName());
            fullNameField.setDisable(true);
            addressField.setText(insuranceSurveyor.getAddress());
            phoneNumberField.setText(insuranceSurveyor.getPhoneNumber());
            emailField.setText(insuranceSurveyor.getEmail());
            passwordField.setText(insuranceSurveyor.getPassword());
            passwordValidationField.setText(insuranceSurveyor.getPassword());
        }

        managerReassignButton.setOnAction(e ->{
            managerReassign = !managerReassign;
            if (managerReassign){
                managerIdField.setDisable(false);
            }
            else {
                managerIdField.setDisable(true);
            }
        });

        submitButton.setOnAction(e ->{
            // Validate input fields before creating or updating a InsuranceManager entity
            String fullName = fullNameField.getText();
            //String address = addressField.getText();
            String phoneNumber = phoneNumberField.getText();
            String email = emailField.getText();
            String password = passwordField.getText();
            String passwordValidation = passwordValidationField.getText();

            // Perform input validation using InputValidator methods
            if (!InputValidator.validateNonEmptyString(fullName)) {
                errorContainer.setText("Full name cannot be empty.");
            } else if (!InputValidator.validateEmailFormat(email)) {
                errorContainer.setText("Invalid email format.");
            } else if (!InputValidator.validatePhoneFormat(phoneNumber)) {
                errorContainer.setText("Invalid phone number format.");
            } else if (!InputValidator.validatePasswordFormat(password)) {
                errorContainer.setText("Invalid password format.");
            } else if (!password.equals(passwordValidation)) {
                errorContainer.setText("Passwords do not match.");
            } else {
                // If all validations pass, proceed with creating or updating the InsuranceManager entity
            if (manager != null){
                EmployeeUpdate.updateInsuranceSurveyor(managerReassign, entityManager, errorContainer, insuranceSurveyor, managerIdField.getText(), addressField.getText(), phoneNumberField.getText(), emailField.getText(), passwordField.getText(), passwordValidationField.getText());
            }
            else {
                EmployeeCreateRemove.createInsuranceSurveyor(entityManager, errorContainer, fullNameField.getText(), addressField.getText(), phoneNumberField.getText(), emailField.getText(), passwordField.getText(), manager, passwordValidationField.getText());

            }}
        });

        returnButton.setOnAction(event -> {
            if (user instanceof SystemAdmin){
                DashBoardController_SystemAdmin dashBoardControllerSystemAdmin = new DashBoardController_SystemAdmin(entityManager, (SystemAdmin) user);
                Stage stage = (Stage) returnButton.getScene().getWindow();
                FXMLLoader fxmlLoader = new FXMLLoader(MainEntryPoint.class.getResource("DashBoard_SystemAdmin.fxml"));
                fxmlLoader.setController(dashBoardControllerSystemAdmin);
                try {
                    Scene scene = new Scene(fxmlLoader.load());
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            else if (user instanceof InsuranceManager){
                DashBoardController_InsuranceManager dashBoardControllerInsuranceManager = new DashBoardController_InsuranceManager((InsuranceManager) user, entityManager);
                Stage stage = (Stage) returnButton.getScene().getWindow();
                FXMLLoader fxmlLoader = new FXMLLoader(MainEntryPoint.class.getResource("DashBoard_InsuranceManager.fxml"));
                fxmlLoader.setController(dashBoardControllerInsuranceManager);
                try {
                    Scene scene = new Scene(fxmlLoader.load());
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            else if (user instanceof InsuranceSurveyor){
                DashBoardController_InsuranceSurveyor dashBoardControllerInsuranceSurveyor = new DashBoardController_InsuranceSurveyor((InsuranceSurveyor) user, entityManager);
                Stage stage = (Stage) returnButton.getScene().getWindow();
                FXMLLoader fxmlLoader = new FXMLLoader(MainEntryPoint.class.getResource("DashBoard_InsuranceSurveyor.fxml"));
                fxmlLoader.setController(dashBoardControllerInsuranceSurveyor);
                try {
                    Scene scene = new Scene(fxmlLoader.load());
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            else if (user instanceof PolicyOwner){
                DashBoardController_PolicyOwner dashBoardController_policyOwner = new DashBoardController_PolicyOwner((PolicyOwner) user, entityManager);
                Stage stage = (Stage) returnButton.getScene().getWindow();
                FXMLLoader fxmlLoader = new FXMLLoader(MainEntryPoint.class.getResource("DashBoard_PolicyOwner.fxml"));
                fxmlLoader.setController(dashBoardController_policyOwner);
                try {
                    Scene scene = new Scene(fxmlLoader.load());
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            else if (user instanceof PolicyHolder){
                DashBoardController_PolicyHolder dashBoardControllerPolicyHolder = new DashBoardController_PolicyHolder((PolicyHolder) user, entityManager);
                Stage stage = (Stage) returnButton.getScene().getWindow();
                FXMLLoader fxmlLoader = new FXMLLoader(MainEntryPoint.class.getResource("DashBoard_PolicyHolder.fxml"));
                fxmlLoader.setController(dashBoardControllerPolicyHolder);
                try {
                    Scene scene = new Scene(fxmlLoader.load());
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }


        });

    }
}
