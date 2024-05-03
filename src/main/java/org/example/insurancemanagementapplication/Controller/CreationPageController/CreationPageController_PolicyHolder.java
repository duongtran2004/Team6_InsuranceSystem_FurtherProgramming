package org.example.insurancemanagementapplication.Controller.CreationPageController;

import Entity.InsuranceCard;
import Entity.PolicyHolder;
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
import java.util.Date;
import java.util.ResourceBundle;

/**
 * @author
 * @version ${}
 * @created 29/04/2024 11:50
 * @project InsuranceManagementTeamProject
 */
public class CreationPageController_PolicyHolder extends CreationPageController implements CustomerCreateRemove, CustomerUpdate, Initializable, Controller {
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
    @FXML
    private Button returnButton;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setActionReturnButton();
        if (policyHolder != null){
            fullNameField.setText(policyHolder.getFullName());
            fullNameField.setDisable(true);
            lengthOfContractField.setDisable(true);
            addressField.setText(policyHolder.getAddress());
            phoneNumberField.setText(policyHolder.getPhoneNumber());
            emailField.setText(policyHolder.getEmail());
            passwordField.setText(policyHolder.getPassword());
            passwordValidationField.setText(policyHolder.getPassword());

        }

        submitButton.setOnAction(e ->{
            String fullName = fullNameField.getText();
            String phoneNumber = phoneNumberField.getText();
            String email = emailField.getText();
            String address = addressField.getText();
            String password = passwordField.getText();
            String passwordValidation = passwordValidationField.getText();
            String message = InputValidator.validatingUser("Policy Holder", entityManager, fullName, email, password, phoneNumber, address, passwordValidation);
            if (message.equals("Success")){
                if (policyHolder != null){
                    CustomerUpdate.updatePolicyHolder(entityManager, policyHolder, addressField.getText(), phoneNumberField.getText(), emailField.getText(), passwordField.getText());
                }
                else {
                    String id = RepeatedCode.idGenerate("PH");
                    String cardNumber = RepeatedCode.idGenerate("");
                    Date utilDate = new Date();
                    int lengthOfContract;
                    try{
                        lengthOfContract = Integer.parseInt(lengthOfContractField.getText());
                        Date expiryUtilDate = new Date(utilDate.getYear() + lengthOfContract - 1, utilDate.getMonth(), utilDate.getDay());
                        InsuranceCard insuranceCard = new InsuranceCard();
                        insuranceCard.setCardNumber(cardNumber);
                        insuranceCard.setExpirationDate(new java.sql.Date(expiryUtilDate.getTime()));
                        CustomerCreateRemove.createPolicyHolder(entityManager, id, insuranceCard, fullName, addressField.getText(), phoneNumberField.getText(), emailField.getText(), passwordField.getText(), policyOwner);

                    } catch (NumberFormatException exception){
                        errorContainer.setText("Length of contract must be in numerical format");
                    }

                }

            }
            else{
                errorContainer.setText(message);
            }


        });



    }

    public CreationPageController_PolicyHolder(EntityManager entityManager, User user, PolicyOwner policyOwner) {
       super(entityManager, user);
        this.policyOwner = policyOwner;
    }

    public CreationPageController_PolicyHolder(EntityManager entityManager, User user, PolicyHolder policyHolder) {
        super(entityManager, user);
        this.policyHolder = policyHolder;
    }
}
