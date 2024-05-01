package org.example.insurancemanagementapplication.Controller.DashBoardController;

import Entity.SystemAdmin;
import jakarta.persistence.EntityManager;
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
       InsuranceManagerTableFilling.fillingInsuranceManagerTable(entityManager, user, EmployeeAnalytics.getAllInsuranceManager(entityManager));
       InsuranceSurveyorTableFilling.fillingInsuranceSurveyorTable(entityManager, user, EmployeeAnalytics.getAllInsuranceSurveyor(entityManager));
       PolicyOwnerTableFilling.fillingPolicyOwnerTable(entityManager, user, CustomerAnalytics.getAllPolicyOwner(entityManager));
       PolicyHolderTableFilling.fillingPolicyHolderTable(entityManager, user, CustomerAnalytics.getAllPolicyHolder(entityManager));
       DependantTableFilling.fillingDependantTable(entityManager, user, CustomerAnalytics.getAllDependant(entityManager));
       InsuranceSurveyorTableFilling.fillingDependantTable(entityManager, user, CustomerAnalytics.getAllDependant(entityManager));
       ClaimTableFilling.fillingClaimTable(entityManager, user, ClaimAnalytics.getAllClaims(entityManager));







    }



    public DashBoardController_SystemAdmin(EntityManager entityManager, SystemAdmin systemAdmin) {
        this.entityManager = entityManager;
        this.user = systemAdmin;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }






}
