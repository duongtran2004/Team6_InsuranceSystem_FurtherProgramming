package org.example.insurancemanagementapplication.Controller.CreationPageController;

import Entity.Dependant;
import Entity.PolicyHolder;
import Entity.User;
import jakarta.persistence.EntityManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.example.insurancemanagementapplication.Interfaces.Controller;
import org.example.insurancemanagementapplication.Interfaces.CustomerCreateRemove;
import org.example.insurancemanagementapplication.Interfaces.CustomerUpdate;
import org.example.insurancemanagementapplication.Utility.InputValidator;
import org.example.insurancemanagementapplication.Utility.RepeatedCode;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Luong Thanh Trung
 * @version ${}
 * @created 29/04/2024 11:50
 * @project InsuranceManagementTeamProject
 */
public class CreationPageController_Dependant extends CreationPageController implements CustomerCreateRemove, CustomerUpdate, Initializable, Controller {
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
        setActionReturnButton();
        if (dependant != null) {
            fullNameField.setDisable(true);
            fullNameField.setText(dependant.getFullName());
            addressField.setText(dependant.getAddress());
            phoneNumberField.setText(dependant.getPhoneNumber());
            emailField.setText(dependant.getEmail());
            passwordField.setText(dependant.getPassword());
            passwordValidationField.setText(dependant.getPassword());
            submitButton.setOnAction(event -> {
                String message = InputValidator.validatingUser(emailField.getText(), passwordField.getText(), phoneNumberField.getText(), addressField.getText(), passwordValidationField.getText());
                if (message.equals("Success")){
                    CustomerUpdate.updateDependant(entityManager, dependant, addressField.getText(), phoneNumberField.getText(), passwordField.getText(), passwordValidationField.getText());
                }
                else {
                    errorContainer.setText(message);
                }
            });
        }
        else {
            submitButton.setOnAction(event -> {
                String message = InputValidator.validatingUser("Dependant", entityManager, fullNameField.getText(), emailField.getText(), passwordField.getText(), phoneNumberField.getText(), addressField.getText(), passwordValidationField.getText());
                if (message.equals("Success")){
                    String id = RepeatedCode.idGenerate("DE");
                    CustomerCreateRemove.createDependant(entityManager, id, fullNameField.getText(), addressField.getText(), phoneNumberField.getText(), emailField.getText(), passwordField.getText(), policyHolder);
                }
            });
        }


    }

    public CreationPageController_Dependant(EntityManager entityManager, User user, PolicyHolder policyHolder) {
        super(entityManager, user);
        this.policyHolder = policyHolder;
    }


    public CreationPageController_Dependant(EntityManager entityManager, User user, Dependant dependant) {
       super(entityManager, user);
        this.dependant = dependant;
    }


}
