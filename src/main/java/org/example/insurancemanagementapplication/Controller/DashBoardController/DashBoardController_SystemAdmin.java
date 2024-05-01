package org.example.insurancemanagementapplication.Controller.DashBoardController;

import Entity.*;
import jakarta.persistence.EntityManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.example.insurancemanagementapplication.Controller.DashBoardController.TableFillingController.*;
import org.example.insurancemanagementapplication.Interfaces.*;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Luong Thanh Trung
 * @version ${}
 * @created 27/04/2024 04:55
 * @project InsuranceManagementTeamProject
 */
public class DashBoardController_SystemAdmin extends InsuranceManagerTableFilling implements ClaimAnalytics, EmployeeCreateRemove, CustomerCreateRemove, Initializable, EmployeeAnalytics {
    private final EntityManager entityManager;
    private final SystemAdmin user;

    private ObservableList<InsuranceManager> insuranceManagersObservableList = FXCollections.observableArrayList();
    private ObservableList<InsuranceSurveyor> insuranceSurveyorsObservableList = FXCollections.observableArrayList();
    private ObservableList<PolicyOwner> policyOwnersObservableList = FXCollections.observableArrayList();
    private ObservableList<PolicyHolder> policyHoldersObservableList = FXCollections.observableArrayList();
    private ObservableList<Dependant> dependantsObservableList = FXCollections.observableArrayList();
    private ObservableList<InsuranceCard> insuranceCardsObservableList = FXCollections.observableArrayList();
    private ObservableList<Claim> claimObservableList = FXCollections.observableArrayList();



    @FXML
    private TextField userIdField;
    @FXML
    private TextField fullNameField;
    @FXML
    private TextField addressField;
    @FXML
    private TextField phoneNumberField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField passwordField;
    @FXML
    private TextField passwordValidationField;
    @FXML
    private Button updateInfo;
    @FXML
    private Button addSurveyor;
    @FXML
    private Button addManager;
    @FXML
    private Label errorContainer;




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
       InsuranceManagerTableFilling.fillingInsuranceManagerTable(entityManager, user, EmployeeAnalytics.getAllInsuranceManager(entityManager), insuranceManagersObservableList);
       InsuranceSurveyorTableFilling.fillingInsuranceSurveyorTable(entityManager, user, EmployeeAnalytics.getAllInsuranceSurveyor(entityManager), insuranceSurveyorsObservableList);
       PolicyOwnerTableFilling.fillingPolicyOwnerTable(entityManager, user, CustomerAnalytics.getAllPolicyOwner(entityManager), policyOwnersObservableList);
       PolicyHolderTableFilling.fillingPolicyHolderTable(entityManager, user, CustomerAnalytics.getAllPolicyHolder(entityManager), policyHoldersObservableList);
       DependantTableFilling.fillingDependantTable(entityManager, user, CustomerAnalytics.getAllDependant(entityManager), dependantsObservableList);
       InsuranceSurveyorTableFilling.fillingInsuranceCardTable(entityManager, user, CustomerAnalytics.getAllInsuranceCard(entityManager), insuranceCardsObservableList);
       ClaimTableFilling.fillingClaimTable(entityManager, user, ClaimAnalytics.getAllClaims(entityManager), claimObservableList);


    }

    public DashBoardController_SystemAdmin(EntityManager entityManager, SystemAdmin systemAdmin) {
        this.entityManager = entityManager;
        this.user = systemAdmin;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }


}
