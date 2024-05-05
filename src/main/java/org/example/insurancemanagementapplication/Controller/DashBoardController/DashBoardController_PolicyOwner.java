package org.example.insurancemanagementapplication.Controller.DashBoardController;

import Entity.*;
import jakarta.persistence.EntityManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import org.example.insurancemanagementapplication.Controller.DashBoardController.TableFillingController.PolicyHolderTableFilling;
import org.example.insurancemanagementapplication.Interfaces.Controller;
import org.example.insurancemanagementapplication.Interfaces.CustomerCreateRemove;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Luong Thanh Trung
 * @version ${}
 * @created 27/04/2024 04:54
 * @project InsuranceManagementTeamProject
 */
public class DashBoardController_PolicyOwner extends PolicyHolderTableFilling implements CustomerCreateRemove, Initializable, Controller {

    @FXML
    private Button addPolicyButton;
    private ObservableList<Claim> claimObservableList = FXCollections.observableArrayList();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //see the DashBoardController Class
        userFillingData();
        //TODO Set action for the add policy button. This button will create a Policy Holder CreationPage controller by passing in the policy owner object into the constructor.
        //TODO Fill the claim table with claims by calling the claim table filling method from the DashBoardControllerClass. Put this method call in a thread
        //TODO Fill the dependant table with dependants by calling the dependant table filling method from the DependantTableFilling class. Put this method call in a thread
        //TODO Fill the policy holder table with policy holders by calling the policy holder table filling method from the PolicyHolderTableFilling class. Put this method call in a thread

    }
    public DashBoardController_PolicyOwner(PolicyOwner user, EntityManager entityManager) {
        super(entityManager, user);
    }




}
