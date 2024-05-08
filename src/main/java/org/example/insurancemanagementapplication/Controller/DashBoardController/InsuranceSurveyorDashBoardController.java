package org.example.insurancemanagementapplication.Controller.DashBoardController;

import Entity.InsuranceSurveyor;
import jakarta.persistence.EntityManager;
import javafx.fxml.Initializable;
import org.example.insurancemanagementapplication.Controller.DashBoardController.TableFillingController.PolicyOwnerTableFilling;
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
public class InsuranceSurveyorDashBoardController extends PolicyOwnerTableFilling implements Initializable, Controller {


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //see the ClaimTableFilling Class
        userFillingData();
        fillingClaimTable(entityManager, user, ClaimRead.getAllClaimsProcessByAnInsuranceSurveyor(entityManager, user.getId()));
        fillingDependantTable(entityManager, user, CustomerRead.getAllDependantsTakeChargeByAnEmployee(entityManager, user.getId()));
        fillingPolicyHolderTable(entityManager, user, CustomerRead.getAllPolicyHoldersTakeChargeByAnEmployee(entityManager, user.getId()));
        fillingPolicyOwnerTable(entityManager, user, CustomerRead.getAllPolicyOwnersTakeChargeByAnEmployee(entityManager, user.getId()));
    }

    public InsuranceSurveyorDashBoardController(InsuranceSurveyor user, EntityManager entityManager) {
        super(entityManager, user);

    }
}