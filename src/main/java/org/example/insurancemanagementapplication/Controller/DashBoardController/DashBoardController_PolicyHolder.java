package org.example.insurancemanagementapplication.Controller.DashBoardController;

import Entity.PolicyHolder;
import jakarta.persistence.EntityManager;
import javafx.fxml.Initializable;
import org.example.insurancemanagementapplication.Controller.DashBoardController.TableFillingController.DependantTableFilling;
import org.example.insurancemanagementapplication.Interfaces.Controller;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Luong Thanh Trung
 * @version ${}
 * @created 27/04/2024 04:54
 * @project InsuranceManagementTeamProject
 */
public class DashBoardController_PolicyHolder extends DependantTableFilling implements Initializable, Controller {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userFillingData();
    }

    public DashBoardController_PolicyHolder(PolicyHolder user, EntityManager entityManager) {
        super(entityManager, user);
    }


}
