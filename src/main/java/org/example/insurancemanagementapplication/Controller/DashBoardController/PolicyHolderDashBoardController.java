package org.example.insurancemanagementapplication.Controller.DashBoardController;

import Entity.PolicyHolder;
import jakarta.persistence.EntityManager;
import javafx.fxml.Initializable;
import org.example.insurancemanagementapplication.Controller.DashBoardController.TableFillingController.DependantTableFilling;
import org.example.insurancemanagementapplication.Interfaces.ClaimRead;
import org.example.insurancemanagementapplication.Interfaces.Controller;
import org.example.insurancemanagementapplication.Interfaces.CustomerRead;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Luong Thanh Trung
 * @version ${}
 * @created 27/04/2024 04:54
 * @project InsuranceManagementTeamProject
 */
public class PolicyHolderDashBoardController extends DependantTableFilling implements Initializable, Controller {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //See the ClaimTableFilling class
        userFillingData();


        //fill claim table
        fillingClaimTable(entityManager, user, ClaimRead.getAllClaimsFromABeneficiary(entityManager,user.getId()));


        //fill dependent table
        fillingDependantTable(entityManager,user, CustomerRead.getAllDependantsOfAPolicyHolder(entityManager, user.getId()));
    }

    public PolicyHolderDashBoardController(PolicyHolder user, EntityManager entityManager) {
        super(entityManager, user);
    }


}
