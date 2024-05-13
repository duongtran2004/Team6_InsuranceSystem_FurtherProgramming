package org.example.insurancemanagementapplication.Controller.DashBoardController;

import Entity.Claim;
import Entity.PolicyOwner;
import jakarta.persistence.EntityManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.example.insurancemanagementapplication.Controller.CreationAndUpdatePageController.CreationAndUpdatePageControllerPolicyHolder;
import org.example.insurancemanagementapplication.Controller.DashBoardController.TableFillingController.PolicyHolderTableFilling;
import org.example.insurancemanagementapplication.Controller.LogInPageController;
import org.example.insurancemanagementapplication.Controller.Threads.ClaimTableFillingThread;
import org.example.insurancemanagementapplication.Controller.Threads.DependantTableFillingThread;
import org.example.insurancemanagementapplication.Controller.Threads.InsuranceCardTableFillingThread;
import org.example.insurancemanagementapplication.Controller.Threads.PolicyHolderTableFillingThread;
import org.example.insurancemanagementapplication.Interfaces.*;
import org.example.insurancemanagementapplication.Utility.StageBuilder;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Luong Thanh Trung
 * @version ${}
 * @created 27/04/2024 04:54
 * @project InsuranceManagementTeamProject
 */
public class PolicyOwnerDashBoardController extends PolicyHolderTableFilling implements CustomerCreateRemove, Initializable, Controller {

    @FXML
    private javafx.scene.control.Button addPolicyHolderButton;
    private ObservableList<Claim> claimObservableList = FXCollections.observableArrayList();
    //For Policy Owner to see total yearly rate
    @FXML
    protected Label totalYearlyRateLabel;
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
    private LogInPageController logInPageController;

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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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
        //fill table
        //see the ClaimTableFilling Class
        userFillingData();

        int yearlyRate = YearlyRateCalculation.calculateYearlyRateOfAPolicyOwner(entityManager, user.getId());
        totalYearlyRateLabel.setText(String.valueOf(yearlyRate));

        //event handling for button
        addPolicyHolderButton.setOnAction(event -> {
            CreationAndUpdatePageControllerPolicyHolder creationPageControllerPolicyHolder = new CreationAndUpdatePageControllerPolicyHolder(entityManager, user, (PolicyOwner) user);
            StageBuilder.showStage((Stage) addPolicyHolderButton.getScene().getWindow(), creationPageControllerPolicyHolder, "PolicyHolderCreationAndUpdatePage.fxml", "PolicyHolderCreationPage");
        });

        //TABLE FILLING: NOW USING THREADS


        ClaimTableFillingThread claimTableFillingThread = new ClaimTableFillingThread(ClaimRead.getAllClaimsFromBeneficiariesOfAPolicyOwner(entityManager, user.getId()), this);
        claimTableFillingThread.start();

        DependantTableFillingThread dependantTableFillingThread = new DependantTableFillingThread(CustomerRead.getAllDependantsOfAPolicyOwner(entityManager, user.getId()), this);
        dependantTableFillingThread.start();

        PolicyHolderTableFillingThread policyHolderTableFillingThread = new PolicyHolderTableFillingThread(CustomerRead.getAllPolicyHoldersOfAPolicyOwner(entityManager, user.getId()), this);
        policyHolderTableFillingThread.start();

        InsuranceCardTableFillingThread insuranceCardTableFillingThread = new InsuranceCardTableFillingThread(InsuranceCardRead.getAllInsuranceCardsOfPolicyOwner(entityManager, user.getId()), this);
        insuranceCardTableFillingThread.start();

//        fillingClaimTable(entityManager, user, ClaimRead.getAllClaimsFromBeneficiariesOfAPolicyOwner(entityManager, user.getId()));
//        fillingDependantTable(entityManager, user, CustomerRead.getAllDependantsOfAPolicyOwner(entityManager, user.getId()));
//        fillingPolicyHolderTable(entityManager, user, CustomerRead.getAllPolicyHoldersOfAPolicyOwner(entityManager, user.getId()));
//        fillingInsuranceCardTable(entityManager, user, InsuranceCardRead.getAllInsuranceCardsOfPolicyOwner(entityManager, user.getId()));


        fillingActionHistoryTable(user);
    }

    public PolicyOwnerDashBoardController(PolicyOwner user, EntityManager entityManager) {
        super(entityManager, user);
    }


}
