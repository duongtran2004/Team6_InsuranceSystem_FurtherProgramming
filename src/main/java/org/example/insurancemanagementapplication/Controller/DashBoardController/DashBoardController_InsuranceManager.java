package org.example.insurancemanagementapplication.Controller.DashBoardController;

import Entity.InsuranceManager;
import jakarta.persistence.EntityManager;
import javafx.fxml.Initializable;
import org.example.insurancemanagementapplication.Controller.DashBoardController.TableFillingController.InsuranceSurveyorTableFilling;
import org.example.insurancemanagementapplication.Interfaces.Controller;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Luong Thanh Trung
 * @version ${}
 * @created 27/04/2024 04:54
 * @project InsuranceManagementTeamProject
 */
public class DashBoardController_InsuranceManager extends InsuranceSurveyorTableFilling implements Initializable, Controller {

    public DashBoardController_InsuranceManager(InsuranceManager user, EntityManager entityManager) {
        super(entityManager, user);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userFillingData();
    }

}
