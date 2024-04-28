package org.example.insurancemanagementapplication.Controller;

import Entity.*;
import jakarta.persistence.EntityManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.example.insurancemanagementapplication.Interfaces.CustomerAnalytics;
import org.example.insurancemanagementapplication.Interfaces.CustomerCreateRemove;
import org.example.insurancemanagementapplication.Interfaces.EmployeeAnalytics;
import org.example.insurancemanagementapplication.Interfaces.EmployeeCreateRemove;
import org.example.insurancemanagementapplication.Utility.IteratorUtility;

import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

/**
 * @author Luong Thanh Trung
 * @version ${}
 * @created 27/04/2024 04:55
 * @project InsuranceManagementTeamProject
 */
public class SystemAdminController implements EmployeeCreateRemove, CustomerCreateRemove, Initializable, EmployeeAnalytics {
    private final EntityManager entityManager;
    private final SystemAdmin systemAdmin;
    private InsuranceSurveyor insuranceSurveyor;
    private InsuranceManager insuranceManager;
    private Customer customer;
    private Claim claim;

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


    //Insurance Manager Table
    @FXML
    private TableView<InsuranceManager> managerTable;
    @FXML
    private TableColumn<InsuranceManager, String> managerId;
    @FXML
    private TableColumn<InsuranceManager, String> managerFullName;
    @FXML
    private TableColumn<InsuranceManager, String> managerAddress;
    @FXML
    private TableColumn<InsuranceManager, String> managerPhoneNumber;
    @FXML
    private TableColumn<InsuranceManager, String> managerEmail;
    @FXML
    private TableColumn<InsuranceManager, String> managerPassword;
    @FXML
    private TableColumn<InsuranceManager, Button> managerUpdateInfoButton;
    @FXML
    private TableColumn<InsuranceManager, Button> managerAddSurveyorButton;

    //Insurance Surveyor Table
    @FXML
    private TableView<InsuranceSurveyor> surveyorTable;
    @FXML
    private TableColumn<InsuranceSurveyor, String> surveyorId;
    @FXML
    private TableColumn<InsuranceSurveyor, String> surveyorFullName;
    @FXML
    private TableColumn<InsuranceSurveyor, String> surveyorAddress;
    @FXML
    private TableColumn<InsuranceSurveyor, String> surveyorPhoneNumber;
    @FXML
    private TableColumn<InsuranceSurveyor, String> surveyorEmail;
    @FXML
    private TableColumn<InsuranceSurveyor, String> surveyorPassword;
    @FXML
    private TableColumn<InsuranceSurveyor, String> manager;
    @FXML
    private  TableColumn<InsuranceSurveyor, Button> surveyorUpdateInfoButton;

    //PolicyHolder Table
    @FXML
    private TableView<PolicyHolder> policyHolderTable;
    @FXML
    private TableColumn<PolicyHolder, String> policyHolderId;
    @FXML
    private TableColumn<PolicyHolder, String> policyHolderFullName;
    @FXML
    private TableColumn<PolicyHolder, String> policyHolderAddress;
    @FXML
    private TableColumn<PolicyHolder, String> policyHolderPhoneNumber;
    @FXML
    private TableColumn<PolicyHolder, String> policyHolderEmail;
    @FXML
    private TableColumn<PolicyHolder, String> policyHolderPassword;
    @FXML
    private TableColumn<PolicyHolder, String> policyOwnerHolderTable;
    @FXML
    private TableColumn<PolicyHolder, String> cardNumberHolderTable;
    @FXML
    private TableColumn<PolicyHolder, Button> policyHolderUpdateInfoButton;
    @FXML
    private TableColumn<PolicyHolder, Button> policyHolderAddDependantButton;


    //Dependant Table
    @FXML
    private TableView<Dependant> dependantTable;
    @FXML
    private TableColumn<Dependant, String> dependantId;
    @FXML
    private TableColumn<Dependant, String> dependantFullName;
    @FXML
    private TableColumn<Dependant, String> dependantAddress;
    @FXML
    private TableColumn<Dependant, String> dependantPhoneNumber;
    @FXML
    private TableColumn<Dependant, String> dependantEmail;
    @FXML
    private TableColumn<Dependant, String> dependantPassword;
    @FXML
    private TableColumn<Dependant, String> policyOwnerDependantTable;
    @FXML
    private TableColumn<Dependant, String> cardNumberDependantTable;
    @FXML
    private TableColumn<Dependant, Button> dependantUpdateInfoButton;

    //PolicyOwner Table
    @FXML
    private TableView<PolicyOwner> policyOwnerTable;
    @FXML
    private TableColumn<PolicyOwner, String> policyOwnerId;
    @FXML
    private TableColumn<PolicyOwner, String> policyOwnerFullName;
    @FXML
    private TableColumn<PolicyOwner, String> policyOwnerAddress;
    @FXML
    private TableColumn<PolicyOwner, String> policyOwnerPhoneNumber;
    @FXML
    private TableColumn<PolicyOwner, String> policyOwnerEmail;
    @FXML
    private TableColumn<PolicyOwner, String> policyOwnerPassword;
    @FXML
    private TableColumn<PolicyOwner, Button> policyOwnerUpdateInfoButton;
    @FXML
    private TableColumn<PolicyOwner, Button> policyOwnerAddPolicyButton;

    //Insurance Card Table
    @FXML
    private TableColumn<InsuranceCard, String> cardNumber;
    @FXML
    private TableColumn<InsuranceCard, Date> expiryDate;
    @FXML
    private TableColumn<InsuranceCard, String> cardHolder;
    @FXML
    private TableColumn<InsuranceCard, String> policyOwnerInsuranceCardTable;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<InsuranceManager> insuranceManagers = FXCollections.observableArrayList();
        IteratorUtility.addToObservableList(EmployeeAnalytics.getAllInsuranceManager(entityManager), insuranceManagers);

        ObservableList<InsuranceSurveyor> insuranceSurveyors = FXCollections.observableArrayList();
        IteratorUtility.addToObservableList(EmployeeAnalytics.getAllInsuranceSurveyor(entityManager), insuranceSurveyors);

        ObservableList<PolicyOwner> policyOwners = FXCollections.observableArrayList();
        IteratorUtility.addToObservableList(CustomerAnalytics.getAllPolicyOwner(entityManager), policyOwners);

        ObservableList<PolicyHolder> policyHolders = FXCollections.observableArrayList();
        IteratorUtility.addToObservableList(CustomerAnalytics.getAllPolicyHolder(entityManager), policyHolders);

        ObservableList<Dependant> dependants = FXCollections.observableArrayList();
        IteratorUtility.addToObservableList(CustomerAnalytics.getAllDependant(entityManager), dependants);

        ObservableList<InsuranceCard> insuranceCards = FXCollections.observableArrayList();
        IteratorUtility.addToObservableList(CustomerAnalytics.getAllInsuranceCard(entityManager), insuranceCards);
        
        ObservableList<Claim> claims = FXCollections.observableArrayList();


    }



    public SystemAdminController(EntityManager entityManager, SystemAdmin systemAdmin) {
        this.entityManager = entityManager;
        this.systemAdmin = systemAdmin;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }




}
