package org.example.insurancemanagementapplication.Controller.DashBoardController;

import Entity.*;
import jakarta.persistence.EntityManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import org.example.insurancemanagementapplication.Controller.DashBoardController.TableFillingController.PolicyHolderTableFilling;
import org.example.insurancemanagementapplication.Interfaces.Controller;
import org.example.insurancemanagementapplication.Interfaces.CustomerCreateRemove;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Luong Thanh Trung
 * @version ${}
 * @created 27/04/2024 04:54
 * @project InsuranceManagementTeamProject
 */
public class DashBoardController_PolicyOwner extends PolicyHolderTableFilling implements CustomerCreateRemove, Initializable, Controller {

    private ObservableList<Claim> claimObservableList = FXCollections.observableArrayList();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userFillingData();
    }
    public DashBoardController_PolicyOwner(PolicyOwner user, EntityManager entityManager) {
        super(entityManager, user);
    }




}
