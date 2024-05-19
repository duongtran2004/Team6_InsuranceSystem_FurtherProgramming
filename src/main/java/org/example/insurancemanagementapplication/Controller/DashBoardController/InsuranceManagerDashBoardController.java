package org.example.insurancemanagementapplication.Controller.DashBoardController;

import Entity.ActionHistory;
import Entity.Claim;
import Entity.InsuranceManager;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.example.insurancemanagementapplication.Controller.DashBoardController.TableFillingController.InsuranceSurveyorTableFilling;
import org.example.insurancemanagementapplication.Controller.LogInPageController;
import org.example.insurancemanagementapplication.Controller.Threads.*;
import org.example.insurancemanagementapplication.Interfaces.*;
import org.example.insurancemanagementapplication.Utility.InputValidator;
import org.example.insurancemanagementapplication.Utility.StageBuilder;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author Luong Thanh Trung
 * @version ${}
 * @created 27/04/2024 04:54
 * @project InsuranceManagementTeamProject
 */
public class InsuranceManagerDashBoardController extends InsuranceSurveyorTableFilling implements Initializable, Controller {


    public InsuranceManagerDashBoardController(InsuranceManager user, EntityManager entityManager) {
        super(entityManager, user);
    }

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

    @FXML
    protected Button logOutButton;
    @FXML
    protected Button updateInfoButton;
    @FXML
    protected Button refreshButton;

    protected void handleLogOutButton() throws IOException {
//Set the current user to null
        user = null;
        StageBuilder.showStage((Stage) logOutButton.getScene().getWindow(), new LogInPageController(entityManager), "LogInPage.fxml", "Login Page");

    }

    public void handleRefreshButton() {
        InsuranceManager insuranceManager = entityManager.find(InsuranceManager.class, user.getId());
        // Reload the dashboard by creating a new dashboard object
        InsuranceManagerDashBoardController insuranceManagerDashBoardController = new InsuranceManagerDashBoardController(insuranceManager, entityManager);

        // Show new DashBoard using stage builder
        StageBuilder.showStage((Stage) refreshButton.getScene().getWindow(), insuranceManagerDashBoardController, "InsuranceManagerDashBoard.fxml", "Insurance Manager Dashboard");
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        InsuranceManager insuranceManager = (InsuranceManager) user;
        logOutButton.setOnAction(event -> {
            try {
                handleLogOutButton();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        updateInfoButton.setOnAction(event -> {
            String message = InputValidator.validatingUser(emailField.getText(), passwordField.getText(), phoneNumberField.getText(), addressField.getText(), passwordValidationField.getText());
            errorContainer.setText(message);
            if (message.equals("Success")) {
                EmployeeUpdate.updateInsuranceManager(entityManager, (InsuranceManager) user, addressField.getText(), phoneNumberField.getText(), addressField.getText(), passwordField.getText());
                ActionHistory actionHistory = ActionHistoryCreate.createActionHistoryObject("UPDATE", "Insurance Manager", user.getId());
                ActionHistoryCreate.writeToActionHistoryObjectToFile(user.getId(), actionHistory);
            } else {
                errorContainer.setText(message);
            }

        });

        clearCreationDateButton.setOnAction(event -> handleClearCreationDateButton());
        clearSettlementDateButton.setOnAction(event -> handleClearSettlementDateButton());
        clearClaimAmountButton.setOnAction(event -> handleClearClaimAmountButton());
        //see the ClaimTableFilling Class
        userFillingData();
        fillingActionHistoryTable(user);
        //USE THREADS TO FILL TABLES


        ClaimTableFillingThread claimTableFillingThread = new ClaimTableFillingThread((List<Claim>) insuranceManager.getListOfClaims(), this);
        claimTableFillingThread.start();


        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManagerDependant = entityManagerFactory.createEntityManager();
        DependantTableFillingThread dependantTableFillingThread = new DependantTableFillingThread(CustomerRead.getAllDependantsTakeChargeByAnEmployee(entityManagerDependant, user.getId(), "InsuranceManager"), this);
        dependantTableFillingThread.start();
        entityManagerDependant.close();


        EntityManager entityManagerPolicyHolder = entityManagerFactory.createEntityManager();
        PolicyHolderTableFillingThread policyHolderTableFillingThread = new PolicyHolderTableFillingThread(CustomerRead.getAllPolicyHoldersTakeChargeByAnEmployee(entityManagerPolicyHolder, user.getId(), "InsuranceManager"), this);
        policyHolderTableFillingThread.start();
        entityManagerPolicyHolder.close();


        EntityManager entityManagerPolicyOwner = entityManagerFactory.createEntityManager();
        PolicyOwnerTableFillingThread policyOwnerTableFillingThread = new PolicyOwnerTableFillingThread(CustomerRead.getAllPolicyOwnersTakeChargeByAnEmployee(entityManagerPolicyOwner, user.getId(), "InsuranceManager"), this);
        policyOwnerTableFillingThread.start();
        entityManagerPolicyOwner.close();


        EntityManager entityManagerInsuranceCard = entityManagerFactory.createEntityManager();
        InsuranceCardTableFillingThread insuranceCardTableFillingThread = new InsuranceCardTableFillingThread(InsuranceCardRead.getAllInsuranceCardsTakeChargeByAnEmployee(entityManagerInsuranceCard, user.getId(), "InsuranceManager"), this);
        insuranceCardTableFillingThread.start();
        entityManagerInsuranceCard.close();

        EntityManager entityManagerInsuranceSurveyor = entityManagerFactory.createEntityManager();
        InsuranceSurveyorTableFillingThread insuranceSurveyorTableFillingThread = new InsuranceSurveyorTableFillingThread(EmployeeRead.getAllInsuranceSurveyorOfAnInsuranceManager(entityManagerInsuranceSurveyor, user.getId()), this);
        insuranceSurveyorTableFillingThread.start();
        entityManagerInsuranceSurveyor.close();

        fillingActionHistoryTable(user);


        // Initialize UserInactivityHandler
        userInactivityHandler = new UserInactivityHandler(user, refreshCountDownTimer, AFKCountDownTimer, buttonList);
        userInactivityHandler.initialize(buttonList);

        // Start the refresh countdown timer
        userInactivityHandler.startRefreshCountDown();

    }

}
