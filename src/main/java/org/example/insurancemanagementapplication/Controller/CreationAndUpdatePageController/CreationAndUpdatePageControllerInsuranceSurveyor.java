package org.example.insurancemanagementapplication.Controller.CreationAndUpdatePageController;

import Entity.ActionHistory;
import Entity.InsuranceManager;
import Entity.InsuranceSurveyor;
import Entity.User;
import jakarta.persistence.EntityManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.example.insurancemanagementapplication.Interfaces.*;
import org.example.insurancemanagementapplication.Utility.IDGenerator;
import org.example.insurancemanagementapplication.Utility.InputValidator;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author
 * @version ${}
 * @created 29/04/2024 11:48
 * @project InsuranceManagementTeamProject
 */
public class CreationAndUpdatePageControllerInsuranceSurveyor extends CreationAndUpdatePageController implements Initializable, EmployeeCreateRemove, EmployeeUpdate, Controller {

    private InsuranceManager manager;
    //This field will determine whether the application will update the Insurance Manager of the surveyor. True means Yes, False means No.
    private boolean managerReassign = false;

    //This field is disabled in creation mode. In update mode, it can be disabled or enabled by the managerReassign button
    @FXML
    private TextField managerIdField;

    //In update mode this button is used to enable or disable the managerId field
    @FXML
    private Button managerReassignButton;

    @FXML
    private Label pageTittleLabel;

//update insurance surveyor as System Admin
    public CreationAndUpdatePageControllerInsuranceSurveyor(EntityManager entityManager, User user) {
        super(entityManager, user);
    }

    //update insurance surveyor as Insurance Manager

    public CreationAndUpdatePageControllerInsuranceSurveyor(EntityManager entityManager, User user, InsuranceManager manager) {
        super(entityManager, user);
        this.manager = manager;
    }

    public CreationAndUpdatePageControllerInsuranceSurveyor(EntityManager entityManager, User user, User selected) {
        super(entityManager, user, selected);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //See the CreationAndUpdatePageController class for this method
        setActionReturnButton();

        managerIdField.setDisable(true);
        managerReassignButton.setDisable(true);

        //When the controller is in update mode
        if (selectedUser != null){
            InsuranceSurveyor insuranceSurveyor = (InsuranceSurveyor) selectedUser;
            changePageTitleInUpdateMode("INSURANCE SURVEYOR UPDATE PAGE");
            managerReassignButton.setDisable(false);
            managerIdField.setText(insuranceSurveyor.getInsuranceManagerId());
            //see the CreationAndUpdatePageController class for this method
            fillingFormAuto();

            //Setting handler for the managerReassignButton. It will first change the current boolean value of the managerReassign Field to the opposite value. Based on the new boolean value, the managerId field is either disabled or enabled
            managerReassignButton.setOnAction(e ->{
                managerReassign = !managerReassign;
                if (managerReassign){
                    managerIdField.setDisable(false);
                }
                else {
                    managerIdField.setDisable(true);
                }
            });

            submitButton.setOnAction(event -> {
                String message = InputValidator.validatingUser(emailField.getText(), passwordField.getText(), phoneNumberField.getText(), addressField.getText(), passwordValidationField.getText());
                errorContainer.setText(message);
                if (message.equals("Success")){
                    if (managerReassign){
                        InsuranceManager insuranceManager = EmployeeRead.findInsuranceManagerById(entityManager, managerIdField.getText());
                        if (insuranceManager == null){
                            errorContainer.setText("Incorrect Manager ID");
                        }
                        else {
                            EmployeeUpdate.updateInsuranceSurveyor(entityManager, insuranceSurveyor, addressField.getText(), phoneNumberField.getText(), emailField.getText(), passwordField.getText(), insuranceManager);
                        }
                    }
                    else {
                        EmployeeUpdate.updateInsuranceSurveyor(entityManager, insuranceSurveyor, addressField.getText(), phoneNumberField.getText(), emailField.getText(), passwordField.getText());
                    }
                    ActionHistory actionHistory = ActionHistoryCreate.createActionHistoryObject("UPDATE", "Insurance Surveyor", selectedUser.getId());
                    ActionHistoryCreate.writeToActionHistoryObjectToFile(user.getId(), actionHistory);
                }
                else {
                    errorContainer.setText(message);
                }
            });


        }
        //When the controller is in creation mode
        else {
            managerIdField.setText(manager.getId());
            submitButton.setOnAction(event -> {
                String message = InputValidator.validatingUser("Insurance Manager", entityManager, fullNameField.getText(), emailField.getText(), passwordField.getText(), phoneNumberField.getText(), addressField.getText(), passwordValidationField.getText());
                errorContainer.setText(message);
                if (message.equals("Success")){
                    String id = IDGenerator.generateId("IS");
                    EmployeeCreateRemove.createInsuranceSurveyor(entityManager, id, fullNameField.getText(), addressField.getText(), phoneNumberField.getText(), emailField.getText(), passwordField.getText(), manager );
                    ActionHistory actionHistory = ActionHistoryCreate.createActionHistoryObject("CREATE", "Insurance Surveyor", id);
                    ActionHistoryCreate.writeToActionHistoryObjectToFile(user.getId(), actionHistory);
                }
            });
        }

    }
}
