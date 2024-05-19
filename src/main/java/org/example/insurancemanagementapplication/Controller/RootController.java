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
            SystemAdmin systemAdmin = entityManager.find(SystemAdmin.class, user.getId());
            SystemAdminDashBoardController dashBoardControllerSystemAdmin = new SystemAdminDashBoardController(entityManager, systemAdmin);
            StageBuilder.showStage((Stage) returnButton.getScene().getWindow(), dashBoardControllerSystemAdmin, "SystemAdminDashBoard.fxml", "Dashboard");
        } else if (user instanceof InsuranceManager) {
            InsuranceManager insuranceManager = entityManager.find(InsuranceManager.class, user.getId());
            InsuranceManagerDashBoardController dashBoardControllerInsuranceManager = new InsuranceManagerDashBoardController(insuranceManager, entityManager);
            StageBuilder.showStage((Stage) returnButton.getScene().getWindow(), dashBoardControllerInsuranceManager, "InsuranceManagerDashBoard.fxml", "Dashboard");

        } else if (user instanceof InsuranceSurveyor) {
            InsuranceSurveyor insuranceSurveyor = entityManager.find(InsuranceSurveyor.class, user.getId());
            InsuranceSurveyorDashBoardController dashBoardControllerInsuranceSurveyor = new InsuranceSurveyorDashBoardController(insuranceSurveyor, entityManager);
            StageBuilder.showStage((Stage) returnButton.getScene().getWindow(), dashBoardControllerInsuranceSurveyor, "InsuranceSurveyorDashBoard.fxml", "Dashboard");

        } else if (user instanceof PolicyOwner) {
            PolicyOwner policyOwner = entityManager.find(PolicyOwner.class, user.getId());
            PolicyOwnerDashBoardController dashBoardController_policyOwner = new PolicyOwnerDashBoardController(policyOwner, entityManager);
            StageBuilder.showStage((Stage) returnButton.getScene().getWindow(), dashBoardController_policyOwner, "PolicyOwnerDashBoard.fxml", "Dashboard");

        } else if (user instanceof PolicyHolder) {
            PolicyHolder policyHolder = entityManager.find(PolicyHolder.class, user.getId());
            PolicyHolderDashBoardController dashBoardControllerPolicyHolder = new PolicyHolderDashBoardController(policyHolder, entityManager);
            StageBuilder.showStage((Stage) returnButton.getScene().getWindow(), dashBoardControllerPolicyHolder, "PolicyHolderDashBoard.fxml", "Dashboard");

        }
    }
}
