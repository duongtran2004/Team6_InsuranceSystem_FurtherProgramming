package org.example.insurancemanagementapplication.Controller;

import Entity.InsuranceManager;
import Entity.InsuranceSurveyor;
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
            if (manager != null){
                EmployeeUpdate.updateInsuranceSurveyor(managerReassign, entityManager, errorContainer, insuranceSurveyor, managerIdField.getText(), addressField.getText(), phoneNumberField.getText(), emailField.getText(), passwordField.getText(), passwordValidationField.getText());
            }
            else {
                EmployeeCreateRemove.createInsuranceSurveyor(entityManager, errorContainer, fullNameField.getText(), addressField.getText(), phoneNumberField.getText(), emailField.getText(), passwordField.getText(), manager, passwordValidationField.getText());

            }
        });

    }
}
