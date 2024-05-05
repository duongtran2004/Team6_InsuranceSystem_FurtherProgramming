package org.example.insurancemanagementapplication.Controller.CreationPageController;

import Entity.Dependant;
import Entity.PolicyHolder;
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
 * @author Luong Thanh Trung
 * @version ${}
 * @created 29/04/2024 11:50
 * @project InsuranceManagementTeamProject
 */
public class CreationPageController_Dependant extends CreationPageController implements CustomerCreateRemove, CustomerUpdate, Initializable, Controller {
    private PolicyHolder policyHolder;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //See the CreationPageController class for this method
        setActionReturnButton();
        //When the controller is in update mode
        if (selectedUser != null) {
            //See the CreationPageController class for this method
            fillingFormAuto();
            //See the CrationPageController class for this method
            setHandlerForSubmitButtonInUserUpdateMode();
        }
        //When the controller is in creation mode
        else {
            submitButton.setOnAction(event -> {
                String message = InputValidator.validatingUser("Dependant", entityManager, fullNameField.getText(), emailField.getText(), passwordField.getText(), phoneNumberField.getText(), addressField.getText(), passwordValidationField.getText());
                if (message.equals("Success")){
                    //See the RepeatedCode class for this method
                    //This method generates an ID.
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
       super(entityManager, user, dependant);

    }


}
