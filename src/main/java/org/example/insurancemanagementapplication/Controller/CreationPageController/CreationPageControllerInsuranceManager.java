package org.example.insurancemanagementapplication.Controller.CreationPageController;

import Entity.InsuranceManager;
import Entity.User;
import jakarta.persistence.EntityManager;
import javafx.fxml.Initializable;
import org.example.insurancemanagementapplication.Interfaces.Controller;
import org.example.insurancemanagementapplication.Interfaces.EmployeeCreateRemove;
import org.example.insurancemanagementapplication.Interfaces.EmployeeUpdate;
import org.example.insurancemanagementapplication.Utility.IDGenerator;
import org.example.insurancemanagementapplication.Utility.InputValidator;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Luong Thanh Trung
 * @version ${}
 * @created 29/04/2024 08:53
 * @project InsuranceManagementTeamProject
 */
public class CreationPageControllerInsuranceManager extends CreationPageController implements EmployeeCreateRemove, EmployeeUpdate, Initializable, Controller {

    public CreationPageControllerInsuranceManager(EntityManager entityManager, User user) {
        super(entityManager, user);
    }

    public CreationPageControllerInsuranceManager(EntityManager entityManager, User user, InsuranceManager insuranceManager) {
        super(entityManager, user, insuranceManager);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //See the CreationPageController Class for this method
        setActionReturnButton();
        //When the controller is in update mode
        if (selectedUser != null){
            //See the CreationPageController class for this method
            fillingFormAuto();
            //See the CreationPageController class for this method
            setHandlerForSubmitButtonInUserUpdateMode();
        }
        //When the controller is in creation mode
        else {
            submitButton.setOnAction(event -> {
                String message = InputValidator.validatingUser("Insurance Manager", entityManager, fullNameField.getText(), emailField.getText(), passwordField.getText(), phoneNumberField.getText(), addressField.getText(), passwordValidationField.getText());
                if (message.equals("Success")){
                    //See the RepeatedCode class for this method
                    //This method generates an id.
                    String id = IDGenerator.generateId("IM");
                    EmployeeCreateRemove.createInsuranceManager(entityManager, id, fullNameField.getText(), addressField.getText(), phoneNumberField.getText(), emailField.getText(), passwordField.getText());
                }
            });
        }
    }
}
