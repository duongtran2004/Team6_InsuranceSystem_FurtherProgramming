package org.example.insurancemanagementapplication.Controller.CreationPageController;

import Entity.*;
import jakarta.persistence.EntityManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.example.insurancemanagementapplication.Controller.DashBoardController.*;
import org.example.insurancemanagementapplication.Utility.RepeatedCode;

/**
 * @author Luong Thanh Trung
 * @version ${}
 * @created 03/05/2024 09:57
 * @project InsuranceManagementTeamProject
 */
public abstract class CreationPageController {
    User user;
    EntityManager entityManager;
    @FXML
    public Button returnButton;

    public CreationPageController(EntityManager entityManager, User user) {
        this.user = user;
        this.entityManager = entityManager;
    }

    public void fillingFormAuto(){

    }

    public void setActionReturnButton(){
        returnButton.setOnAction(event -> {
            if (user instanceof SystemAdmin) {
                DashBoardController_SystemAdmin dashBoardControllerSystemAdmin = new DashBoardController_SystemAdmin(entityManager, (SystemAdmin) user);
                RepeatedCode.showStage((Stage) returnButton.getScene().getWindow(), dashBoardControllerSystemAdmin, "DashBoard_SystemAdmin.fxml", "Dashboard");
            }
            else if (user instanceof InsuranceManager) {
                DashBoardController_InsuranceManager dashBoardControllerInsuranceManager = new DashBoardController_InsuranceManager((InsuranceManager) user, entityManager);
                RepeatedCode.showStage((Stage) returnButton.getScene().getWindow(), dashBoardControllerInsuranceManager, "DashBoard_InsuranceManager.fxml", "Dashboard");

            }
            else if (user instanceof InsuranceSurveyor) {

                DashBoardController_InsuranceSurveyor dashBoardControllerInsuranceSurveyor = new DashBoardController_InsuranceSurveyor((InsuranceSurveyor) user, entityManager);
                RepeatedCode.showStage((Stage) returnButton.getScene().getWindow(), dashBoardControllerInsuranceSurveyor, "DashBoard_InsuranceSurveyor.fxml", "Dashboard");

            }
            else if (user instanceof PolicyOwner) {
                DashBoardController_PolicyOwner dashBoardController_policyOwner = new DashBoardController_PolicyOwner((PolicyOwner) user, entityManager);
                RepeatedCode.showStage((Stage) returnButton.getScene().getWindow(), dashBoardController_policyOwner, "DashBoard_PolicyOwner.fxml", "Dashboard");

            }
            else if (user instanceof PolicyHolder) {
                DashBoardController_PolicyHolder dashBoardControllerPolicyHolder = new DashBoardController_PolicyHolder((PolicyHolder) user, entityManager);
                RepeatedCode.showStage((Stage) returnButton.getScene().getWindow(), dashBoardControllerPolicyHolder, "DashBoard_PolicyHolder.fxml", "Dashboard");

            }


        });
    }
}
