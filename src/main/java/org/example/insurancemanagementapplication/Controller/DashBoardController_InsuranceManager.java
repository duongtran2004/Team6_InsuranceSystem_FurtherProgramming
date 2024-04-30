package org.example.insurancemanagementapplication.Controller;

import Entity.*;
import jakarta.persistence.EntityManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.sql.Date;

/**
 * @author Luong Thanh Trung
 * @version ${}
 * @created 27/04/2024 04:54
 * @project InsuranceManagementTeamProject
 */
public class DashBoardController_InsuranceManager {
    private final EntityManager entityManager;
    private final InsuranceManager insuranceManager;
    private InsuranceSurveyor insuranceSurveyor;
    private Customer customer;
    private Claim claim;


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

    public DashBoardController_InsuranceManager(InsuranceManager insuranceManager, EntityManager entityManager) {
        this.insuranceManager = insuranceManager;
        this.entityManager = entityManager;
    }

    public InsuranceManager getInsuranceManager() {
        return insuranceManager;
    }

    public InsuranceSurveyor getInsuranceSurveyor() {
        return insuranceSurveyor;
    }

    public void setInsuranceSurveyor(InsuranceSurveyor insuranceSurveyor) {
        this.insuranceSurveyor = insuranceSurveyor;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Claim getClaim() {
        return claim;
    }

    public void setClaim(Claim claim) {
        this.claim = claim;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }
}
