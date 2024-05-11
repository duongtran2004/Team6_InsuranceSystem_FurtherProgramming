package org.example.insurancemanagementapplication.Controller.DashBoardController;

import Entity.InsuranceSurveyor;
import jakarta.persistence.EntityManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import org.example.insurancemanagementapplication.Controller.DashBoardController.TableFillingController.PolicyOwnerTableFilling;
import org.example.insurancemanagementapplication.Interfaces.ClaimRead;
import org.example.insurancemanagementapplication.Interfaces.Controller;
import org.example.insurancemanagementapplication.Interfaces.CustomerRead;
import org.example.insurancemanagementapplication.Interfaces.InsuranceCardRead;

import java.net.URL;
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
        fillingClaimTable(entityManager, user, ClaimRead.getAllClaimsProcessByAnInsuranceSurveyor(entityManager, user.getId()));
        fillingDependantTable(entityManager, user, CustomerRead.getAllDependantsTakeChargeByAnEmployee(entityManager, user.getId(), "InsuranceSurveyor"));
        fillingPolicyHolderTable(entityManager, user, CustomerRead.getAllPolicyHoldersTakeChargeByAnEmployee(entityManager, user.getId(), "InsuranceSurveyor"));
        fillingPolicyOwnerTable(entityManager, user, CustomerRead.getAllPolicyOwnersTakeChargeByAnEmployee(entityManager, user.getId(), "InsuranceSurveyor"));
        fillingInsuranceCardTable(entityManager,user, InsuranceCardRead.getAllInsuranceCardsTakeChargeByAnEmployee(entityManager, user.getId(), "InsuranceSurveyor"));
    }

    public InsuranceSurveyorDashBoardController(InsuranceSurveyor user, EntityManager entityManager) {
        super(entityManager, user);

    }
}
