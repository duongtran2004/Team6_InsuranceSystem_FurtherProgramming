package org.example.insurancemanagementapplication.Controller.DashBoardController;

import Entity.Claim;
import Entity.Dependant;
import jakarta.persistence.EntityManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.example.insurancemanagementapplication.Controller.DashBoardController.TableFillingController.ClaimTableFilling;
import org.example.insurancemanagementapplication.Controller.LogInPageController;
import org.example.insurancemanagementapplication.Controller.Threads.UserInactivityHandler;
import org.example.insurancemanagementapplication.Interfaces.ClaimRead;
import org.example.insurancemanagementapplication.Interfaces.Controller;
import org.example.insurancemanagementapplication.Utility.StageBuilder;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author Luong Thanh Trung
 * @version ${}
 * @created 27/04/2024 04:53
 * @project InsuranceManagementTeamProject
 */
public class DependantDashBoardController extends ClaimTableFilling implements Initializable, Controller {
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
        //See the ClaimTableFilling class
        //aka fill the top form's default input as user's info
        userFillingData();


        //Fill claim table, no need to use thread. //only get the claim related to that user
        Dependant dependant = (Dependant) user;
        fillingClaimTable(entityManager, user, (List<Claim>) dependant.getListOfClaims());

        // Initialize UserInactivityHandler
        userInactivityHandler = new UserInactivityHandler(user, refreshCountDownTimer, AFKCountDownTimer, buttonList);
        userInactivityHandler.initialize(buttonList);

        // Start the refresh countdown timer
        userInactivityHandler.startRefreshCountDown();
     // no need to fill action history table for Dependant Dashboard


    }


    //constructor
    public DependantDashBoardController(Dependant user, EntityManager entityManager) {
        super(user, entityManager);
    }


}
