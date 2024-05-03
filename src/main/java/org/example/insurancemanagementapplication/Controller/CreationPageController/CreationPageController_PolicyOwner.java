package org.example.insurancemanagementapplication.Controller.CreationPageController;

import Entity.PolicyOwner;
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
 * @author
 * @version ${}
 * @created 29/04/2024 11:49
 * @project InsuranceManagementTeamProject
 */
public class CreationPageController_PolicyOwner extends CreationPageController implements CustomerCreateRemove, CustomerUpdate, Initializable, Controller {
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
        setActionReturnButton();
        if (policyOwner != null){
            fullNameField.setText(policyOwner.getFullName());
            fullNameField.setDisable(true);
            addressField.setText(policyOwner.getAddress());
            phoneNumberField.setText(policyOwner.getPhoneNumber());
            emailField.setText(policyOwner.getEmail());
            passwordField.setText(policyOwner.getPassword());
            passwordValidationField.setText(policyOwner.getPassword());
            submitButton.setOnAction(event -> {
                String message = InputValidator.validatingUser(emailField.getText(), passwordField.getText(), phoneNumberField.getText(), addressField.getText(), passwordValidationField.getText());
                if (message.equals("Success")){
                    CustomerUpdate.updatePolicyOwner(entityManager, policyOwner, addressField.getText(), phoneNumberField.getText(), passwordField.getText(), passwordValidationField.getText());
                }
                else {
                    errorContainer.setText(message);
                }
            });


        }
        else {
            submitButton.setOnAction(event -> {
                String message = InputValidator.validatingUser("Policy Owner", entityManager, fullNameField.getText(), emailField.getText(), passwordField.getText(), phoneNumberField.getText(), addressField.getText(), passwordValidationField.getText());
                if (message.equals("Success")){
                    String id = RepeatedCode.idGenerate("PO");
                    CustomerCreateRemove.createPolicyOwner(entityManager, id, fullNameField.getText(), addressField.getText(), phoneNumberField.getText(), emailField.getText(), passwordField.getText());
                }
            });
        }
        submitButton.setOnAction(e ->{
            String fullName = fullNameField.getText();
            String phoneNumber = phoneNumberField.getText();
            String email = emailField.getText();
            String address = addressField.getText();
            String password = passwordField.getText();
            String passwordValidation = passwordValidationField.getText();
            String message = InputValidator.validatingUser("Policy Owner", entityManager, fullName, email, password, phoneNumber, address, passwordValidation);
            if (message.equals("Success")){
                if (policyOwner != null){
                    CustomerUpdate.updatePolicyOwner(entityManager, policyOwner, addressField.getText(), phoneNumberField.getText(), emailField.getText(), passwordField.getText());
                }
                else {
                    String id = RepeatedCode.idGenerate("PO");
                    CustomerCreateRemove.createPolicyOwner(entityManager, id, fullName, addressField.getText(), phoneNumberField.getText(), emailField.getText(), passwordField.getText());
                }

            }
            else{
                errorContainer.setText(message);
            }


        });




    }

    public CreationPageController_PolicyOwner(EntityManager entityManager, User user) {
        super(entityManager, user);
    }

    public CreationPageController_PolicyOwner(EntityManager entityManager, User user, PolicyOwner policyOwner) {
        super(entityManager, user);
        this.policyOwner = policyOwner;
    }


}
