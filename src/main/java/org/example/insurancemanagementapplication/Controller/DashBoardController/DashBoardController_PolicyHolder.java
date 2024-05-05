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
        //See the DashBoardController class
        userFillingData();
        //TODO Fill the claim table with claims by calling the claim table filling method from the DashBoardControllerClass. Put this method call in a thread
        //TODO Fill the dependant table with dependants by calling the dependant table filling method from the DependantTableFilling class. Put this method call in a thread
    }

    public DashBoardController_PolicyHolder(PolicyHolder user, EntityManager entityManager) {
        super(entityManager, user);
    }


}
