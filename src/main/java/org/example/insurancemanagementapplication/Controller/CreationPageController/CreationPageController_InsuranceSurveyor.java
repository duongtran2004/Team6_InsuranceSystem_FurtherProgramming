package org.example.insurancemanagementapplication.Controller.CreationPageController;

import Entity.InsuranceManager;
import Entity.InsuranceSurveyor;
import Entity.User;
import jakarta.persistence.EntityManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.example.insurancemanagementapplication.Interfaces.Controller;
import org.example.insurancemanagementapplication.Interfaces.EmployeeAnalytics;
import org.example.insurancemanagementapplication.Interfaces.EmployeeCreateRemove;
import org.example.insurancemanagementapplication.Interfaces.EmployeeUpdate;
import org.example.insurancemanagementapplication.Utility.InputValidator;
import org.example.insurancemanagementapplication.Utility.RepeatedCode;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author
 * @version ${}
 * @created 29/04/2024 11:48
 * @project InsuranceManagementTeamProject
 */
public class CreationPageController_InsuranceSurveyor extends CreationPageController implements Initializable, EmployeeCreateRemove, EmployeeUpdate, Controller {
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
    private Button managerReassignButton;

    public CreationPageController_InsuranceSurveyor(EntityManager entityManager, User user, InsuranceSurveyor insuranceSurveyor) {
        super(entityManager, user);
        this.insuranceSurveyor = insuranceSurveyor;
    }

    public CreationPageController_InsuranceSurveyor(EntityManager entityManager, User user, InsuranceManager manager) {
        super(entityManager, user);
        this.manager = manager;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setActionReturnButton();
        managerIdField.setDisable(true);
        managerReassignButton.setDisable(true);
        if (insuranceSurveyor != null){
            managerReassignButton.setDisable(false);
            managerIdField.setText(insuranceSurveyor.getInsuranceManagerId());
            fullNameField.setDisable(true);
            fullNameField.setText(insuranceSurveyor.getFullName());
            addressField.setText(insuranceSurveyor.getAddress());
            phoneNumberField.setText(insuranceSurveyor.getPhoneNumber());
            emailField.setText(insuranceSurveyor.getEmail());
            passwordField.setText(insuranceSurveyor.getPassword());
            passwordValidationField.setText(insuranceSurveyor.getPassword());

            managerReassignButton.setOnAction(e ->{
                managerReassign = !managerReassign;
                if (managerReassign){
                    managerIdField.setDisable(false);
                }
                else {
                    managerIdField.setDisable(true);
                }
            });

            submitButton.setOnAction(event -> {
                String message = InputValidator.validatingUser(emailField.getText(), passwordField.getText(), phoneNumberField.getText(), addressField.getText(), passwordValidationField.getText());
                if (message.equals("Success")){
                    if (managerReassign){
                        InsuranceManager insuranceManager = EmployeeAnalytics.findInsuranceManagerById(entityManager, managerIdField.getText());
                        if (insuranceManager == null){
                            errorContainer.setText("Incorrect Manager ID");
                        }
                        else {
                            EmployeeUpdate.updateInsuranceSurveyor(entityManager, insuranceSurveyor, addressField.getText(), phoneNumberField.getText(), emailField.getText(), passwordField.getText(), insuranceManager);
                        }
                    }
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
                    String id = RepeatedCode.idGenerate("IS");
                    EmployeeCreateRemove.createInsuranceSurveyor(entityManager, id, fullNameField.getText(), addressField.getText(), phoneNumberField.getText(), emailField.getText(), passwordField.getText(), manager );
                }
            });
        }

    }
}
