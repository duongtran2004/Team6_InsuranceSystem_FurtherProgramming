package org.example.insurancemanagementapplication.Controller;

import Entity.*;
import jakarta.persistence.EntityManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.example.insurancemanagementapplication.HelloApplication;
import org.example.insurancemanagementapplication.Interfaces.CustomerAnalytics;
import org.example.insurancemanagementapplication.Interfaces.CustomerCreateRemove;
import org.example.insurancemanagementapplication.Interfaces.EmployeeAnalytics;
import org.example.insurancemanagementapplication.Interfaces.EmployeeCreateRemove;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.List;
import java.util.ListIterator;
import java.util.ResourceBundle;

/**
 * @author Luong Thanh Trung
 * @version ${}
 * @created 27/04/2024 04:55
 * @project InsuranceManagementTeamProject
 */
public class SystemAdminDashBoardController implements EmployeeCreateRemove, CustomerCreateRemove, Initializable, EmployeeAnalytics {
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
    @FXML
    private TableColumn<Dependant, String> policyHolderDependantTable;

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
        ObservableList<InsuranceManager> insuranceManagersObservableList = FXCollections.observableArrayList();
        List<InsuranceManager> insuranceManagers = EmployeeAnalytics.getAllInsuranceManager(entityManager);
        ListIterator<InsuranceManager> listIteratorInsuranceManager = insuranceManagers.listIterator();
        while (listIteratorInsuranceManager.hasNext()){
            InsuranceManager insuranceManager = listIteratorInsuranceManager.next();
            Button buttonUpdateInfo = new Button("Update Info");
            insuranceManager.setUpdateInfoButton(buttonUpdateInfo);
            buttonUpdateInfo.setUserData(insuranceManager);
            buttonUpdateInfo.setOnAction(event -> {
                InsuranceManagerCreationPageController insuranceManagerCreationPageController = new InsuranceManagerCreationPageController(entityManager, user, (InsuranceManager) buttonUpdateInfo.getUserData());
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(HelloApplication.class.getResource("InsuranceManagerCreationPage.fxml"));
                fxmlLoader.setController(insuranceManagerCreationPageController);
                try {
                    Scene scene = new Scene(fxmlLoader.load());
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    stage.show();

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            Button buttonAddSurveyor = new Button("Add Surveyor");
            insuranceManager.setAddSurveyorButton(buttonAddSurveyor);
            buttonAddSurveyor.setUserData(insuranceManager);
            insuranceManagersObservableList.add(insuranceManager);

            managerId.setCellValueFactory(new PropertyValueFactory<InsuranceManager, String>("id"));
            managerFullName.setCellValueFactory(new PropertyValueFactory<InsuranceManager, String>("fullName"));
            managerAddress.setCellValueFactory(new PropertyValueFactory<InsuranceManager, String>("address"));
            managerEmail.setCellValueFactory(new PropertyValueFactory<InsuranceManager, String>("email"));
            managerPassword.setCellValueFactory(new PropertyValueFactory<InsuranceManager, String>("password"));
            managerUpdateInfoButton.setCellValueFactory(new PropertyValueFactory<InsuranceManager, Button>("updateInfoButton"));
            managerAddSurveyorButton.setCellValueFactory(new PropertyValueFactory<InsuranceManager, Button>("addSurveyorButton"));
            managerTable.getItems().setAll(insuranceManagersObservableList);

        }





        ObservableList<InsuranceSurveyor> insuranceSurveyorsObservableList = FXCollections.observableArrayList();
        List<InsuranceSurveyor> insuranceSurveyors = EmployeeAnalytics.getAllInsuranceSurveyor(entityManager);
        ListIterator<InsuranceSurveyor> listIteratorInsuranceSurveyor = insuranceSurveyors.listIterator();
        while (listIteratorInsuranceSurveyor.hasNext()){
            InsuranceSurveyor insuranceSurveyor = listIteratorInsuranceSurveyor.next();
            Button buttonUpdateInfo = new Button("Update Info");
            insuranceSurveyor.setUpdateInfoButton(buttonUpdateInfo);
            buttonUpdateInfo.setUserData(insuranceSurveyor);
            insuranceSurveyorsObservableList.add(insuranceSurveyor);
        }


        ObservableList<PolicyOwner> policyOwnersObservableList = FXCollections.observableArrayList();
        List<PolicyOwner> policyOwners = CustomerAnalytics.getAllPolicyOwner(entityManager);
        ListIterator<PolicyOwner> policyOwnerListIterator = policyOwners.listIterator();
        while (policyOwnerListIterator.hasNext()){
            PolicyOwner policyOwner = policyOwnerListIterator.next();
            Button buttonUpdateInfo = new Button("Update Info");
            buttonUpdateInfo.setUserData(policyOwner);
            policyOwner.setUpdateInfoButton(buttonUpdateInfo);
            Button buttonAddPolicy = new Button("Add Policy");
            policyOwner.setAddPolicyButton(buttonAddPolicy);
            buttonAddPolicy.setUserData(policyOwner);
            policyOwnersObservableList.add(policyOwner);
        }

        ObservableList<PolicyHolder> policyHoldersObservableList = FXCollections.observableArrayList();
        List<PolicyHolder> policyHolders = CustomerAnalytics.getAllPolicyHolder(entityManager);
        ListIterator<PolicyHolder> policyHolderListIterator = policyHolders.listIterator();
        while (policyHolderListIterator.hasNext()){
            PolicyHolder policyHolder = policyHolderListIterator.next();
            Button buttonUpdateInfo = new Button("Update Info");
            buttonUpdateInfo.setUserData(policyHolder);
            policyHolder.setUpdateInfoButton(buttonUpdateInfo);
            Button buttonAddDependant = new Button("Add Dependant");
            policyHolder.setAddDependantButton(buttonAddDependant);
            buttonAddDependant.setUserData(policyHolder);
            policyHoldersObservableList.add(policyHolder);
        }



       ObservableList<Dependant> dependantObservableList = FXCollections.observableArrayList();
        List<Dependant> dependants = CustomerAnalytics.getAllDependant(entityManager);
        ListIterator<Dependant> dependantListIterator = dependants.listIterator();
        while (dependantListIterator.hasNext()){
            Dependant dependant = dependantListIterator.next();
            Button buttonUpdateInfo = new Button("Update Info");
            buttonUpdateInfo.setUserData(dependant);
            dependant.setUpdateInfoButton(buttonUpdateInfo);
            dependantObservableList.add(dependant);
        }

    }



    public SystemAdminDashBoardController(EntityManager entityManager, SystemAdmin systemAdmin) {
        this.entityManager = entityManager;
        this.user = systemAdmin;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }






}
