package org.example.insurancemanagementapplication.Controller;

import Entity.*;
import jakarta.persistence.EntityManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.example.insurancemanagementapplication.Interfaces.*;
import org.example.insurancemanagementapplication.MainEntryPoint;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.Comparator;
import java.util.List;
import java.util.ListIterator;
import java.util.ResourceBundle;

/**
 * @author Luong Thanh Trung
 * @version ${}
 * @created 27/04/2024 04:55
 * @project InsuranceManagementTeamProject
 */
public class DashBoardController_SystemAdmin implements ClaimAnalytics, EmployeeCreateRemove, CustomerCreateRemove, Initializable, EmployeeAnalytics {
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
    @FXML
    private TextField insuranceManagerSearchField;

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
    @FXML
    private TextField insuranceSurveyorSearchField;

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
    @FXML
    private TextField policyHolderSearchField;


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
    @FXML
    private TableColumn<Dependant, String> policyHolderDependantTable;
    @FXML
    private TextField dependantSearchField;

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
    @FXML
    private TextField policyOwnerSearchField;

    //Insurance Card Table
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
    @FXML TableColumn<InsuranceCard, Button> insuranceCardRemoveButton;
    @FXML
    private TextField insuranceCardSearchField;

    @FXML
    private TableView<Claim> claimTable;
    @FXML
    private TableColumn<Claim, String> claimId;
    @FXML
    private TableColumn<Claim, Date> creationDate;
    @FXML
    private TableColumn<Claim, String> insuredPersonId;
    @FXML
    private TableColumn<Claim, String> cardNumberClaimTable;
    @FXML
    private TableColumn<Claim, String> policyOwnerClaimTable;
    @FXML
    private TableColumn<Claim, Float> claimAmount;
    @FXML
    private TableColumn<Claim, Date> settlementDate;
    @FXML
    private TableColumn<Claim, String> status;
    @FXML
    private TableColumn<Claim, Button> claimButton;
    @FXML
    private TextField claimListSearchField;
    @FXML
    private ChoiceBox<String> sortList;
    @FXML
    private ChoiceBox<String> statusList;
    @FXML
    private DatePicker creationDateFrom;
    @FXML
    private DatePicker creationDateTo;
    @FXML
    private DatePicker settlementDateFrom;
    @FXML
    private DatePicker settlementDateTo;
    @FXML
    private TextField claimAmountFrom;
    @FXML
    private TextField claimAmountTo;





    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        /**
         * Filling the insurance manager table with data
         * Add methods to Buttons
         */
        ObservableList<InsuranceManager> insuranceManagersObservableList = FXCollections.observableArrayList();
        List<InsuranceManager> insuranceManagers = EmployeeAnalytics.getAllInsuranceManager(entityManager);
        ListIterator<InsuranceManager> listIteratorInsuranceManager = insuranceManagers.listIterator();
        while (listIteratorInsuranceManager.hasNext()){
            InsuranceManager insuranceManager = listIteratorInsuranceManager.next();
            Button buttonUpdateInfo = new Button("Update Info");
            insuranceManager.setUpdateInfoButton(buttonUpdateInfo);
            buttonUpdateInfo.setUserData(insuranceManager);
            buttonUpdateInfo.setOnAction(event -> {
                CreationPageController_InsuranceManager insuranceManagerCreationPageController = new CreationPageController_InsuranceManager(entityManager, user, (InsuranceManager) buttonUpdateInfo.getUserData());
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(MainEntryPoint.class.getResource("InsuranceManagerCreationPage.fxml"));
                fxmlLoader.setController(insuranceManagerCreationPageController);
                try {
                    Scene scene = new Scene(fxmlLoader.load());
                    Stage stage = (Stage) buttonUpdateInfo.getScene().getWindow();
                    stage.setScene(scene);
                    stage.show();

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            Button buttonAddSurveyor = new Button("Add Surveyor");
            insuranceManager.setAddSurveyorButton(buttonAddSurveyor);
            buttonAddSurveyor.setUserData(insuranceManager);
            buttonAddSurveyor.setOnAction(event ->{
                CreationPageController_InsuranceSurveyor creationPageControllerInsuranceSurveyor = new CreationPageController_InsuranceSurveyor(entityManager, user, (InsuranceManager) buttonAddSurveyor.getUserData());
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(MainEntryPoint.class.getResource("InsuranceSurveyorCreationPage.fxml"));
                fxmlLoader.setController(creationPageControllerInsuranceSurveyor);
                try {
                    Scene scene = new Scene(fxmlLoader.load());
                    Stage stage = (Stage) buttonAddSurveyor.getScene().getWindow();
                    stage.setScene(scene);
                    stage.show();

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            Button buttonRemove = new Button("Remove");
            insuranceManager.setRemoveButton(buttonRemove);
            buttonRemove.setOnAction(event -> {
                EmployeeCreateRemove.removeInsuranceManager(entityManager, insuranceManager);
            });



            insuranceManagersObservableList.add(insuranceManager);
        }
        FilteredList<InsuranceManager> filteredManagerList = new FilteredList<>(insuranceManagersObservableList, b -> true);
        insuranceManagerSearchField.textProperty().addListener((observable, oldValue, newValue)->{
            filteredManagerList.setPredicate(insuranceManager -> {
                if (newValue.isEmpty() || newValue == null || newValue.isBlank()){
                    return true;
                }
                String searchValue = newValue.toLowerCase();
                if (insuranceManager.getId().equals(searchValue)){
                    return true;
                }
                else if (insuranceManager.getFullName().equals(searchValue)){
                    return true;
                }
                else if (insuranceManager.getEmail().equals(searchValue)){
                    return true;
                }
                else if (insuranceManager.getAddress().equals(searchValue)){
                    return true;
                }
                else if (insuranceManager.getPhoneNumber().equals(searchValue)){
                    return true;
                }
                else {
                    return false;
                }
            });

        });
        managerId.setCellValueFactory(new PropertyValueFactory<InsuranceManager, String>("id"));
        managerFullName.setCellValueFactory(new PropertyValueFactory<InsuranceManager, String>("fullName"));
        managerAddress.setCellValueFactory(new PropertyValueFactory<InsuranceManager, String>("address"));
        managerEmail.setCellValueFactory(new PropertyValueFactory<InsuranceManager, String>("email"));
        managerPassword.setCellValueFactory(new PropertyValueFactory<InsuranceManager, String>("password"));
        managerUpdateInfoButton.setCellValueFactory(new PropertyValueFactory<InsuranceManager, Button>("updateInfoButton"));
        managerAddSurveyorButton.setCellValueFactory(new PropertyValueFactory<InsuranceManager, Button>("addSurveyorButton"));
        managerTable.getItems().setAll(filteredManagerList);


        /**
         * Filling the surveyors table with data
         * Add method to Buttons
         */
        ObservableList<InsuranceSurveyor> insuranceSurveyorsObservableList = FXCollections.observableArrayList();
        List<InsuranceSurveyor> insuranceSurveyors = EmployeeAnalytics.getAllInsuranceSurveyor(entityManager);
        ListIterator<InsuranceSurveyor> listIteratorInsuranceSurveyor = insuranceSurveyors.listIterator();
        while (listIteratorInsuranceSurveyor.hasNext()){
            InsuranceSurveyor insuranceSurveyor = listIteratorInsuranceSurveyor.next();
            Button buttonUpdateInfo = new Button("Update Info");
            buttonUpdateInfo.setOnAction(event -> {
                CreationPageController_InsuranceSurveyor creationPageControllerInsuranceSurveyor = new CreationPageController_InsuranceSurveyor(entityManager, user, insuranceSurveyor);
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(MainEntryPoint.class.getResource("InsuranceSurveyorCreationPage.fxml"));
                fxmlLoader.setController(creationPageControllerInsuranceSurveyor);
                try {
                    Scene scene = new Scene(fxmlLoader.load());
                    Stage stage = (Stage) buttonUpdateInfo.getScene().getWindow();
                    stage.setScene(scene);
                    stage.show();

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            });
            insuranceSurveyor.setUpdateInfoButton(buttonUpdateInfo);
            Button buttonRemove = new Button("Remove");
            insuranceSurveyor.setRemoveButton(buttonRemove);
            buttonRemove.setOnAction(event -> {
                EmployeeCreateRemove.removeInsuranceSurveyor(entityManager, insuranceSurveyor);
            });
            insuranceSurveyorsObservableList.add(insuranceSurveyor);
        }
        FilteredList<InsuranceSurveyor> filteredSurveyorList = new FilteredList<>(insuranceSurveyorsObservableList, b -> true);
        insuranceSurveyorSearchField.textProperty().addListener((observable, oldValue, newValue)->{
            filteredSurveyorList.setPredicate(insuranceSurveyor -> {
                if (newValue.isEmpty() || newValue == null || newValue.isBlank()){
                    return true;
                }
                String searchValue = newValue.toLowerCase();
                if (insuranceSurveyor.getId().equals(searchValue)){
                    return true;
                }
                else if (insuranceSurveyor.getFullName().equals(searchValue)){
                    return true;
                }
                else if (insuranceSurveyor.getEmail().equals(searchValue)){
                    return true;
                }
                else if (insuranceSurveyor.getAddress().equals(searchValue)){
                    return true;
                }
                else if (insuranceSurveyor.getPhoneNumber().equals(searchValue)){
                    return true;
                }
                else if (insuranceSurveyor.getInsuranceManagerId().equals(searchValue)){
                    return true;
                }
                else if (insuranceSurveyor.getInsuranceManager().getFullName().equals(searchValue)){
                    return true;
                }
                else {
                    return false;
                }
            });

        });
        surveyorId.setCellValueFactory(new PropertyValueFactory<InsuranceSurveyor, String>("id"));
        surveyorFullName.setCellValueFactory(new PropertyValueFactory<InsuranceSurveyor, String>("fullName"));
        surveyorAddress.setCellValueFactory(new PropertyValueFactory<InsuranceSurveyor, String>("address"));
        surveyorEmail.setCellValueFactory(new PropertyValueFactory<InsuranceSurveyor, String>("email"));
        surveyorPassword.setCellValueFactory(new PropertyValueFactory<InsuranceSurveyor, String>("password"));
        manager.setCellValueFactory(new PropertyValueFactory<InsuranceSurveyor, String>("insuranceManagerId"));
        surveyorUpdateInfoButton.setCellValueFactory(new PropertyValueFactory<InsuranceSurveyor, Button>("updateInfoButton"));
        surveyorTable.getItems().setAll(filteredSurveyorList);

        /**
         * Filling the Policy Owners table with data
         * Add Methods to Buttons
         */
        ObservableList<PolicyOwner> policyOwnersObservableList = FXCollections.observableArrayList();
        List<PolicyOwner> policyOwners = CustomerAnalytics.getAllPolicyOwner(entityManager);
        ListIterator<PolicyOwner> policyOwnerListIterator = policyOwners.listIterator();
        while (policyOwnerListIterator.hasNext()){
            PolicyOwner policyOwner = policyOwnerListIterator.next();
            Button buttonUpdateInfo = new Button("Update Info");
            buttonUpdateInfo.setOnAction(event -> {
                CreationPageController_PolicyOwner creationPageControllerPolicyOwner = new CreationPageController_PolicyOwner(entityManager, user, policyOwner);
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(MainEntryPoint.class.getResource("PolicyOwnerCreationPage.fxml"));
                fxmlLoader.setController(creationPageControllerPolicyOwner);
                try {
                    Scene scene = new Scene(fxmlLoader.load());
                    Stage stage = (Stage) buttonUpdateInfo.getScene().getWindow();
                    stage.setScene(scene);
                    stage.show();

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });

            policyOwner.setUpdateInfoButton(buttonUpdateInfo);
            Button buttonAddPolicy = new Button("Add Policy");
            buttonAddPolicy.setOnAction(event -> {
                CreationPageController_PolicyHolder creationPageControllerPolicyHolder = new CreationPageController_PolicyHolder(entityManager, user, policyOwner);
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(MainEntryPoint.class.getResource("PolicyHolderCreationPage.fxml"));
                fxmlLoader.setController(creationPageControllerPolicyHolder);
                try {
                    Scene scene = new Scene(fxmlLoader.load());
                    Stage stage = (Stage) buttonAddPolicy.getScene().getWindow();
                    stage.setScene(scene);
                    stage.show();

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            Button buttonRemove = new Button("Remove");
            policyOwner.setRemoveButton(buttonRemove);
            buttonRemove.setOnAction(event -> {
                CustomerCreateRemove.removePolicyOwner(entityManager, policyOwner );
            });
            policyOwner.setAddPolicyButton(buttonAddPolicy);
            policyOwnersObservableList.add(policyOwner);
        }
        FilteredList<PolicyOwner> filteredPolicyOwnerList = new FilteredList<>(policyOwnersObservableList, b -> true);
        policyOwnerSearchField.textProperty().addListener((observable, oldValue, newValue)->{
            filteredPolicyOwnerList.setPredicate(policyOwner -> {
                if (newValue.isEmpty() || newValue == null || newValue.isBlank()){
                    return true;
                }
                String searchValue = newValue.toLowerCase();
                if (policyOwner.getId().equals(searchValue)){
                    return true;
                }
                else if (policyOwner.getFullName().equals(searchValue)){
                    return true;
                }
                else if (policyOwner.getEmail().equals(searchValue)){
                    return true;
                }
                else if (policyOwner.getAddress().equals(searchValue)){
                    return true;
                }
                else if (policyOwner.getPhoneNumber().equals(searchValue)){
                    return true;
                }

                else {
                    return false;
                }
            });
        });
        policyOwnerId.setCellValueFactory(new PropertyValueFactory<PolicyOwner, String>("id"));
        policyOwnerFullName.setCellValueFactory(new PropertyValueFactory<PolicyOwner, String>("fullName"));
        policyOwnerAddress.setCellValueFactory(new PropertyValueFactory<PolicyOwner, String>("address"));
        policyOwnerEmail.setCellValueFactory(new PropertyValueFactory<PolicyOwner, String>("email"));
        policyOwnerPassword.setCellValueFactory(new PropertyValueFactory<PolicyOwner, String>("password"));
        policyOwnerUpdateInfoButton.setCellValueFactory(new PropertyValueFactory<PolicyOwner, Button>("updateInfoButton"));
        policyOwnerAddPolicyButton.setCellValueFactory(new PropertyValueFactory<PolicyOwner, Button>("addPolicyButton"));
        policyOwnerTable.getItems().setAll(filteredPolicyOwnerList);

        /**
         * Filling the Policy Holder table with data
         * Add event handler to buttons
         */
        ObservableList<PolicyHolder> policyHoldersObservableList = FXCollections.observableArrayList();
        List<PolicyHolder> policyHolders = CustomerAnalytics.getAllPolicyHolder(entityManager);
        ListIterator<PolicyHolder> policyHolderListIterator = policyHolders.listIterator();
        while (policyHolderListIterator.hasNext()){
            PolicyHolder policyHolder = policyHolderListIterator.next();
            Button buttonUpdateInfo = new Button("Update Info");
            buttonUpdateInfo.setOnAction(event -> {
                CreationPageController_PolicyHolder creationPageControllerPolicyHolder = new CreationPageController_PolicyHolder(entityManager, user, policyHolder);
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(MainEntryPoint.class.getResource("PolicyHolderCreationPage.fxml"));
                fxmlLoader.setController(creationPageControllerPolicyHolder);
                try {
                    Scene scene = new Scene(fxmlLoader.load());
                    Stage stage = (Stage) buttonUpdateInfo.getScene().getWindow();
                    stage.setScene(scene);
                    stage.show();

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            buttonUpdateInfo.setUserData(policyHolder);
            policyHolder.setUpdateInfoButton(buttonUpdateInfo);
            Button buttonAddDependant = new Button("Add Dependant");
            buttonAddDependant.setOnAction(event -> {
                CreationPageController_Dependant creationPageControllerDependant = new CreationPageController_Dependant(entityManager, user, policyHolder);
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(MainEntryPoint.class.getResource("DependantCreationPage.fxml"));
                fxmlLoader.setController(creationPageControllerDependant);
                try {
                    Scene scene = new Scene(fxmlLoader.load());
                    Stage stage = (Stage) buttonAddDependant.getScene().getWindow();
                    stage.setScene(scene);
                    stage.show();

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            });
            Button buttonRemove = new Button("Remove");
            policyHolder.setRemoveButton(buttonRemove);
            buttonRemove.setOnAction(event -> {
                CustomerCreateRemove.removePolicyHolder(entityManager, policyHolder );
            });
            policyHolder.setAddDependantButton(buttonAddDependant);
            buttonAddDependant.setUserData(policyHolder);
            policyHoldersObservableList.add(policyHolder);
        }
        FilteredList<PolicyHolder> filteredPolicyHolderList = new FilteredList<>(policyHoldersObservableList, b -> true);
        policyHolderSearchField.textProperty().addListener((observable, oldValue, newValue)->{
            filteredPolicyHolderList.setPredicate(policyHolder -> {
                if (newValue.isEmpty() || newValue == null || newValue.isBlank()){
                    return true;
                }
                String searchValue = newValue.toLowerCase();
                if (policyHolder.getId().equals(searchValue)){
                    return true;
                }
                else if (policyHolder.getFullName().equals(searchValue)){
                    return true;
                }
                else if (policyHolder.getEmail().equals(searchValue)){
                    return true;
                }
                else if (policyHolder.getAddress().equals(searchValue)){
                    return true;
                }
                else if (policyHolder.getPhoneNumber().equals(searchValue)){
                    return true;
                }
                else if (policyHolder.getPolicyOwnerId().equals(searchValue)){
                    return true;
                }
                else if( policyHolder.getPolicyOwner().getFullName().equals(searchValue)) {
                    return true;
                }

                else {
                    return false;
                }
            });
        });

        policyHolderId.setCellValueFactory(new PropertyValueFactory<PolicyHolder, String>("id"));
        policyHolderFullName.setCellValueFactory(new PropertyValueFactory<PolicyHolder, String>("fullName"));
        policyHolderAddress.setCellValueFactory(new PropertyValueFactory<PolicyHolder, String>("address"));
        policyHolderEmail.setCellValueFactory(new PropertyValueFactory<PolicyHolder, String>("email"));
        policyOwnerHolderTable.setCellValueFactory(new PropertyValueFactory<PolicyHolder, String>("policyOwnerId"));
        cardNumberHolderTable.setCellValueFactory(new PropertyValueFactory<PolicyHolder, String>("cardNumber"));
        policyHolderPassword.setCellValueFactory(new PropertyValueFactory<PolicyHolder, String>("password"));
        policyHolderUpdateInfoButton.setCellValueFactory(new PropertyValueFactory<PolicyHolder, Button>("updateInfoButton"));
        policyHolderAddDependantButton.setCellValueFactory(new PropertyValueFactory<PolicyHolder, Button>("addDependantButton"));
        policyHolderTable.getItems().setAll(filteredPolicyHolderList);


        /**
         * Fill Dependant table with data
         * Add methods to buttons
         */

        ObservableList<Dependant> dependantObservableList = FXCollections.observableArrayList();
        List<Dependant> dependants = CustomerAnalytics.getAllDependant(entityManager);
        ListIterator<Dependant> dependantListIterator = dependants.listIterator();
        while (dependantListIterator.hasNext()){
            Dependant dependant = dependantListIterator.next();
            Button buttonUpdateInfo = new Button("Update Info");
            buttonUpdateInfo.setOnAction(event -> {
                CreationPageController_Dependant creationPageControllerDependant = new CreationPageController_Dependant(entityManager, user, dependant);
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(MainEntryPoint.class.getResource("DependantCreationPage.fxml"));
                fxmlLoader.setController(creationPageControllerDependant);
                try {
                    Scene scene = new Scene(fxmlLoader.load());
                    Stage stage = (Stage) buttonUpdateInfo.getScene().getWindow();
                    stage.setScene(scene);
                    stage.show();

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            Button buttonRemove = new Button("Remove");
            dependant.setRemoveButton(buttonRemove);
            buttonRemove.setOnAction(event -> {
                CustomerCreateRemove.removeDependant(entityManager, dependant );
            });
            buttonUpdateInfo.setUserData(dependant);
            dependant.setUpdateInfoButton(buttonUpdateInfo);
            dependantObservableList.add(dependant);
        }
        FilteredList<Dependant> filteredDependantList = new FilteredList<>(dependantObservableList, b -> true);
        dependantSearchField.textProperty().addListener((observable, oldValue, newValue)->{
            filteredDependantList.setPredicate(dependant -> {
                if (newValue.isEmpty() || newValue == null || newValue.isBlank()){
                    return true;
                }
                String searchValue = newValue.toLowerCase();
                if (dependant.getId().equals(searchValue)){
                    return true;
                }
                else if (dependant.getFullName().equals(searchValue)){
                    return true;
                }
                else if (dependant.getAddress().equals(searchValue)){
                    return true;
                }
                else if (dependant.getEmail().equals(searchValue)){
                    return true;
                }
                else if (dependant.getPhoneNumber().equals(searchValue)){
                    return true;
                }
                else if (dependant.getPolicyOwnerId().equals(searchValue)){
                    return true;
                }
                else if(dependant.getPolicyOwner().getFullName().equals(searchValue)) {
                    return true;
                }
                else if (dependant.getPolicyHolderId().equals(searchValue)){
                    return true;
                }
                else if(dependant.getPolicyHolder().getFullName().equals(searchValue)) {
                    return true;
                }
                else {
                    return false;
                }
            });
        });
        dependantId.setCellValueFactory(new PropertyValueFactory<Dependant, String>("id"));
        dependantFullName.setCellValueFactory(new PropertyValueFactory<Dependant, String>("fullName"));
        dependantAddress.setCellValueFactory(new PropertyValueFactory<Dependant, String>("address"));
        dependantEmail.setCellValueFactory(new PropertyValueFactory<Dependant, String>("email"));
        dependantPassword.setCellValueFactory(new PropertyValueFactory<Dependant, String>("password"));
        policyOwnerDependantTable.setCellValueFactory(new PropertyValueFactory<Dependant, String>("policyOwnerId"));
        cardNumberDependantTable.setCellValueFactory(new PropertyValueFactory<Dependant, String>("cardNumber"));
        policyHolderDependantTable.setCellValueFactory(new PropertyValueFactory<Dependant, String>("policyHolderId"));
        dependantUpdateInfoButton.setCellValueFactory(new PropertyValueFactory<Dependant, Button>("updateInfoButton"));
        dependantTable.getItems().setAll(dependantObservableList);

        ObservableList<InsuranceCard> insuranceCardObservableList = FXCollections.observableArrayList();
        List<InsuranceCard> insuranceCards = CustomerAnalytics.getAllInsuranceCard(entityManager);
        ListIterator<InsuranceCard> insuranceCardListIterator = insuranceCards.listIterator();
        while (insuranceCardListIterator.hasNext()){
            InsuranceCard insuranceCard = new InsuranceCard();
            Button buttonRemove = new Button("Remove");
            buttonRemove.setOnAction(event -> {

            });
            insuranceCard.setRemoveButton(buttonRemove);
            insuranceCardObservableList.add(insuranceCard);
        }
        FilteredList<InsuranceCard> filteredInsuranceCardList = new FilteredList<>(insuranceCardObservableList, b -> true);
        insuranceCardSearchField.textProperty().addListener((observable, oldValue, newValue) ->{
            filteredInsuranceCardList.setPredicate(insuranceCard -> {
                if (newValue.isBlank() || newValue.isEmpty() || newValue == null){
                    return true;
                }
                String searchValue = newValue.toLowerCase();
                if (insuranceCard.getCardNumber().equals(searchValue)){
                    return true;
                }
                else if (insuranceCard.getCardHolderId().equals(searchValue)){
                    return true;
                }
                else if (insuranceCard.getPolicyOwnerId().equals(searchValue)){
                    return true;
                }
                else if (insuranceCard.getCardHolder().getFullName().equals(searchValue)){
                    return true;
                }
                else if (insuranceCard.getPolicyOwner().getFullName().equals(searchValue)){
                    return true;
                }
                else {
                    return false;
                }
            });
        });


        cardNumber.setCellValueFactory(new PropertyValueFactory<InsuranceCard, String>("cardNumber"));
        cardHolderId.setCellValueFactory(new PropertyValueFactory<InsuranceCard, String>("cardHolder"));
        policyOwnerInsuranceCardTable.setCellValueFactory(new PropertyValueFactory<InsuranceCard, String>("policyOwner"));
        expiryDate.setCellValueFactory(new PropertyValueFactory<InsuranceCard, Date>("expirationDate"));
        insuranceCardRemoveButton.setCellValueFactory(new PropertyValueFactory<InsuranceCard, Button>("removeButton"));
        insuranceCardTable.getItems().addAll(filteredInsuranceCardList);

        ObservableList<Claim> claimObservableList = FXCollections.observableArrayList();
        List<Claim> claims = ClaimAnalytics.getAllClaims(entityManager);
        ListIterator<Claim> claimListIterator = claims.listIterator();
        while (claimListIterator.hasNext()){
            Claim claim = claimListIterator.next();
            Button viewClaimButton = new Button("View Claim");
            viewClaimButton.setOnAction(event -> {
               CreationPageController_Claim creationPageControllerClaim = new CreationPageController_Claim(entityManager, user, claim);
            });
            claim.setClaimButton(viewClaimButton);
            claimObservableList.add(claim);

        }

        FilteredList<Claim> filteredClaimList = new FilteredList<>(claimObservableList, b -> true);
        claimListSearchField.textProperty().addListener((observable, oldValue, newValue)->{
            filteredClaimList.setPredicate(claim -> {
                String searchValue = newValue.toLowerCase();
                if (newValue.isEmpty() || newValue.isBlank() || newValue == null){
                    return true;
                }
                else if (claim.getClaimId().equals(searchValue)){
                    return true;
                }
                else if (claim.getInsuredPersonId().equals(searchValue)){
                    return true;
                } else if (claim.getCardNumber().equals(searchValue)){
                    return true;
                } else if (claim.getPolicyOwnerId().equals(searchValue)){
                    return true;
                } else if (claim.getStatus().equals(searchValue)){
                    return true;
                } else{
                    return false;
                }




            });
        });

        creationDateFrom.valueProperty().addListener((observable, oldDate, newDate)->{
            filteredClaimList.setPredicate(claim -> {
                if (newDate == null){
                    return true;
                }
                else if (!claim.getCreationDate().toLocalDate().isBefore(newDate)){
                    return true;
                }
                else {
                    return false;
                }
            });
        });

        creationDateTo.valueProperty().addListener((observable, oldDate, newDate)->{
            filteredClaimList.setPredicate(claim -> {
                if (newDate == null){
                    return true;
                }
                else if (!claim.getCreationDate().toLocalDate().isAfter(newDate)){
                    return true;
                }
                else {
                    return false;
                }
            });
        });

        settlementDateFrom.valueProperty().addListener((observable, oldDate, newDate)->{
            filteredClaimList.setPredicate(claim -> {
                if (newDate == null){
                    return true;
                }
                else if (!claim.getSettlementDate().toLocalDate().isBefore(newDate)){
                    return true;
                }
                else {
                    return false;
                }
            });
        });


        settlementDateTo.valueProperty().addListener((observable, oldDate, newDate)->{
            filteredClaimList.setPredicate(claim -> {
                if (newDate == null){
                    return true;
                }
                else if (!claim.getSettlementDate().toLocalDate().isAfter(newDate)){
                    return true;
                }
                else {
                    return false;
                }
            });
        });

        claimAmountFrom.textProperty().addListener((observable, oldValue, newValue)->{
            filteredClaimList.setPredicate(claim -> {
                if (newValue == null || newValue.isBlank() || newValue.isEmpty()){
                    return true;
                }
                else {
                    try{
                        if (Float.parseFloat(newValue) <= claim.getClaimAmount()){
                            return true;
                        }
                        else {
                            return false;
                        }
                    } catch (NumberFormatException e){
                        return false;
                    }
                }

            });
        });

        claimAmountTo.textProperty().addListener((observable, oldValue, newValue)->{
            filteredClaimList.setPredicate(claim -> {
                if (newValue == null || newValue.isBlank() || newValue.isEmpty()){
                    return true;
                }
                else {
                    try{
                        if (Float.parseFloat(newValue) >= claim.getClaimAmount()){
                            return true;
                        }
                        else {
                            return false;
                        }
                    } catch (NumberFormatException e){
                        return false;
                    }
                }

            });
        });

        statusList.valueProperty().addListener((observable, oldVal, newVal)->{
            filteredClaimList.setPredicate(claim -> {
                if (newVal == null){
                    return true;
                }
                else if (claim.getStatus().equals(newVal)){
                    return true;
                }
                else {
                    return false;
                }

            });
        });

        class ClaimCreationDateComparator implements Comparator<Claim>{
            @Override
            public int compare(Claim firstClaim, Claim secondClaim) {
               long firstClaimTime = firstClaim.getCreationDate().getTime();
               long secondClaimTime = secondClaim.getCreationDate().getTime();
               return Long.compare(firstClaimTime, secondClaimTime);
            }

        }

        class ClaimSettlementDateComparator implements Comparator<Claim>{

            @Override
            public int compare(Claim firstClaim, Claim secondClaim) {
                long firstClaimTime = firstClaim.getSettlementDate().getTime();
                long secondClaimTime = secondClaim.getSettlementDate().getTime();
                return Long.compare(firstClaimTime, secondClaimTime);
            }

        }

        class ClaimAmountComparator implements Comparator<Claim>{

            @Override
            public int compare(Claim firstClaim, Claim secondClaim) {
                return Float.compare(firstClaim.getClaimAmount(), secondClaim.getClaimAmount());
            }
        }

        SortedList<Claim> sortedClaimList = new SortedList<>(filteredClaimList);
        sortList.valueProperty().addListener((observable, oldVal, newVal)->{
            if (newVal.equals("Sort By Creation Date In Ascending Order")){
                ClaimCreationDateComparator claimCreationDateComparator = new ClaimCreationDateComparator();
                sortedClaimList.sort(claimCreationDateComparator);
            }
            else if (newVal.equals("Sort By Creation Date In Ascending Order")){
                ClaimCreationDateComparator claimCreationDateComparator = new ClaimCreationDateComparator();
                sortedClaimList.sort(claimCreationDateComparator);
                sortedClaimList.reversed();
            }
            else if (newVal.equals("Sort By Settlement Date In Ascending Order")){
                ClaimSettlementDateComparator claimSettlementDateComparator = new ClaimSettlementDateComparator();
                sortedClaimList.sort(claimSettlementDateComparator);
            }
            else if (newVal.equals("Sort By Settlement Date In Descending Order")){
                ClaimSettlementDateComparator claimSettlementDateComparator = new ClaimSettlementDateComparator();
                sortedClaimList.sort(claimSettlementDateComparator);
                sortedClaimList.reversed();
            }

            else if (newVal.equals("Sort by Claim Amount In Ascending Order")){
                ClaimAmountComparator claimAmountComparator = new ClaimAmountComparator();
                sortedClaimList.sort(claimAmountComparator);
            }
            else if (newVal.equals("Sort by Claim Amount In Descending Order")){
                ClaimAmountComparator claimAmountComparator = new ClaimAmountComparator();
                sortedClaimList.sort(claimAmountComparator);
                sortedClaimList.reversed();
            }

        });
        claimId.setCellValueFactory(new PropertyValueFactory<Claim, String>("claimId"));
        creationDate.setCellValueFactory(new PropertyValueFactory<Claim, Date>("creationDate"));
        insuredPersonId.setCellValueFactory(new PropertyValueFactory<Claim, String>("insuredPersonId"));
        cardNumberClaimTable.setCellValueFactory(new PropertyValueFactory<Claim, String>("cardNumber"));
        policyOwnerClaimTable.setCellValueFactory(new PropertyValueFactory<Claim, String>("policyOwnerId"));
        claimAmount.setCellValueFactory(new PropertyValueFactory<Claim, Float>("claimAmount"));
        settlementDate.setCellValueFactory(new PropertyValueFactory<Claim, Date>("settlementDate"));
        status.setCellValueFactory(new PropertyValueFactory<Claim, String>("status"));
        claimButton.setCellValueFactory(new PropertyValueFactory<Claim, Button>("claimButton"));
        claimTable.getItems().setAll(sortedClaimList);
    }



    public DashBoardController_SystemAdmin(EntityManager entityManager, SystemAdmin systemAdmin) {
        this.entityManager = entityManager;
        this.user = systemAdmin;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }






}
