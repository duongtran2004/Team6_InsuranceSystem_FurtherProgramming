package org.example.insurancemanagementapplication.Controller.DashBoardController;

import Entity.Claim;
import Entity.InsuranceSurveyor;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.example.insurancemanagementapplication.Controller.DashBoardController.TableFillingController.PolicyOwnerTableFilling;
import org.example.insurancemanagementapplication.Controller.LogInPageController;
import org.example.insurancemanagementapplication.Controller.Threads.*;
import org.example.insurancemanagementapplication.Interfaces.ClaimRead;
import org.example.insurancemanagementapplication.Interfaces.Controller;
import org.example.insurancemanagementapplication.Interfaces.CustomerRead;
import org.example.insurancemanagementapplication.Interfaces.InsuranceCardRead;
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
public class InsuranceSurveyorDashBoardController extends PolicyOwnerTableFilling implements Initializable, Controller {

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
        //see the ClaimTableFilling Class
        userFillingData();

        //FILL TABLE USING THREADS

        InsuranceSurveyor insuranceSurveyor = (InsuranceSurveyor) user;

        ClaimTableFillingThread claimTableFillingThread = new ClaimTableFillingThread((List<Claim>) insuranceSurveyor.getListOfClaims(), this);
        claimTableFillingThread.start();

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManagerDependant = entityManagerFactory.createEntityManager();
        DependantTableFillingThread dependantTableFillingThread = new DependantTableFillingThread((CustomerRead.getAllDependantsTakeChargeByAnEmployee(entityManagerDependant, user.getId(), "InsuranceSurveyor")), this);
        dependantTableFillingThread.start();
        entityManagerDependant.close();


        EntityManager entityManagerPolicyHolder = entityManagerFactory.createEntityManager();
        PolicyHolderTableFillingThread policyHolderTableFillingThread = new PolicyHolderTableFillingThread(CustomerRead.getAllPolicyHoldersTakeChargeByAnEmployee(entityManagerPolicyHolder, user.getId(), "InsuranceSurveyor"), this);
        policyHolderTableFillingThread.start();
        entityManagerPolicyHolder.close();

        EntityManager entityManagerPolicyOwner = entityManagerFactory.createEntityManager();
        PolicyOwnerTableFillingThread policyOwnerTableFillingThread = new PolicyOwnerTableFillingThread(CustomerRead.getAllPolicyOwnersTakeChargeByAnEmployee(entityManagerPolicyOwner, user.getId(), "InsuranceSurveyor"), this);
        policyOwnerTableFillingThread.start();
        entityManagerPolicyOwner.close();


        EntityManager entityManagerInsuranceCard = entityManagerFactory.createEntityManager();
        InsuranceCardTableFillingThread insuranceCardTableFillingThread = new InsuranceCardTableFillingThread(InsuranceCardRead.getAllInsuranceCardsTakeChargeByAnEmployee(entityManagerInsuranceCard, user.getId(), "InsuranceSurveyor"), this);
        insuranceCardTableFillingThread.start();
        entityManagerInsuranceCard.close();

        entityManagerFactory.close();


        //        fillingClaimTable(entityManager, user, ClaimRead.getAllClaimsProcessByAnInsuranceSurveyor(entityManager, user.getId()));
//        fillingDependantTable(entityManager, user, CustomerRead.getAllDependantsTakeChargeByAnEmployee(entityManager, user.getId(), "InsuranceSurveyor"));
//        fillingPolicyHolderTable(entityManager, user, CustomerRead.getAllPolicyHoldersTakeChargeByAnEmployee(entityManager, user.getId(), "InsuranceSurveyor"));
//        fillingPolicyOwnerTable(entityManager, user, CustomerRead.getAllPolicyOwnersTakeChargeByAnEmployee(entityManager, user.getId(), "InsuranceSurveyor"));
//        fillingInsuranceCardTable(entityManager,user, InsuranceCardRead.getAllInsuranceCardsTakeChargeByAnEmployee(entityManager, user.getId(), "InsuranceSurveyor"));


        fillingActionHistoryTable(user);
    }

    public InsuranceSurveyorDashBoardController(InsuranceSurveyor user, EntityManager entityManager) {
        super(entityManager, user);

    }
}
