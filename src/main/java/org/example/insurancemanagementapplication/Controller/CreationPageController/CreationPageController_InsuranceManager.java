package org.example.insurancemanagementapplication.Controller.CreationPageController;

import Entity.InsuranceManager;
import Entity.User;
import jakarta.persistence.EntityManager;
import javafx.fxml.Initializable;
import org.example.insurancemanagementapplication.Interfaces.Controller;
import org.example.insurancemanagementapplication.Interfaces.EmployeeCreateRemove;
import org.example.insurancemanagementapplication.Interfaces.EmployeeUpdate;
import org.example.insurancemanagementapplication.Utility.InputValidator;
import org.example.insurancemanagementapplication.Utility.RepeatedCode;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Luong Thanh Trung
 * @version ${}
 * @created 29/04/2024 08:53
 * @project InsuranceManagementTeamProject
 */
public class CreationPageController_InsuranceManager extends CreationPageController implements EmployeeCreateRemove, EmployeeUpdate, Initializable, Controller {

    public CreationPageController_InsuranceManager(EntityManager entityManager, User user) {
        super(entityManager, user);
    }

    public CreationPageController_InsuranceManager(EntityManager entityManager, User user, InsuranceManager insuranceManager) {
        super(entityManager, user, insuranceManager);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setActionReturnButton();
        if (selectedUser != null){
            fillingFormAuto();
            setHandlerForSubmitButtonInUserUpdateMode();
        }
        else {
            submitButton.setOnAction(event -> {
                String message = InputValidator.validatingUser("Insurance Manager", entityManager, fullNameField.getText(), emailField.getText(), passwordField.getText(), phoneNumberField.getText(), addressField.getText(), passwordValidationField.getText());
                if (message.equals("Success")){
                    String id = RepeatedCode.idGenerate("IM");
                    EmployeeCreateRemove.createInsuranceManager(entityManager, id, fullNameField.getText(), addressField.getText(), phoneNumberField.getText(), emailField.getText(), passwordField.getText());
                }
            });
        }
    }
}
