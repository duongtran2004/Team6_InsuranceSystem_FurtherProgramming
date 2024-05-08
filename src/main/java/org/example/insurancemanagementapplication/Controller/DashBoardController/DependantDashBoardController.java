package org.example.insurancemanagementapplication.Controller.DashBoardController;

import Entity.Dependant;
import jakarta.persistence.EntityManager;
import javafx.fxml.Initializable;
import org.example.insurancemanagementapplication.Controller.DashBoardController.TableFillingController.ClaimTableFilling;
import org.example.insurancemanagementapplication.Interfaces.ClaimRead;
import org.example.insurancemanagementapplication.Interfaces.Controller;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Luong Thanh Trung
 * @version ${}
 * @created 27/04/2024 04:53
 * @project InsuranceManagementTeamProject
 */
public class DependantDashBoardController extends ClaimTableFilling implements Initializable, Controller {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //See the ClaimTableFilling class
        //aka fill the top form's default input as user's info
        userFillingData();
        //Put this method call in a thread

        //Fill claim table
        fillingClaimTable(entityManager, user, ClaimRead.getAllClaimsFromABeneficiary(entityManager,user.getId()));
        //only get the claim related to that user

    }


    //constructor
    public DependantDashBoardController(Dependant user, EntityManager entityManager) {
        super(entityManager, user);
    }


}
