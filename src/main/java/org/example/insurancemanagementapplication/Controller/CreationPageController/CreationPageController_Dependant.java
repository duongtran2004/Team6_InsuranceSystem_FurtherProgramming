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
        setActionReturnButton();
        if (selectedUser != null) {
            autoFillingForm();
            setHandlerForSubmitButtonInUserUpdateMode();
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
       super(entityManager, user, dependant);

    }


}
