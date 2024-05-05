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
        //See the CreationPageController class
        setActionReturnButton();
        //When the controller is in update mode
        if (selectedUser != null){
            //See the CreationPageController class
            fillingFormAuto();
            //See the CreationPage Controller Class
            setHandlerForSubmitButtonInUserUpdateMode();
        }
        //When the controller is in creation mode
        else {
            submitButton.setOnAction(event -> {
                String message = InputValidator.validatingUser("Policy Owner", entityManager, fullNameField.getText(), emailField.getText(), passwordField.getText(), phoneNumberField.getText(), addressField.getText(), passwordValidationField.getText());
                if (message.equals("Success")){
                    //See the RepeatedCode class for this method
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
