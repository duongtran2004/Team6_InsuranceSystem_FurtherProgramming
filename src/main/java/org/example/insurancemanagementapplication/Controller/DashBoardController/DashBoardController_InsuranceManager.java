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
        //see the DashBoardController Class
        userFillingData();
        //TODO Fill the claim table with claims by calling the claim table filling method from the DashBoardControllerClass. Put this method call in a thread
        //TODO Fill the dependant table with dependants by calling the dependant table filling method from the DependantTableFilling class. Put this method call in a thread
        //TODO Fill the policy holder table with policy holders by calling the policy holder table filling method from the PolicyHolderTableFilling class. Put this method call in a thread
        //TODO Fill the policy owner table with policy owners by calling the policy owner table filling method from the PolicyOwnerTableFilling class. Put this method call in a thread
        //TODO Fill the insurance surveyor table with insurance surveyors by calling the insurance surveyor table filling method from the InsuranceSurveyorTableFilling class. Put this method call in a thread
    }

}
