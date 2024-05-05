package org.example.insurancemanagementapplication.Controller.DashBoardController;

import Entity.Dependant;
import jakarta.persistence.EntityManager;
import javafx.fxml.Initializable;
import org.example.insurancemanagementapplication.Controller.DashBoardController.TableFillingController.DashBoardController;
import org.example.insurancemanagementapplication.Interfaces.Controller;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Luong Thanh Trung
 * @version ${}
 * @created 27/04/2024 04:53
 * @project InsuranceManagementTeamProject
 */
public class DashBoardController_Dependant extends DashBoardController implements Initializable, Controller {



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userFillingData();

    }

    public DashBoardController_Dependant(Dependant user, EntityManager entityManager) {
        super(entityManager, user);
    }


}
