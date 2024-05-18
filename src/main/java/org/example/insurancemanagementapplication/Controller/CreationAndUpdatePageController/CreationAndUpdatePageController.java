package org.example.insurancemanagementapplication.Controller.CreationAndUpdatePageController;

import Entity.*;
import jakarta.persistence.EntityManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.example.insurancemanagementapplication.Controller.RootController;
import org.example.insurancemanagementapplication.Interfaces.CustomerUpdate;
import org.example.insurancemanagementapplication.Interfaces.EmployeeUpdate;
import org.example.insurancemanagementapplication.Utility.InputValidator;

/**
 * @author Luong Thanh Trung
 * @version ${}
 * @created 03/05/2024 09:57
 * @project InsuranceManagementTeamProject
 */
public abstract class CreationAndUpdatePageController extends RootController {
    protected User user;

    protected EntityManager entityManager;
    protected User selectedUser;
    @FXML
    protected Label pageTitleLabel;
    @FXML
    protected TextField fullNameField;
    @FXML
    protected TextField addressField;
    @FXML
    protected TextField phoneNumberField;
    @FXML
    protected TextField emailField;
    @FXML
    protected PasswordField passwordField;
    @FXML
    protected PasswordField passwordValidationField;
    @FXML
    protected Label errorContainer;
    @FXML
    protected Button submitButton;
    @FXML
    protected Button returnButton;


    public CreationAndUpdatePageController(EntityManager entityManager, User user, User selectedUser) {
        this.user = user;
        this.entityManager = entityManager;
        this.selectedUser = selectedUser;
    }

    public CreationAndUpdatePageController(EntityManager entityManager, User user) {
        this.user = user;
        this.entityManager = entityManager;
    }

    public void changePageTitleInUpdateMode(String newTitle) {
        pageTitleLabel.setText(newTitle);
    }

    /**
     * This method automatically fill form when the controller is in update mode
     */
    public void fillingFormAuto() {

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
    public void setHandlerForSubmitButtonInUserUpdateMode() {
        submitButton.setOnAction(event -> {
            String message = InputValidator.validatingUser(emailField.getText(), passwordField.getText(), phoneNumberField.getText(), addressField.getText(), passwordValidationField.getText());
            if (message.equals("Success")) {
                if (selectedUser instanceof Dependant) {
                    CustomerUpdate.updateDependant(entityManager, (Dependant) selectedUser, addressField.getText(), phoneNumberField.getText(), passwordField.getText(), passwordValidationField.getText());
                } else if (selectedUser instanceof PolicyHolder) {
                    CustomerUpdate.updatePolicyHolder(entityManager, (PolicyHolder) selectedUser, addressField.getText(), phoneNumberField.getText(), passwordField.getText(), passwordValidationField.getText());
                } else if (selectedUser instanceof PolicyOwner) {
                    CustomerUpdate.updatePolicyOwner(entityManager, (PolicyOwner) selectedUser, addressField.getText(), phoneNumberField.getText(), passwordField.getText(), passwordValidationField.getText());
                } else if (selectedUser instanceof InsuranceManager) {
                    EmployeeUpdate.updateInsuranceManager(entityManager, (InsuranceManager) selectedUser, addressField.getText(), phoneNumberField.getText(), passwordField.getText(), passwordValidationField.getText());
                }

            } else {
                errorContainer.setText(message);
            }
        });
    }

    /**
     * This method defines action for the return button. Pressing this button will return to the dashboard
     */
    public void setActionReturnButton() {
        returnButton.setOnAction(event -> {
            returnToDashBoard(user, entityManager, returnButton);
        });
    }
}
