package org.example.insurancemanagementapplication.Controller;

import Entity.*;
import jakarta.persistence.EntityManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.example.insurancemanagementapplication.Interfaces.CustomerCreateRemove;

import java.sql.Date;

/**
 * @author Luong Thanh Trung
 * @version ${}
 * @created 27/04/2024 04:54
 * @project InsuranceManagementTeamProject
 */
public class PolicyOwnerController implements CustomerCreateRemove {
    private final EntityManager entityManager;
    private final PolicyOwner policyOwner;
    private Beneficiaries beneficiary;
    private Claim claim;

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

    //Insurance Card Table
    @FXML
    private TableColumn<InsuranceCard, String> cardNumber;
    @FXML
    private TableColumn<InsuranceCard, Date> expiryDate;
    @FXML
    private TableColumn<InsuranceCard, String> cardHolder;
    @FXML
    private TableColumn<InsuranceCard, String> policyOwnerInsuranceCardTable;

    public PolicyOwnerController(PolicyOwner policyOwner, EntityManager entityManager) {
        this.policyOwner = policyOwner;
        this.entityManager = entityManager;
    }

    public PolicyOwner getPolicyOwner() {
        return policyOwner;
    }

    public Beneficiaries getBeneficiary() {
        return beneficiary;
    }

    public void setBeneficiary(Beneficiaries beneficiary) {
        this.beneficiary = beneficiary;

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
