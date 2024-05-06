package org.example.insurancemanagementapplication.Controller.CreationPageController;

import Entity.*;
import jakarta.persistence.EntityManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.insurancemanagementapplication.Controller.DashBoardController.*;
import org.example.insurancemanagementapplication.Interfaces.CustomerUpdate;
import org.example.insurancemanagementapplication.Interfaces.EmployeeUpdate;
import org.example.insurancemanagementapplication.Utility.InputValidator;
import org.example.insurancemanagementapplication.Utility.StageBuilder;

/**
 * @author Luong Thanh Trung
 * @version ${}
 * @created 03/05/2024 09:57
 * @project InsuranceManagementTeamProject
 */
public abstract class CreationPageController {
    protected User user;

    protected EntityManager entityManager;
    protected User selectedUser;
    @FXML
    protected TextField fullNameField;
    @FXML
    protected TextField addressField;
    @FXML
    protected TextField phoneNumberField;
    @FXML
    protected TextField emailField;
    @FXML
    protected TextField passwordField;
    @FXML
    protected TextField passwordValidationField;
    @FXML
    protected Label errorContainer;
    @FXML Button submitButton;
    @FXML
    protected Button returnButton;



    public CreationPageController(EntityManager entityManager, User user, User selectedUser) {
        this.user = user;
        this.entityManager = entityManager;
        this.selectedUser = selectedUser;
    }

    public CreationPageController(EntityManager entityManager, User user) {
        this.user = user;
        this.entityManager = entityManager;
    }

    /**
     * This method automatically fill form when the controller is in update mode
     */
    public void fillingFormAuto(){
        fullNameField.setDisable(true);
        fullNameField.setText(selectedUser.getFullName());
        addressField.setText(selectedUser.getAddress());
        phoneNumberField.setText(selectedUser.getPhoneNumber());
        emailField.setText(selectedUser.getEmail());
        passwordField.setText(selectedUser.getPassword());
        passwordValidationField.setText(selectedUser.getPassword());
    }

    /**
     * This method defines the action for the submit button when the controller is in update mode
     */
    public void setHandlerForSubmitButtonInUserUpdateMode(){
        submitButton.setOnAction(event -> {
            String message = InputValidator.validatingUser(emailField.getText(), passwordField.getText(), phoneNumberField.getText(), addressField.getText(), passwordValidationField.getText());
            if (message.equals("Success")){
                if (selectedUser instanceof Dependant){
                    CustomerUpdate.updateDependant(entityManager, (Dependant) selectedUser, addressField.getText(), phoneNumberField.getText(), passwordField.getText(), passwordValidationField.getText());
                }
                else if (selectedUser instanceof PolicyHolder){
                    CustomerUpdate.updatePolicyHolder(entityManager, (PolicyHolder) selectedUser, addressField.getText(), phoneNumberField.getText(), passwordField.getText(), passwordValidationField.getText());
                }
                else if (selectedUser instanceof PolicyOwner){
                    CustomerUpdate.updatePolicyOwner(entityManager, (PolicyOwner) selectedUser, addressField.getText(), phoneNumberField.getText(), passwordField.getText(), passwordValidationField.getText());
                }
                else if (selectedUser instanceof InsuranceManager){
                    EmployeeUpdate.updateInsuranceManager(entityManager, (InsuranceManager) selectedUser, addressField.getText(), phoneNumberField.getText(), passwordField.getText(), passwordValidationField.getText());
                }

            }
            else {
                errorContainer.setText(message);
            }
        });
    }

    /**
     * This method defines action for the return button. Pressing this button will return to the dashboard
     */
    public void setActionReturnButton(){
        returnButton.setOnAction(event -> {
            if (user instanceof SystemAdmin) {
                SystemAdminDashBoardController dashBoardControllerSystemAdmin = new SystemAdminDashBoardController(entityManager, (SystemAdmin) user);
                StageBuilder.showStage((Stage) returnButton.getScene().getWindow(), dashBoardControllerSystemAdmin, "SystemAdminDashBoard.fxml", "Dashboard");
            }
            else if (user instanceof InsuranceManager) {
                InsuranceManagerDashBoardController dashBoardControllerInsuranceManager = new InsuranceManagerDashBoardController((InsuranceManager) user, entityManager);
                StageBuilder.showStage((Stage) returnButton.getScene().getWindow(), dashBoardControllerInsuranceManager, "InsuranceManagerDashBoard.fxml", "Dashboard");

            }
            else if (user instanceof InsuranceSurveyor) {

                InsuranceSurveyorDashBoardController dashBoardControllerInsuranceSurveyor = new InsuranceSurveyorDashBoardController((InsuranceSurveyor) user, entityManager);
                StageBuilder.showStage((Stage) returnButton.getScene().getWindow(), dashBoardControllerInsuranceSurveyor, "InsuranceSurveyorDashBoard.fxml", "Dashboard");

            }
            else if (user instanceof PolicyOwner) {
                PolicyOwnerDashBoardController dashBoardController_policyOwner = new PolicyOwnerDashBoardController((PolicyOwner) user, entityManager);
                StageBuilder.showStage((Stage) returnButton.getScene().getWindow(), dashBoardController_policyOwner, "PolicyOwnerDashBoard.fxml", "Dashboard");

            }
            else if (user instanceof PolicyHolder) {
                PolicyHolderDashBoardController dashBoardControllerPolicyHolder = new PolicyHolderDashBoardController((PolicyHolder) user, entityManager);
                StageBuilder.showStage((Stage) returnButton.getScene().getWindow(), dashBoardControllerPolicyHolder, "PolicyHolderDashBoard.fxml", "Dashboard");

            }


        });
    }
}
