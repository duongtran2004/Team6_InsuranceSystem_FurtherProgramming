package org.example.insurancemanagementapplication.Controller.CreationAndUpdatePageController;

import Entity.InsuranceCard;
import Entity.PolicyHolder;
import Entity.PolicyOwner;
import Entity.User;
import jakarta.persistence.EntityManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.example.insurancemanagementapplication.Interfaces.Controller;
import org.example.insurancemanagementapplication.Interfaces.CustomerCreateRemove;
import org.example.insurancemanagementapplication.Interfaces.CustomerUpdate;
import org.example.insurancemanagementapplication.Utility.IDGenerator;
import org.example.insurancemanagementapplication.Utility.InputValidator;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * @author
 * @version ${}
 * @created 29/04/2024 11:50
 * @project InsuranceManagementTeamProject
 */
public class CreationAndUpdatePageControllerPolicyHolder extends CreationAndUpdatePageController implements CustomerCreateRemove, CustomerUpdate, Initializable, Controller {

    private PolicyOwner policyOwner;
    @FXML
    private TextField lengthOfContractField;
    @FXML
    private Label pageTittleLabel;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //See the CreationAndUpdatePageController class for this method
        setActionReturnButton();
        //when the controller is in update mode
        if (selectedUser != null){
            changePageTitleInUpdateMode("POLICY HOLDER UPDATE PAGE");
            lengthOfContractField.setDisable(true);
            //See the CreationAndUpdatePageController class for this method
            fillingFormAuto();
            //See the CreationAndUpdatePageController class for this method
            setHandlerForSubmitButtonInUserUpdateMode();

        }
        //when the controller is in creation mode
        else {
            submitButton.setOnAction(event -> {
                //String role, EntityManager entityManager, String fullName, String
                //            email, String password, String phoneNumber, String address, String passwordValidator)
                String message = InputValidator.validatingUser("Policy Holder", entityManager, fullNameField.getText(), emailField.getText(), passwordField.getText(), phoneNumberField.getText(), addressField.getText(), passwordValidationField.getText());
                if (message.equals("Success")){
                    //See the RepeatedCode class for this method.
                    //This method generates an ID.
                    String id = IDGenerator.generateId("PH");
                    String cardNumber = IDGenerator.generateId("");
                    Date utilDate = new Date();
                    int lengthOfContract;
                    try{
                        lengthOfContract = Integer.parseInt(lengthOfContractField.getText());
                        Date expiryUtilDate = new Date(utilDate.getYear() + lengthOfContract - 1, utilDate.getMonth(), utilDate.getDay());
                        InsuranceCard insuranceCard = new InsuranceCard();
                        insuranceCard.setCardNumber(cardNumber);
                        insuranceCard.setExpirationDate(new java.sql.Date(expiryUtilDate.getTime()));
                        CustomerCreateRemove.createPolicyHolder(submitButton, user, entityManager, id, insuranceCard, fullNameField.getText(), addressField.getText(), phoneNumberField.getText(), emailField.getText(), passwordField.getText(), policyOwner);

                    } catch (NumberFormatException exception){
                        errorContainer.setText("Length of contract must be in numerical format");
                    }
                }
                else {
                    errorContainer.setText(message);
                }

            });
        }


    }

    //Calling this constructor when the controller is to be open in creation mode
    public CreationAndUpdatePageControllerPolicyHolder(EntityManager entityManager, User user, PolicyOwner policyOwner) {
       super(entityManager, user);
        this.policyOwner = policyOwner;
    }

    //Call this constructor when the controller is to be open in update mode
    public CreationAndUpdatePageControllerPolicyHolder(EntityManager entityManager, User user, PolicyHolder policyHolder) {
        super(entityManager, user, policyHolder);
    }
}
