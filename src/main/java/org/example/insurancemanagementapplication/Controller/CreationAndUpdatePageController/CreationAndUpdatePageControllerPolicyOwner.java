package org.example.insurancemanagementapplication.Controller.CreationAndUpdatePageController;

import Entity.PolicyOwner;
import Entity.User;
import jakarta.persistence.EntityManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import org.example.insurancemanagementapplication.Interfaces.Controller;
import org.example.insurancemanagementapplication.Interfaces.CustomerCreateRemove;
import org.example.insurancemanagementapplication.Interfaces.CustomerUpdate;
import org.example.insurancemanagementapplication.Utility.IDGenerator;
import org.example.insurancemanagementapplication.Utility.InputValidator;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author
 * @version ${}
 * @created 29/04/2024 11:49
 * @project InsuranceManagementTeamProject
 */
public class CreationAndUpdatePageControllerPolicyOwner extends CreationAndUpdatePageController implements CustomerCreateRemove, CustomerUpdate, Initializable, Controller {

    @FXML
    Label pageTittleLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //See the CreationAndUpdatePageController class
        setActionReturnButton();
        //When the controller is in update mode
        if (selectedUser != null) {
            changePageTitleInUpdateMode(" POLICY OWNER UPDATE PAGE");
            //See the CreationAndUpdatePageController class
            fillingFormAuto();
            //See the CreationPage Controller Class
            setHandlerForSubmitButtonInUserUpdateMode();
        }
        //When the controller is in creation mode
        else {
            submitButton.setOnAction(event -> {
                String message = InputValidator.validatingUser("Policy Owner", entityManager, fullNameField.getText(), emailField.getText(), passwordField.getText(), phoneNumberField.getText(), addressField.getText(), passwordValidationField.getText());
                if (message.equals("Success")) {
                    //See the RepeatedCode class for this method
                    String id = IDGenerator.generateId("PO");
                    CustomerCreateRemove.createPolicyOwner(entityManager, id, fullNameField.getText(), addressField.getText(), phoneNumberField.getText(), emailField.getText(), passwordField.getText());
                }
            });
        }


    }

    public CreationAndUpdatePageControllerPolicyOwner(EntityManager entityManager, User user) {
        super(entityManager, user);
    }

    public CreationAndUpdatePageControllerPolicyOwner(EntityManager entityManager, User user, PolicyOwner policyOwner) {
        super(entityManager, user, policyOwner);

    }


}
