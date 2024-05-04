package org.example.insurancemanagementapplication.Controller.CreationPageController;

import Entity.PolicyOwner;
import Entity.User;
import jakarta.persistence.EntityManager;
import javafx.fxml.Initializable;
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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setActionReturnButton();
        if (selectedUser != null){
            fillingFormAuto();
            setHandlerForSubmitButtonInUserUpdateMode();
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



    }

    public CreationPageController_PolicyOwner(EntityManager entityManager, User user) {
        super(entityManager, user);
    }

    public CreationPageController_PolicyOwner(EntityManager entityManager, User user, PolicyOwner policyOwner) {
        super(entityManager, user, policyOwner);

    }


}
