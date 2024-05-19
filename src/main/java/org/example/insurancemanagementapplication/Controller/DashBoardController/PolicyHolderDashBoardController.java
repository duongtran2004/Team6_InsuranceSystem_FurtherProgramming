package org.example.insurancemanagementapplication.Controller.DashBoardController;

import Entity.ActionHistory;
import Entity.Beneficiaries;
import Entity.Claim;
import Entity.PolicyHolder;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.example.insurancemanagementapplication.Controller.CreationAndUpdatePageController.CreationAndUpdatePageControllerClaim;
import org.example.insurancemanagementapplication.Controller.DashBoardController.TableFillingController.DependantTableFilling;
import org.example.insurancemanagementapplication.Controller.LogInPageController;
import org.example.insurancemanagementapplication.Controller.Threads.ClaimTableFillingThread;
import org.example.insurancemanagementapplication.Controller.Threads.DependantTableFillingThread;
import org.example.insurancemanagementapplication.Controller.Threads.UserInactivityHandler;
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
public class PolicyHolderDashBoardController extends DependantTableFilling implements Initializable, Controller {

    //Cancel choices button
    @FXML
    protected Button
            clearCreationDateButton;
    @FXML
    protected Button clearSettlementDateButton;

    @FXML
    protected Button clearClaimAmountButton;
    @FXML
    protected Button logOutButton;

    @FXML
    protected Button refreshButton;
    @FXML
    protected Button fileClaimButton;
    @FXML
    protected Button updateInfoButton;


    protected  void fileClaimButton(){
        CreationAndUpdatePageControllerClaim creationAndUpdatePageControllerClaim = new CreationAndUpdatePageControllerClaim(entityManager, user, (Beneficiaries) user);
        StageBuilder.showStage((Stage) fileClaimButton.getScene().getWindow(), creationAndUpdatePageControllerClaim, "ClaimCreationAndUpdatePage.fxml", "Claim Creation");
    }
    protected void handleLogOutButton() throws IOException {
//Set the current user to null
        user = null;
        StageBuilder.showStage((Stage) logOutButton.getScene().getWindow(), new LogInPageController(entityManager), "LogInPage.fxml", "Login Page");

    }

    public void handleRefreshButton() {

        // Reload the dashboard by creating a new dashboard object
        PolicyHolderDashBoardController policyHolderDashBoardController = new PolicyHolderDashBoardController((PolicyHolder) user, entityManager);

        // Show new DashBoard using stage builder
        StageBuilder.showStage((Stage) refreshButton.getScene().getWindow(), policyHolderDashBoardController, "PolicyHolderDashBoard.fxml", "Policy Holder Dashboard");
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
        refreshButton.setOnAction(event -> {
            handleRefreshButton();
        });
        updateInfoButton.setOnAction(event -> {
            String message = InputValidator.validatingUser(emailField.getText(), passwordField.getText(), phoneNumberField.getText(), addressField.getText(), passwordValidationField.getText());
            errorContainer.setText(message);
            if (message.equals("Success")) {
                CustomerUpdate.updatePolicyHolder(entityManager, (PolicyHolder) user, addressField.getText(), phoneNumberField.getText(), addressField.getText(), passwordField.getText());
                ActionHistory actionHistory = ActionHistoryCreate.createActionHistoryObject("UPDATE", "Policy Holder", user.getId());
                ActionHistoryCreate.writeToActionHistoryObjectToFile(user.getId(), actionHistory);
            } else {
                errorContainer.setText(message);
            }

        });
        logOutButton.setOnAction(event -> {
            try {
                handleLogOutButton();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        clearCreationDateButton.setOnAction(event -> handleClearCreationDateButton());
        clearSettlementDateButton.setOnAction(event -> handleClearSettlementDateButton());
        clearClaimAmountButton.setOnAction(event -> handleClearClaimAmountButton());
        //See the ClaimTableFilling class
        userFillingData();
        //FILLING TABLE USING THREADS

        PolicyHolder policyHolder = (PolicyHolder) user;
        ClaimTableFillingThread claimTableFillingThread = new ClaimTableFillingThread((List<Claim>) policyHolder.getListOfClaims(), this);
        claimTableFillingThread.start();

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManagerDependant = entityManagerFactory.createEntityManager();
        DependantTableFillingThread dependantTableFillingThread = new DependantTableFillingThread(CustomerRead.getAllDependantsOfAPolicyHolder(entityManagerDependant, user.getId()), this);
        dependantTableFillingThread.start();
        entityManagerDependant.close();
        entityManagerFactory.close();


        fillingActionHistoryTable(user);

        // Initialize UserInactivityHandler
        userInactivityHandler = new UserInactivityHandler(user, refreshCountDownTimer, AFKCountDownTimer, buttonList);
        userInactivityHandler.initialize(buttonList);

        // Start the refresh countdown timer
        userInactivityHandler.startRefreshCountDown();
    }

    public PolicyHolderDashBoardController(PolicyHolder user, EntityManager entityManager) {
        super(entityManager, user);
    }


}
