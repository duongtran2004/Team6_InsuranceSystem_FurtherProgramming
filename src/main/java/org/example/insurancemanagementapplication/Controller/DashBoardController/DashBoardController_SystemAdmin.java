package org.example.insurancemanagementapplication.Controller.DashBoardController;

import Entity.*;
import jakarta.persistence.EntityManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.example.insurancemanagementapplication.Controller.DashBoardController.TableFillingController.*;
import org.example.insurancemanagementapplication.Interfaces.*;

import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

/**
 * @author Luong Thanh Trung
 * @version ${}
 * @created 27/04/2024 04:55
 * @project InsuranceManagementTeamProject
 */
public class DashBoardController_SystemAdmin extends InsuranceManagerTableFilling implements ClaimAnalytics, EmployeeCreateRemove, CustomerCreateRemove, Initializable, EmployeeAnalytics, Controller {
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
    @FXML
    private TableColumn<InsuranceManager, Button> managerRemoveButton;
    @FXML
    private TextField  insuranceManagerSearchField;

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
    @FXML
    private TableColumn<InsuranceSurveyor, Button> surveyorRemoveButton;
    @FXML
    private TextField insuranceSurveyorSearchField;

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
    @FXML
    private TableColumn<PolicyOwner, Button> policyOwnerRemoveButton;
    @FXML
    private TextField policyOwnerSearchField;



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
    @FXML
    private TableColumn<PolicyHolder, Button> policyHolderRemoveButton;
    @FXML
    private TextField policyHolderSearchField;

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
    @FXML
    private TableColumn<Dependant, Button> dependantRemoveButton;
    @FXML
    private TableColumn<Dependant, String> policyHolderDependantTable;
    @FXML
    private TextField dependantSearchField;


    @FXML
    private TableView<InsuranceCard> insuranceCardTable;
    @FXML
    private TableColumn<InsuranceCard, String> cardNumber;
    @FXML
    private TableColumn<InsuranceCard, Date> expiryDate;
    @FXML
    private TableColumn<InsuranceCard, String> cardHolderId;
    @FXML
    private TableColumn<InsuranceCard, String> policyOwnerInsuranceCardTable;
    @FXML
    private TableColumn<InsuranceCard, Button> insuranceCardRemoveButton;
    @FXML
    private TextField insuranceCardSearchField;


    @FXML
    private TableView<Claim> claimTable;
    @FXML
    private  TableColumn<Claim, String> claimId;
    @FXML
    private  TableColumn<Claim, Date> creationDate;
    @FXML
    private  TableColumn<Claim, String> insuredPersonId;
    @FXML
    private  TableColumn<Claim, String> cardNumberClaimTable;
    @FXML
    private  TableColumn<Claim, String> policyOwnerClaimTable;
    @FXML
    private  TableColumn<Claim, Integer> claimAmount;
    @FXML
    private  TableColumn<Claim, Date> settlementDate;
    @FXML
    private  TableColumn<Claim, String> status;
    @FXML
    private  TableColumn<Claim, Button> claimButton;
    @FXML
    private  TextField claimListSearchField;
    @FXML
    private  ChoiceBox<String> sortList;
    @FXML
    private  ChoiceBox<String> statusList;
    @FXML
    private  DatePicker creationDateFrom;
    @FXML
    private  DatePicker creationDateTo;
    @FXML
    private  DatePicker settlementDateFrom;
    @FXML
    private  DatePicker settlementDateTo;
    @FXML
    private  TextField claimAmountFrom;
    @FXML
    private  TextField claimAmountTo;



    public void userFillingData (){
        userIdField.setText(user.getId());
        fullNameField.setText(user.getFullName());
        addressField.setText(user.getAddress());
        phoneNumberField.setText(user.getPhoneNumber());
        emailField.setText(user.getEmail());
        passwordField.setText(user.getPassword());
        passwordValidationField.setText(user.getPassword());
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        userFillingData();
        System.out.println("Hello World");

        InsuranceManagerTableFilling insuranceManagerTableFilling = instantiateInsuranceManagerTableFilling();
        insuranceManagerTableFilling.fillingInsuranceManagerTable(entityManager, user, EmployeeAnalytics.getAllInsuranceManager(entityManager), insuranceManagersObservableList);

        System.out.println("Hello Insurance Surveyor");
        InsuranceSurveyorTableFilling insuranceSurveyorTableFilling = instantiateInsuranceSurveyorTableFilling();
        insuranceSurveyorTableFilling.fillingInsuranceSurveyorTable(entityManager, user, EmployeeAnalytics.getAllInsuranceSurveyor(entityManager), insuranceSurveyorsObservableList);

        System.out.println("Hello Policy Owner");
        PolicyOwnerTableFilling policyOwnerTableFilling = instantiatePolicyOwnerTableFilling();
        policyOwnerTableFilling.fillingPolicyOwnerTable(entityManager, user, CustomerAnalytics.getAllPolicyOwner(entityManager), policyOwnersObservableList);

        System.out.println("Holder");
        PolicyHolderTableFilling policyHolderTableFilling = instantiatePolicyHolderTableFilling();
        policyHolderTableFilling.fillingPolicyHolderTable(entityManager, user, CustomerAnalytics.getAllPolicyHolder(entityManager), policyHoldersObservableList);

        System.out.println("Dependant");
        DependantTableFilling dependantTableFilling = instantiateDependantTableFilling();
        dependantTableFilling.fillingDependantTable(entityManager, user, CustomerAnalytics.getAllDependant(entityManager), dependantsObservableList);


        InsuranceCardFillingTable insuranceCardFillingTable = instantiateInsuranceCardFillingTable();
        System.out.println("Hello Card");
        if (!CustomerAnalytics.getAllInsuranceCard(entityManager).isEmpty()){
            insuranceCardFillingTable.fillingInsuranceCardTable(entityManager, user, CustomerAnalytics.getAllInsuranceCard(entityManager), insuranceCardsObservableList);
        }

        ClaimTableFilling claimTableFilling = instantiateClaimTableFilling();
        if (!ClaimAnalytics.getAllClaims(entityManager).isEmpty()){
            claimTableFilling.fillingClaimTable(entityManager, user, ClaimAnalytics.getAllClaims(entityManager), claimObservableList);
        }





    }

    public DashBoardController_SystemAdmin(EntityManager entityManager, SystemAdmin systemAdmin) {
        this.entityManager = entityManager;
        this.user = systemAdmin;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public InsuranceManagerTableFilling instantiateInsuranceManagerTableFilling(){
        InsuranceManagerTableFilling insuranceManagerTableFilling = new InsuranceManagerTableFilling();
        insuranceManagerTableFilling.setManagerTable(managerTable);
        insuranceManagerTableFilling.setManagerId(managerId);
        insuranceManagerTableFilling.setManagerFullName(managerFullName);
        insuranceManagerTableFilling.setManagerAddress(managerAddress);
        insuranceManagerTableFilling.setManagerPhoneNumber(managerPhoneNumber);
        insuranceManagerTableFilling.setManagerEmail(managerEmail);
        insuranceManagerTableFilling.setManagerPassword(managerPassword);
        insuranceManagerTableFilling.setManagerRemoveButton(managerRemoveButton);
        insuranceManagerTableFilling.setManagerAddSurveyorButton(managerAddSurveyorButton);
        insuranceManagerTableFilling.setInsuranceManagerSearchField(insuranceManagerSearchField);
        insuranceManagerTableFilling.setManagerUpdateInfoButton(managerUpdateInfoButton);
        return insuranceManagerTableFilling;
    }

    public InsuranceSurveyorTableFilling instantiateInsuranceSurveyorTableFilling(){
        InsuranceSurveyorTableFilling insuranceSurveyorTableFilling = new InsuranceSurveyorTableFilling();
        insuranceSurveyorTableFilling.setSurveyorTable(surveyorTable);
        insuranceSurveyorTableFilling.setSurveyorId(surveyorId);
        insuranceSurveyorTableFilling.setSurveyorFullName(surveyorFullName);
        insuranceSurveyorTableFilling.setSurveyorAddress(surveyorAddress);
        insuranceSurveyorTableFilling.setSurveyorPhoneNumber(surveyorPhoneNumber);
        insuranceSurveyorTableFilling.setSurveyorEmail(surveyorEmail);
        insuranceSurveyorTableFilling.setSurveyorPassword(surveyorPassword);
        insuranceSurveyorTableFilling.setManager(manager);
        insuranceSurveyorTableFilling.setInsuranceSurveyorSearchField(insuranceSurveyorSearchField);
        insuranceSurveyorTableFilling.setSurveyorUpdateInfoButton(surveyorUpdateInfoButton);
        insuranceSurveyorTableFilling.setSurveyorRemoveButton(surveyorRemoveButton);
        return insuranceSurveyorTableFilling;
    }

    public PolicyOwnerTableFilling instantiatePolicyOwnerTableFilling(){
        PolicyOwnerTableFilling policyOwnerTableFilling = new PolicyOwnerTableFilling();
        policyOwnerTableFilling.setPolicyOwnerTable(policyOwnerTable);
        policyOwnerTableFilling.setPolicyOwnerId(policyOwnerId);
        policyOwnerTableFilling.setPolicyOwnerFullName(policyOwnerFullName);
        policyOwnerTableFilling.setPolicyOwnerAddress(policyOwnerAddress);
        policyOwnerTableFilling.setPolicyOwnerPhoneNumber(policyOwnerPhoneNumber);
        policyOwnerTableFilling.setPolicyOwnerEmail(policyOwnerEmail);
        policyOwnerTableFilling.setPolicyOwnerPassword(policyOwnerPassword);
        policyOwnerTableFilling.setPolicyOwnerSearchField(policyOwnerSearchField);
        policyOwnerTableFilling.setPolicyOwnerAddPolicyButton(policyOwnerAddPolicyButton);
        policyOwnerTableFilling.setPolicyOwnerRemoveButton(policyOwnerRemoveButton);
        policyOwnerTableFilling.setPolicyOwnerUpdateInfoButton(policyOwnerUpdateInfoButton);
        return policyOwnerTableFilling;
    }

    public PolicyHolderTableFilling instantiatePolicyHolderTableFilling(){
        PolicyHolderTableFilling policyHolderTableFilling = new PolicyHolderTableFilling();
        policyHolderTableFilling.setPolicyHolderTable(policyHolderTable);
        policyHolderTableFilling.setPolicyHolderId(policyHolderId);
        policyHolderTableFilling.setPolicyHolderFullName(policyHolderFullName);
        policyHolderTableFilling.setPolicyHolderAddress(policyHolderAddress);
        policyHolderTableFilling.setPolicyHolderPhoneNumber(policyHolderPhoneNumber);
        policyHolderTableFilling.setPolicyHolderEmail(policyHolderEmail);
        policyHolderTableFilling.setPolicyHolderPassword(policyHolderPassword);
        policyHolderTableFilling.setPolicyOwnerHolderTable(policyOwnerHolderTable);
        policyHolderTableFilling.setCardNumberHolderTable(cardNumberHolderTable);
        policyHolderTableFilling.setPolicyHolderSearchField(policyHolderSearchField);
        policyHolderTableFilling.setPolicyHolderUpdateInfoButton(policyHolderUpdateInfoButton);
        policyHolderTableFilling.setPolicyHolderRemoveButton(policyHolderRemoveButton);
        policyHolderTableFilling.setPolicyHolderAddDependantButton(policyHolderAddDependantButton);
        policyHolderTableFilling.setPolicyHolderRemoveButton(policyHolderRemoveButton);
        return policyHolderTableFilling;
    }

    public DependantTableFilling instantiateDependantTableFilling(){
        DependantTableFilling dependantTableFilling = new DependantTableFilling();
        dependantTableFilling.setDependantTable(dependantTable);
        dependantTableFilling.setDependantId(dependantId);
        dependantTableFilling.setDependantFullName(dependantFullName);
        dependantTableFilling.setDependantEmail(dependantEmail);
        dependantTableFilling.setDependantAddress(dependantAddress);
        dependantTableFilling.setDependantPhoneNumber(dependantPhoneNumber);
        dependantTableFilling.setDependantPassword(dependantPassword);
        dependantTableFilling.setPolicyHolderDependantTable(policyHolderDependantTable);
        dependantTableFilling.setPolicyOwnerDependantTable(policyOwnerDependantTable);
        dependantTableFilling.setCardNumberDependantTable(cardNumberDependantTable);
        dependantTableFilling.setDependantSearchField(dependantSearchField);
        dependantTableFilling.setDependantRemoveButton(dependantRemoveButton);
        dependantTableFilling.setDependantUpdateInfoButton(dependantUpdateInfoButton);
        return dependantTableFilling;
    }

    public InsuranceCardFillingTable instantiateInsuranceCardFillingTable(){
        InsuranceCardFillingTable insuranceCardFillingTable = new InsuranceCardFillingTable();
        insuranceCardFillingTable.setInsuranceCardTable(insuranceCardTable);
        insuranceCardFillingTable.setCardNumber(cardNumber);
        insuranceCardFillingTable.setCardHolderId(cardHolderId);
        insuranceCardFillingTable.setPolicyOwnerInsuranceCardTable(policyOwnerInsuranceCardTable);
        insuranceCardFillingTable.setExpiryDate(expiryDate);
        insuranceCardFillingTable.setInsuranceCardSearchField(insuranceCardSearchField);
        insuranceCardFillingTable.setInsuranceCardRemoveButton(insuranceCardRemoveButton);
        return insuranceCardFillingTable;
    }

    public ClaimTableFilling instantiateClaimTableFilling(){
        ClaimTableFilling claimTableFilling = new ClaimTableFilling();
        claimTableFilling.setClaimTable(claimTable);
        claimTableFilling.setClaimId(claimId);
        claimTableFilling.setClaimAmount(claimAmount);
        claimTableFilling.setInsuredPersonId(insuredPersonId);
        claimTableFilling.setPolicyOwnerClaimTable(policyOwnerClaimTable);
        claimTableFilling.setCardNumberClaimTable(cardNumberClaimTable);
        claimTableFilling.setCreationDate(creationDate);
        claimTableFilling.setSettlementDate(settlementDate);
        claimTableFilling.setStatus(status);
        claimTableFilling.setClaimAmountTo(claimAmountTo);
        claimTableFilling.setClaimAmountFrom(claimAmountFrom);
        claimTableFilling.setCreationDateFrom(creationDateFrom);
        claimTableFilling.setCreationDateTo(creationDateTo);
        claimTableFilling.setSettlementDateFrom(settlementDateFrom);
        claimTableFilling.setSettlementDateTo(settlementDateTo);
        claimTableFilling.setClaimListSearchField(claimListSearchField);
        claimTableFilling.setSortList(sortList);
        claimTableFilling.setStatusList(statusList);
        claimTableFilling.setClaimButton(claimButton);
        return claimTableFilling;
    }


}
