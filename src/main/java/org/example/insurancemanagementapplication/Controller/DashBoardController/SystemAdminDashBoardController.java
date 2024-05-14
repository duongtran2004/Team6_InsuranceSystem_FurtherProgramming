package org.example.insurancemanagementapplication.Controller.DashBoardController;

import Entity.SystemAdmin;
import Entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
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
import java.util.Timer;

/**
 * @author Luong Thanh Trung
 * @version ${}
 * @created 27/04/2024 04:55
 * @project InsuranceManagementTeamProject
 */
public class SystemAdminDashBoardController extends InsuranceManagerTableFilling implements ClaimRead, EmployeeCreateRemove, CustomerCreateRemove, Initializable, EmployeeRead, Controller {

    private Timer AFKCountDownTimer = new Timer();
    private Timer refreshCountDownTimer = new Timer();
    private UserInactivityHandler userInactivityHandler; // Declare UserInactivityHandler instance
    //do we even need constructor for SADashBoard ?


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

    @FXML
    protected Button refreshButton;


    // Method to handle the RefreshButton click event
    public void handleRefreshButton() {

        // Reload the dashboard by creating a new dashboard object
        SystemAdminDashBoardController systemAdminDashBoardController = new SystemAdminDashBoardController(entityManager, user);
        // Show new DashBoard using stage builder
        StageBuilder.showStage((Stage) refreshButton.getScene().getWindow(), systemAdminDashBoardController, "SystemAdminDashboard.fxml", "System Admin Dashboard");
    }


    protected void handleLogOutButton() throws IOException {
        // Cancel timers when logging out
        userInactivityHandler.cancelTimers();
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


    @lombok.SneakyThrows
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        clearCreationDateButton.setOnAction(event -> handleClearCreationDateButton());
        clearSettlementDateButton.setOnAction(event -> handleClearSettlementDateButton());
        clearClaimAmountButton.setOnAction(event -> handleClearClaimAmountButton());
        refreshButton.setOnAction(event -> handleRefreshButton());
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
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManagerClaim = entityManagerFactory.createEntityManager();
        ClaimTableFillingThread claimTableFillingThread = new ClaimTableFillingThread(ClaimRead.getAllClaims(entityManagerClaim), this);
        claimTableFillingThread.setPriority(10);
        claimTableFillingThread.start();
        entityManagerClaim.close();


        EntityManager entityManagerDependant = entityManagerFactory.createEntityManager();
        DependantTableFillingThread dependantTableFillingThread = new DependantTableFillingThread(CustomerRead.getAllDependant(entityManagerDependant), this);
        dependantTableFillingThread.setPriority(9);
        dependantTableFillingThread.start();
        entityManagerDependant.close();

        EntityManager entityManagerManager = entityManagerFactory.createEntityManager();
        InsuranceManagerTableFillingThread insuranceManagerTableFillingThread = new InsuranceManagerTableFillingThread(EmployeeRead.getAllInsuranceManager(entityManagerManager), this);
        insuranceManagerTableFillingThread.setPriority(8);
        insuranceManagerTableFillingThread.start();
        entityManagerManager.close();

        EntityManager entityManagerSurveyor = entityManagerFactory.createEntityManager();
        InsuranceSurveyorTableFillingThread insuranceSurveyorTableFillingThread = new InsuranceSurveyorTableFillingThread(EmployeeRead.getAllInsuranceSurveyor(entityManagerSurveyor), this);
        insuranceSurveyorTableFillingThread.setPriority(7);
        insuranceSurveyorTableFillingThread.start();
        entityManagerSurveyor.close();

        EntityManager entityManagerHolder = entityManagerFactory.createEntityManager();
        PolicyHolderTableFillingThread policyHolderTableFillingThread = new PolicyHolderTableFillingThread(CustomerRead.getAllPolicyHolder(entityManagerHolder), this);
        policyHolderTableFillingThread.setPriority(6);
        policyHolderTableFillingThread.start();
        entityManagerHolder.close();

        EntityManager entityManagerOwner = entityManagerFactory.createEntityManager();
        PolicyOwnerTableFillingThread policyOwnerTableFillingThread = new PolicyOwnerTableFillingThread(CustomerRead.getAllPolicyOwner(entityManagerOwner), this);
        policyOwnerTableFillingThread.setPriority(5);
        policyOwnerTableFillingThread.start();
        entityManagerOwner.close();


        EntityManager entityManagerCard = entityManagerFactory.createEntityManager();
        InsuranceCardTableFillingThread insuranceCardTableFillingThread = new InsuranceCardTableFillingThread(InsuranceCardRead.getAllInsuranceCard(entityManagerCard), this);
        insuranceCardTableFillingThread.setPriority(4);
        insuranceCardTableFillingThread.start();
        entityManagerCard.close();

        entityManagerFactory.close();


        //add necessary buttons into the list
        buttonList.add(updateInfoButton);
        buttonList.add(addPolicyOwnerButton);
        buttonList.add(addManagerButton);
        buttonList.add(clearClaimAmountButton);
        buttonList.add(clearSettlementDateButton);


        // Initialize UserInactivityHandler
        userInactivityHandler = new UserInactivityHandler(user, refreshCountDownTimer, AFKCountDownTimer, buttonList);
        userInactivityHandler.initialize(buttonList);

        // Start the refresh countdown timer
        userInactivityHandler.startRefreshCountDown();

    }


    public EntityManager getEntityManager() {
        return entityManager;
    }

    class SystemAdminClaimTableFilling extends Thread {
        ClaimTableFilling claimTableFilling = new ClaimTableFilling(user, entityManager);


    }

}
