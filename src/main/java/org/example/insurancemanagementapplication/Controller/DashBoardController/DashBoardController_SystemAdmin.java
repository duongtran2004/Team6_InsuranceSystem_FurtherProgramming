package org.example.insurancemanagementapplication.Controller.DashBoardController;

import Entity.*;
import jakarta.persistence.EntityManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.example.insurancemanagementapplication.Controller.CreationPageController.CreationPageController_InsuranceManager;
import org.example.insurancemanagementapplication.Controller.CreationPageController.CreationPageController_PolicyOwner;
import org.example.insurancemanagementapplication.Controller.DashBoardController.TableFillingController.InsuranceManagerTableFilling;
import org.example.insurancemanagementapplication.Interfaces.*;
import org.example.insurancemanagementapplication.Utility.InputValidator;
import org.example.insurancemanagementapplication.Utility.RepeatedCode;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Luong Thanh Trung
 * @version ${}
 * @created 27/04/2024 04:55
 * @project InsuranceManagementTeamProject
 */
public class DashBoardController_SystemAdmin extends InsuranceManagerTableFilling implements ClaimAnalytics, EmployeeCreateRemove, CustomerCreateRemove, Initializable, EmployeeAnalytics, Controller {


    private ObservableList<InsuranceManager> insuranceManagersObservableList = FXCollections.observableArrayList();
    private ObservableList<InsuranceSurveyor> insuranceSurveyorsObservableList = FXCollections.observableArrayList();
    private ObservableList<PolicyOwner> policyOwnersObservableList = FXCollections.observableArrayList();
    private ObservableList<PolicyHolder> policyHoldersObservableList = FXCollections.observableArrayList();
    private ObservableList<Dependant> dependantsObservableList = FXCollections.observableArrayList();
    private ObservableList<InsuranceCard> insuranceCardsObservableList = FXCollections.observableArrayList();
    private ObservableList<Claim> claimObservableList = FXCollections.observableArrayList();



    @FXML
    private Button updateInfoButton;
    @FXML
    private Button addPolicyOwnerButton;
    @FXML
    private Button addManagerButton;


    public DashBoardController_SystemAdmin(EntityManager entityManager, User user) {
        super(entityManager, user);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userFillingData();
        updateInfoButton.setOnAction(event -> {
            String message = InputValidator.validatingUser(emailField.getText(), passwordField.getText(), phoneNumberField.getText(), addressField.getText(), passwordValidationField.getText());
            if (message.equals("Success")){
                EmployeeUpdate.updateSystemAdmin(entityManager, (SystemAdmin) user, addressField.getText(), phoneNumberField.getText(), addressField.getText(), passwordField.getText());
            }
            else {
                errorContainer.setText(message);
            }

        });

        addPolicyOwnerButton.setOnAction(event -> {
            CreationPageController_PolicyOwner creationPageControllerPolicyOwner = new CreationPageController_PolicyOwner(entityManager, user);
            RepeatedCode.showStage((Stage) addPolicyOwnerButton.getScene().getWindow(), creationPageControllerPolicyOwner, "PolicyOwnerCreationPage.fxml", "Policy Owner Creation Page");
        });

        addManagerButton.setOnAction(event -> {
            CreationPageController_InsuranceManager creationPageControllerInsuranceManager = new CreationPageController_InsuranceManager(entityManager, user);
            RepeatedCode.showStage((Stage) addManagerButton.getScene().getWindow(), creationPageControllerInsuranceManager, "InsuranceManagerCreationPage.fxml", "Insurance Manager Creation Page");
        });

        fillingInsuranceManagerTable(entityManager, user, EmployeeAnalytics.getAllInsuranceManager(entityManager), insuranceManagersObservableList);
        fillingInsuranceSurveyorTable(entityManager, user, EmployeeAnalytics.getAllInsuranceSurveyor(entityManager), insuranceSurveyorsObservableList);
        fillingPolicyOwnerTable(entityManager, user, CustomerAnalytics.getAllPolicyOwner(entityManager), policyOwnersObservableList);
        fillingPolicyHolderTable(entityManager, user, CustomerAnalytics.getAllPolicyHolder(entityManager), policyHoldersObservableList);
        fillingDependantTable(entityManager, user, CustomerAnalytics.getAllDependant(entityManager), dependantsObservableList);
        fillingInsuranceCardTable(entityManager, user, CustomerAnalytics.getAllInsuranceCard(entityManager), insuranceCardsObservableList);
        fillingClaimTable(entityManager, user, ClaimAnalytics.getAllClaims(entityManager), claimObservableList);






    }



    public EntityManager getEntityManager() {
        return entityManager;
    }




}
