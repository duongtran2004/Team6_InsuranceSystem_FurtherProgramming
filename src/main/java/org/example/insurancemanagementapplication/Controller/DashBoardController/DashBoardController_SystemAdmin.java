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
    //Task: Create a thread that get all Policy Holders from the table  and check if new entries exist. If they do, append the new entries to the Observable List
    private ObservableList<PolicyHolder> policyHoldersObservableList = FXCollections.observableArrayList();
    //Task: Create a thread that get all Dependants from the table  and check if new entries exist. If they do, append the new entries to the Observable List
    private ObservableList<Dependant> dependantsObservableList = FXCollections.observableArrayList();
    //Task: Create a thread that get all Insurance Cards from the table  and check if new entries exist. If they do, append the new entries to the Observable List
    private ObservableList<InsuranceCard> insuranceCardsObservableList = FXCollections.observableArrayList();
    //Task: Create a thread that get all claims from the table  and check if new entries exist. If they do, append the new entries to the Observable List
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
        //See the DashBoardController Class for this method
        userFillingData();
        //
        updateInfoButton.setOnAction(event -> {
            String message = InputValidator.validatingUser(emailField.getText(), passwordField.getText(), phoneNumberField.getText(), addressField.getText(), passwordValidationField.getText());
            if (message.equals("Success")){
                EmployeeUpdate.updateSystemAdmin(entityManager, (SystemAdmin) user, addressField.getText(), phoneNumberField.getText(), addressField.getText(), passwordField.getText());
            }
            else {
                errorContainer.setText(message);
            }

        });

        //Add handler to the addPolicyOwnerButton. this function creates a CreationPage Controller for Policy Owner and then transfer the user to the Policy Owner Creation Page
        addPolicyOwnerButton.setOnAction(event -> {
            CreationPageController_PolicyOwner creationPageControllerPolicyOwner = new CreationPageController_PolicyOwner(entityManager, user);
            //See the RepeatedCode class for this method
            RepeatedCode.showStage((Stage) addPolicyOwnerButton.getScene().getWindow(), creationPageControllerPolicyOwner, "PolicyOwnerCreationPage.fxml", "Policy Owner Creation Page");
        });
        //Add handler to the addManagerButton. this function creates a CreationPage Controller for Insurance Manager and then transfer the user to the Insurance Manager Creation Page
        addManagerButton.setOnAction(event -> {
            CreationPageController_InsuranceManager creationPageControllerInsuranceManager = new CreationPageController_InsuranceManager(entityManager, user);
            RepeatedCode.showStage((Stage) addManagerButton.getScene().getWindow(), creationPageControllerInsuranceManager, "InsuranceManagerCreationPage.fxml", "Insurance Manager Creation Page");
        });

        //Task: Create a separate thread to fill in Insurance Manager Table
        fillingInsuranceManagerTable(entityManager, user, EmployeeAnalytics.getAllInsuranceManager(entityManager), insuranceManagersObservableList);
        //Task: Create a separate thread to fill in the Insurance Surveyor Table
        fillingInsuranceSurveyorTable(entityManager, user, EmployeeAnalytics.getAllInsuranceSurveyor(entityManager), insuranceSurveyorsObservableList);
        //Task: Create a separate thread to fill in the Policy Owner Table
        fillingPolicyOwnerTable(entityManager, user, CustomerAnalytics.getAllPolicyOwner(entityManager), policyOwnersObservableList);
        //Task: Create a separate thread to fill in the Policy Holder Table
        fillingPolicyHolderTable(entityManager, user, CustomerAnalytics.getAllPolicyHolder(entityManager), policyHoldersObservableList);
        //Task: Create a separate thread to fill in the Dependant Table
        fillingDependantTable(entityManager, user, CustomerAnalytics.getAllDependant(entityManager), dependantsObservableList);
        //Task: Create a separate thread to fill in the Insurance Claim Table
        fillingInsuranceCardTable(entityManager, user, CustomerAnalytics.getAllInsuranceCard(entityManager), insuranceCardsObservableList);
        //Task: Create a separate thread to fill in the Claim Table
        fillingClaimTable(entityManager, user, ClaimAnalytics.getAllClaims(entityManager), claimObservableList);

    }



    public EntityManager getEntityManager() {
        return entityManager;
    }




}
