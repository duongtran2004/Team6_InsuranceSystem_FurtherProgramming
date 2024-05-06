package org.example.insurancemanagementapplication.Controller.DashBoardController;

import Entity.InsuranceManager;
import jakarta.persistence.EntityManager;
import javafx.fxml.Initializable;
import org.example.insurancemanagementapplication.Controller.DashBoardController.TableFillingController.InsuranceSurveyorTableFilling;
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

        fillingInsuranceSurveyorTable(entityManager, user, EmployeeRead.getAllInsuranceSurveyor(entityManager));
        //fill all the table of customers
        fillingDependantTable(entityManager, user, CustomerRead.getAllDependant(entityManager));
        fillingPolicyHolderTable(entityManager, user, CustomerRead.getAllPolicyHolder(entityManager));
        fillingPolicyOwnerTable(entityManager, user, CustomerRead.getAllPolicyOwner(entityManager));

    }

}
