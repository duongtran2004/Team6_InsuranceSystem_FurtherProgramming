package org.example.insurancemanagementapplication.Controller.DashBoardController;

import Entity.SystemAdmin;
import Entity.User;
import jakarta.persistence.EntityManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.example.insurancemanagementapplication.Controller.CreationAndUpdatePageController.CreationAndUpdatePageControllerInsuranceManager;
import org.example.insurancemanagementapplication.Controller.CreationAndUpdatePageController.CreationAndUpdatePageControllerPolicyOwner;
import org.example.insurancemanagementapplication.Controller.DashBoardController.TableFillingController.ClaimTableFilling;
import org.example.insurancemanagementapplication.Controller.DashBoardController.TableFillingController.InsuranceManagerTableFilling;
import org.example.insurancemanagementapplication.Controller.LogInPageController;
import org.example.insurancemanagementapplication.Controller.Threads.*;
import org.example.insurancemanagementapplication.Interfaces.*;
import org.example.insurancemanagementapplication.Utility.InputValidator;
import org.example.insurancemanagementapplication.Utility.StageBuilder;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Luong Thanh Trung
 * @version ${}
 * @created 27/04/2024 04:55
 * @project InsuranceManagementTeamProject
 */
public class SystemAdminDashBoardController extends InsuranceManagerTableFilling implements ClaimRead, EmployeeCreateRemove, CustomerCreateRemove, Initializable, EmployeeRead, Controller {


    //import necessary FXML controller object for the creation and update form at the top
    @FXML
    private Button updateInfoButton;
    @FXML
    private Button addPolicyOwnerButton;
    @FXML
    private Button addManagerButton;


    //Cancel choices button
    @FXML
    protected Button
            clearCreationDateButton;
    @FXML
    protected Button
            clearSettlementDateButton;

    @FXML
    protected Button
            clearClaimAmountButton;
    //    private LogInPageController logInPageController;
    @FXML
    protected Button logOutButton;

    protected void handleLogOutButton() throws IOException {
//Set the current user to null
        user = null;
        StageBuilder.showStage((Stage) logOutButton.getScene().getWindow(), new LogInPageController(entityManager), "LogInPage.fxml", "Login Page");

    }

    // Event handler for clearing the creation date filter
    protected void handleClearCreationDateButton() {
        creationDateFrom.setValue(null);
        creationDateFrom.getEditor().clear();
        creationDateTo.setValue(null);
        creationDateTo.getEditor().clear();
        fillingClaimTable(entityManager, user, ClaimRead.getAllClaims(entityManager)); //refill claim table
    }

    // Event handler for clearing the settlement date filter

    protected void handleClearSettlementDateButton() {
        settlementDateFrom.setValue(null);
        settlementDateFrom.getEditor().clear();
        settlementDateTo.setValue(null);
        settlementDateTo.getEditor().clear();
        fillingClaimTable(entityManager, user, ClaimRead.getAllClaims(entityManager));
    }

    // Event handler for clearing the claim amount filter

    protected void handleClearClaimAmountButton() {
        claimAmountFrom.clear();
        claimAmountTo.clear();
        fillingClaimTable(entityManager, user, ClaimRead.getAllClaims(entityManager));
    }


    //constructor


    public SystemAdminDashBoardController(EntityManager entityManager, User user) {
        super(entityManager, user);

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        clearCreationDateButton.setOnAction(event -> handleClearCreationDateButton());
        clearSettlementDateButton.setOnAction(event -> handleClearSettlementDateButton());
        clearClaimAmountButton.setOnAction(event -> handleClearClaimAmountButton());
        logOutButton.setOnAction(event -> {
            try {
                handleLogOutButton();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        //UPDATE INFO
        //See the ClaimTableFilling Class for this method
        //automatically fill the top form with current user info

        System.out.println("User: " + user);
        userFillingData();
        //
        updateInfoButton.setOnAction(event -> {
            String message = InputValidator.validatingUser(emailField.getText(), passwordField.getText(), phoneNumberField.getText(), addressField.getText(), passwordValidationField.getText());
            if (message.equals("Success")) {
                EmployeeUpdate.updateSystemAdmin(entityManager, (SystemAdmin) user, addressField.getText(), phoneNumberField.getText(), addressField.getText(), passwordField.getText());
            } else {
                errorContainer.setText(message);
            }

        });
        // ADD A NEW POLICY OWNER USER

        //Add handler to the addPolicyOwnerButton. this function creates a CreationPage Controller for Policy Owner and then transfer the user to the Policy Owner Creation Page
        addPolicyOwnerButton.setOnAction(event -> {
            CreationAndUpdatePageControllerPolicyOwner creationPageControllerPolicyOwner = new CreationAndUpdatePageControllerPolicyOwner(entityManager, user);
            //See the RepeatedCode class for this method
            StageBuilder.showStage((Stage) addPolicyOwnerButton.getScene().getWindow(), creationPageControllerPolicyOwner, "PolicyOwnerCreationAndUpdatePage.fxml", "Policy Owner Creation Page");
        });

        // ADD A NEW INSURANCE MANAGER USER
        //Add handler to the addManagerButton. this function creates a CreationPage Controller for Insurance Manager and then transfer the user to the Insurance Manager Creation Page
        addManagerButton.setOnAction(event -> {
            CreationAndUpdatePageControllerInsuranceManager creationPageControllerInsuranceManager = new CreationAndUpdatePageControllerInsuranceManager(entityManager, user);
            StageBuilder.showStage((Stage) addManagerButton.getScene().getWindow(), creationPageControllerInsuranceManager, "InsuranceManagerCreationAndUpdatePage.fxml", "Insurance Manager Creation Page");


        });

        //FILL ALL THE NECESSARY TABLE (CALL METHODS IN TABLE FILLING CLASS)
        //METHOD FROM TABLE FILLING CLASS CALLS "READ ALL" METHODS IN READ INTERFACES (PASS ENTITY MANAGER AS ARGUMENT)

//using thread

        ClaimTableFillingThread claimTableFillingThread = new ClaimTableFillingThread(ClaimRead.getAllClaims(entityManager), this);
        claimTableFillingThread.start();

        DependantTableFillingThread dependantTableFillingThread = new DependantTableFillingThread(CustomerRead.getAllDependant(entityManager), this);
        dependantTableFillingThread.start();

        InsuranceManagerTableFillingThread insuranceManagerTableFillingThread = new InsuranceManagerTableFillingThread(EmployeeRead.getAllInsuranceManager(entityManager), this);
        insuranceManagerTableFillingThread.start();

        InsuranceSurveyorTableFillingThread insuranceSurveyorTableFillingThread = new InsuranceSurveyorTableFillingThread(EmployeeRead.getAllInsuranceSurveyor(entityManager), this);
        insuranceSurveyorTableFillingThread.start();

        PolicyHolderTableFillingThread policyHolderTableFillingThread = new PolicyHolderTableFillingThread(CustomerRead.getAllPolicyHolder(entityManager), this);
        policyHolderTableFillingThread.start();

        PolicyOwnerTableFillingThread policyOwnerTableFillingThread = new PolicyOwnerTableFillingThread(CustomerRead.getAllPolicyHolder(entityManager), this);
        policyOwnerTableFillingThread.start();

        InsuranceCardTableFillingThread insuranceCardTableFillingThread = new InsuranceCardTableFillingThread(InsuranceCardRead.getAllInsuranceCard(entityManager), this);
        insuranceCardTableFillingThread.start();


//        fillingInsuranceManagerTable(entityManager, user, EmployeeRead.getAllInsuranceManager(entityManager));
//        //Task: Create a separate thread to fill in the Insurance Surveyor Table
//        fillingInsuranceSurveyorTable(entityManager, user, EmployeeRead.getAllInsuranceSurveyor(entityManager));
//        //Task: Create a separate thread to fill in the Policy Owner Table
//        fillingPolicyOwnerTable(entityManager, user, CustomerRead.getAllPolicyOwner(entityManager));
//        //Task: Create a separate thread to fill in the Policy Holder Table
//        fillingPolicyHolderTable(entityManager, user, CustomerRead.getAllPolicyHolder(entityManager));
//        //Task: Create a separate thread to fill in the Dependant Table
//        fillingDependantTable(entityManager, user, CustomerRead.getAllDependant(entityManager));
//        //Task: Create a separate thread to fill in the Insurance Claim Table
//        fillingInsuranceCardTable(entityManager, user, InsuranceCardRead.getAllInsuranceCard(entityManager));
//        //Task: Create a separate thread to fill in the Claim Table
//        fillingClaimTable(entityManager, user, ClaimRead.getAllClaims(entityManager));
//        fillingActionHistoryTable(user);

    }


    public EntityManager getEntityManager() {
        return entityManager;
    }

    class SystemAdminClaimTableFilling extends Thread {
        ClaimTableFilling claimTableFilling = new ClaimTableFilling(user, entityManager);


    }

}
