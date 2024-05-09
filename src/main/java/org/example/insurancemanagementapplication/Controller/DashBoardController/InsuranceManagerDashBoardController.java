package org.example.insurancemanagementapplication.Controller.DashBoardController;

import Entity.InsuranceManager;
import jakarta.persistence.EntityManager;
import javafx.fxml.Initializable;
import org.example.insurancemanagementapplication.Controller.DashBoardController.TableFillingController.InsuranceSurveyorTableFilling;
import org.example.insurancemanagementapplication.Interfaces.ClaimRead;
import org.example.insurancemanagementapplication.Interfaces.Controller;
import org.example.insurancemanagementapplication.Interfaces.CustomerRead;
import org.example.insurancemanagementapplication.Interfaces.EmployeeRead;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Luong Thanh Trung
 * @version ${}
 * @created 27/04/2024 04:54
 * @project InsuranceManagementTeamProject
 */
public class InsuranceManagerDashBoardController extends InsuranceSurveyorTableFilling implements Initializable, Controller {


    public InsuranceManagerDashBoardController(InsuranceManager user, EntityManager entityManager) {
        super(entityManager, user);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //see the ClaimTableFilling Class
        userFillingData();
        //fill claim table
        fillingClaimTable(entityManager, user, ClaimRead.getAllClaimsProcessByAnInsuranceManager(entityManager, user.getId()));

        //fill insurance surveyor table
        fillingInsuranceSurveyorTable(entityManager, user, EmployeeRead.getAllInsuranceSurveyorOfAnInsuranceManager(entityManager, user.getId()));
        //fill all the table of customers
        fillingDependantTable(entityManager, user, CustomerRead.getAllDependantsTakeChargeByAnInsuranceManager(entityManager, user.getId()));
        fillingPolicyHolderTable(entityManager, user, CustomerRead.getAllPolicyHoldersTakeChargeByAnEmployee(entityManager, user.getId()));
        fillingPolicyOwnerTable(entityManager, user, CustomerRead.getAllPolicyOwnersTakeChargeByAnEmployee(entityManager, user.getId()));

    }

}
