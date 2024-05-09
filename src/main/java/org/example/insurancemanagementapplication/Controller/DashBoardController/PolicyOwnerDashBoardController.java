package org.example.insurancemanagementapplication.Controller.DashBoardController;

import Entity.Claim;
import Entity.PolicyOwner;
import jakarta.persistence.EntityManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import org.example.insurancemanagementapplication.Controller.DashBoardController.TableFillingController.PolicyHolderTableFilling;
import org.example.insurancemanagementapplication.Interfaces.*;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Luong Thanh Trung
 * @version ${}
 * @created 27/04/2024 04:54
 * @project InsuranceManagementTeamProject
 */
public class PolicyOwnerDashBoardController extends PolicyHolderTableFilling implements CustomerCreateRemove, Initializable, Controller {

    @FXML
    private javafx.scene.control.Button addPolicyButton;
    private ObservableList<Claim> claimObservableList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //see the ClaimTableFilling Class
        userFillingData();
        //TODO Set action for the add policy holder  button. This button will create a Policy Holder CreationPage controller by passing in the policy owner object into the constructor.
//create policyHolderCreationPageController Class => switch stage (as a controller for Pol


//still miss to fix buttons
        fillingClaimTable(entityManager, user, ClaimRead.getAllClaimsFromBeneficiariesOfAPolicyOwner(entityManager, user.getId()));
        fillingDependantTable(entityManager, user, CustomerRead.getAllDependantsOfAPolicyOwner(entityManager, user.getId()));
        fillingPolicyHolderTable(entityManager, user, CustomerRead.getAllPolicyHoldersOfAPolicyOwner(entityManager, user.getId()));
        fillingInsuranceCardTable(entityManager,user, InsuranceCardRead.getAllInsuranceCardsOfPolicyOwner(entityManager, user.getId());
    }

    public PolicyOwnerDashBoardController(PolicyOwner user, EntityManager entityManager) {
        super(entityManager, user);
    }


}
