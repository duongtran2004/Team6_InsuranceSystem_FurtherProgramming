package org.example.insurancemanagementapplication.Controller.CreationAndUpdatePageController;

import Entity.ActionHistory;
import Entity.Dependant;
import Entity.PolicyHolder;
import Entity.User;
import jakarta.persistence.EntityManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import org.example.insurancemanagementapplication.Interfaces.ActionHistoryCreate;
import org.example.insurancemanagementapplication.Interfaces.Controller;
import org.example.insurancemanagementapplication.Interfaces.CustomerCreateRemove;
import org.example.insurancemanagementapplication.Interfaces.CustomerUpdate;
import org.example.insurancemanagementapplication.Utility.IDGenerator;
import org.example.insurancemanagementapplication.Utility.InputValidator;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Luong Thanh Trung
 * @version ${}
 * @created 29/04/2024 11:50
 * @project InsuranceManagementTeamProject
 */
public class CreationAndUpdatePageControllerDependant extends CreationAndUpdatePageController implements CustomerCreateRemove, CustomerUpdate, Initializable, Controller {

    private PolicyHolder policyHolder;

    @FXML
    private Label pageTittleLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //See the CreationAndUpdatePageController class for this method
        setActionReturnButton();
        //When the controller is in update mode

        if (selectedUser != null) {
            changePageTitleInUpdateMode(" DEPENDANT UPDATE PAGE ");
            //See the CreationAndUpdatePageController class for this method
            fillingFormAuto();
            //See the CrationPageController class for this method
            setHandlerForSubmitButtonInUserUpdateMode();
        }
        //When the controller is in creation mode
        else {
            submitButton.setOnAction(event -> {
                String message = InputValidator.validatingUser("Dependant", entityManager, fullNameField.getText(), emailField.getText(), passwordField.getText(), phoneNumberField.getText(), addressField.getText(), passwordValidationField.getText());
                errorContainer.setText(message);
                if (message.equals("Success")) {
                    //See the RepeatedCode class for this method
                    //This method generates an ID.
                    String id = IDGenerator.generateId("DE");
                    CustomerCreateRemove.createDependant(entityManager, id, fullNameField.getText(), addressField.getText(), phoneNumberField.getText(), emailField.getText(), passwordField.getText(), policyHolder);
                    ActionHistory actionHistory = ActionHistoryCreate.createActionHistoryObject("CREATE", "Dependant", id);
                    ActionHistoryCreate.writeToActionHistoryObjectToFile(user.getId(), actionHistory);
                }
            });
        }


    }

    public CreationAndUpdatePageControllerDependant(EntityManager entityManager, User user, PolicyHolder policyHolder) {
        super(entityManager, user);
        this.policyHolder = policyHolder;
    }


    public CreationAndUpdatePageControllerDependant(EntityManager entityManager, User user, Dependant dependant) {
        super(entityManager, user, dependant);

    }


}
