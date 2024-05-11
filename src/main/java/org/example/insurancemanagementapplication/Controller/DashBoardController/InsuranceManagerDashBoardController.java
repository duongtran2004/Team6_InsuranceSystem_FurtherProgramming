package org.example.insurancemanagementapplication.Controller.DashBoardController;

import Entity.InsuranceManager;
import jakarta.persistence.EntityManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import org.example.insurancemanagementapplication.Controller.DashBoardController.TableFillingController.InsuranceSurveyorTableFilling;
import org.example.insurancemanagementapplication.Interfaces.*;

import java.net.URL;
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

        clearCreationDateButton.setOnAction(event -> handleClearCreationDateButton());
        clearSettlementDateButton.setOnAction(event -> handleClearSettlementDateButton());
        clearClaimAmountButton.setOnAction(event -> handleClearClaimAmountButton());
        //see the ClaimTableFilling Class
        userFillingData();
        //fill claim table
        fillingClaimTable(entityManager, user, ClaimRead.getAllClaimsProcessByAnInsuranceManager(entityManager, user.getId()));
        //fill all the table of customers
        // fill dependant table
        fillingDependantTable(entityManager, user, CustomerRead.getAllDependantsTakeChargeByAnEmployee(entityManager, user.getId(), "InsuranceManager"));
        // fill policy holder table
        fillingPolicyHolderTable(entityManager, user, CustomerRead.getAllPolicyHoldersTakeChargeByAnEmployee(entityManager, user.getId(), "InsuranceManager"));
        // fill policy owner table
        fillingPolicyOwnerTable(entityManager, user, CustomerRead.getAllPolicyOwnersTakeChargeByAnEmployee(entityManager, user.getId(), "InsuranceManager"));
        // fill insurance card table
        fillingInsuranceCardTable(entityManager,user, InsuranceCardRead.getAllInsuranceCardsTakeChargeByAnEmployee(entityManager, user.getId(), "InsuranceManager"));

        //fill employee table
        //fill insurance surveyor table
        fillingInsuranceSurveyorTable(entityManager, user, EmployeeRead.getAllInsuranceSurveyorOfAnInsuranceManager(entityManager, user.getId()));


    }

}
