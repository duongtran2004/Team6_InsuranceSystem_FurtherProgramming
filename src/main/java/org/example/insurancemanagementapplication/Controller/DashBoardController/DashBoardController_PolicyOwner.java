package org.example.insurancemanagementapplication.Controller.DashBoardController;

import Entity.PolicyOwner;
import jakarta.persistence.EntityManager;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userFillingData();
    }
    public DashBoardController_PolicyOwner(PolicyOwner user, EntityManager entityManager) {
        super(entityManager, user);
    }




}
