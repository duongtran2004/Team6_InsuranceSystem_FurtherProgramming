package org.example.insurancemanagementapplication.Controller;

import Entity.*;
import jakarta.persistence.EntityManager;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.example.insurancemanagementapplication.Controller.DashBoardController.*;
import org.example.insurancemanagementapplication.Utility.StageBuilder;

/**
 * @author Luong Thanh Trung
 * @version ${}
 * @created 18/05/2024 04:11
 * @project Team6_InsuranceSystem_FurtherProgramming
 */
public class RootController {
    public void returnToDashBoard(User user, EntityManager entityManager, Button returnButton){
        if (user instanceof SystemAdmin) {
            SystemAdminDashBoardController dashBoardControllerSystemAdmin = new SystemAdminDashBoardController(entityManager, (SystemAdmin) user);
            StageBuilder.showStage((Stage) returnButton.getScene().getWindow(), dashBoardControllerSystemAdmin, "SystemAdminDashBoard.fxml", "Dashboard");
        } else if (user instanceof InsuranceManager) {
            InsuranceManagerDashBoardController dashBoardControllerInsuranceManager = new InsuranceManagerDashBoardController((InsuranceManager) user, entityManager);
            StageBuilder.showStage((Stage) returnButton.getScene().getWindow(), dashBoardControllerInsuranceManager, "InsuranceManagerDashBoard.fxml", "Dashboard");

        } else if (user instanceof InsuranceSurveyor) {

            InsuranceSurveyorDashBoardController dashBoardControllerInsuranceSurveyor = new InsuranceSurveyorDashBoardController((InsuranceSurveyor) user, entityManager);
            StageBuilder.showStage((Stage) returnButton.getScene().getWindow(), dashBoardControllerInsuranceSurveyor, "InsuranceSurveyorDashBoard.fxml", "Dashboard");

        } else if (user instanceof PolicyOwner) {
            PolicyOwnerDashBoardController dashBoardController_policyOwner = new PolicyOwnerDashBoardController((PolicyOwner) user, entityManager);
            StageBuilder.showStage((Stage) returnButton.getScene().getWindow(), dashBoardController_policyOwner, "PolicyOwnerDashBoard.fxml", "Dashboard");

        } else if (user instanceof PolicyHolder) {
            PolicyHolderDashBoardController dashBoardControllerPolicyHolder = new PolicyHolderDashBoardController((PolicyHolder) user, entityManager);
            StageBuilder.showStage((Stage) returnButton.getScene().getWindow(), dashBoardControllerPolicyHolder, "PolicyHolderDashBoard.fxml", "Dashboard");

        }
    }
}
