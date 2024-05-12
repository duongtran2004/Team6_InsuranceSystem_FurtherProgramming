package org.example.insurancemanagementapplication.Controller.CreationAndUpdatePageController;

import Entity.ActionHistory;
import Entity.InsuranceManager;
import Entity.User;
import jakarta.persistence.EntityManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import org.example.insurancemanagementapplication.Interfaces.ActionHistoryCreate;
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
public class CreationAndUpdatePageControllerInsuranceManager extends CreationAndUpdatePageController implements EmployeeCreateRemove, EmployeeUpdate, Initializable, Controller {

    @FXML
    private Label pageTittleLabel;

    public CreationAndUpdatePageControllerInsuranceManager(EntityManager entityManager, User user) {
        super(entityManager, user);
    }

    public CreationAndUpdatePageControllerInsuranceManager(EntityManager entityManager, User user, InsuranceManager insuranceManager) {
        super(entityManager, user, insuranceManager);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //See the CreationAndUpdatePageController Class for this method
        setActionReturnButton();
        //When the controller is in update mode
        if (selectedUser != null) {
            changePageTittleInUpdateMode("INSURANCE MANAGER UPDATE PAGE");
            //See the CreationAndUpdatePageController class for this method
            fillingFormAuto();
            //See the CreationAndUpdatePageController class for this method
            setHandlerForSubmitButtonInUserUpdateMode();
        }
        //When the controller is in creation mode
        else {
            submitButton.setOnAction(event -> {
                System.out.println(
                        passwordField.getText()
                );
                System.out.println(
                        passwordValidationField.getText()
                );
                String message = InputValidator.validatingUser("Insurance Manager", entityManager, fullNameField.getText(), emailField.getText(), passwordField.getText(), phoneNumberField.getText(), addressField.getText(), passwordValidationField.getText());
                System.out.println(message);
                if (message.equals("Success")) {

                    //See the RepeatedCode class for this method
                    //This method generates an id.
                    String id = IDGenerator.generateId("IM");
                    EmployeeCreateRemove.createInsuranceManager(entityManager, id, fullNameField.getText(), addressField.getText(), phoneNumberField.getText(), emailField.getText(), passwordField.getText());

                    //record action history
                    ActionHistory actionHistory =
                            ActionHistoryCreate.createActionHistoryObject("CREATE", "Insurance Manager", id);
                    ActionHistoryCreate.writeToActionHistoryObjectToFile(user.getId(), actionHistory);
                }
            });
        }
    }
}
