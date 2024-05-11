package org.example.insurancemanagementapplication.Controller.DashBoardController;

import Entity.PolicyHolder;
import jakarta.persistence.EntityManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import org.example.insurancemanagementapplication.Controller.DashBoardController.TableFillingController.DependantTableFilling;
import org.example.insurancemanagementapplication.Interfaces.ClaimRead;
import org.example.insurancemanagementapplication.Interfaces.Controller;
import org.example.insurancemanagementapplication.Interfaces.CustomerRead;

import java.net.URL;
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
        //See the ClaimTableFilling class
        userFillingData();


        //fill claim table
        fillingClaimTable(entityManager, user, ClaimRead.getAllClaimsFromABeneficiary(entityManager,user.getId()));


        //fill dependent table
        fillingDependantTable(entityManager,user, CustomerRead.getAllDependantsOfAPolicyHolder(entityManager, user.getId()));
    }

    public PolicyHolderDashBoardController(PolicyHolder user, EntityManager entityManager) {
        super(entityManager, user);
    }


}
