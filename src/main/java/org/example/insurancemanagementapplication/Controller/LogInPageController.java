package org.example.insurancemanagementapplication.Controller;

import Entity.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.insurancemanagementapplication.Controller.DashBoardController.*;
import org.example.insurancemanagementapplication.Interfaces.Controller;
import org.example.insurancemanagementapplication.Interfaces.CustomerRead;
import org.example.insurancemanagementapplication.Interfaces.EmployeeRead;
import org.example.insurancemanagementapplication.Utility.StageBuilder;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Luong Thanh Trung
 * @version ${}
 * @created 27/04/2024 04:50
 * @project InsuranceManagementTeamProject
 */
public class LogInPageController implements Controller, Initializable, CustomerRead, EmployeeRead {
    EntityManager entityManager;

    public LogInPageController(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @FXML
    private TextField userIdField;
    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;
    @FXML
    private ChoiceBox<String> roleSelectionBox;
    @FXML
    private Button logInButton;
    @FXML
    private Label errorContainer;
    private String[] roles = {"System Admin", "Insurance Manager", "Insurance Surveyor", "Policy Owner", "Policy Holder", "Dependant"};


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        roleSelectionBox.getItems().addAll(roles);
        roleSelectionBox.setValue("System Admin");
        logInButton.setOnAction(event -> {
            String email = emailField.getText();
            String userId = userIdField.getText();
            String password = passwordField.getText();
            String role = roleSelectionBox.getValue();
            Stage stage = (Stage) logInButton.getScene().getWindow();
            //try-catch block to catch NoResultException (user not found)
//            try {
            if (role.equals("System Admin")) {
                try{
                    SystemAdmin systemAdmin = EmployeeRead.getSystemAdminWithCredential(entityManager, userId, email, password);
                    SystemAdminDashBoardController dashBoardControllerSystemAdmin = new SystemAdminDashBoardController(entityManager, systemAdmin);
                    StageBuilder.showStage((Stage) logInButton.getScene().getWindow(), dashBoardControllerSystemAdmin,"SystemAdminDashBoard.fxml", "System Admin Dashboard" );

                } catch (NoResultException e){
                    errorContainer.setText("User Not Found");
                }


            } else if (role.equals("Insurance Manager")) {
                try {
                    InsuranceManager insuranceManager = EmployeeRead.getInsuranceManagerWithCredential(entityManager, userId, email, password);
                    InsuranceManagerDashBoardController dashBoardControllerInsuranceManager = new InsuranceManagerDashBoardController(insuranceManager, entityManager);
                    StageBuilder.showStage((Stage) logInButton.getScene().getWindow(), dashBoardControllerInsuranceManager,"InsuranceManagerDashBoard.fxml", "Insurance Manager Dashboard" );
                } catch (NoResultException e){
                    errorContainer.setText("User Not Found");
                }

            }
            else if (role.equals("Insurance Surveyor")) {
                try {
                    InsuranceSurveyor insuranceSurveyor = EmployeeRead.getInsuranceSurveyorWithCredential(entityManager, userId, email, password);
                    InsuranceSurveyorDashBoardController dashBoardControllerInsuranceSurveyor = new InsuranceSurveyorDashBoardController(insuranceSurveyor, entityManager);
                    StageBuilder.showStage((Stage) logInButton.getScene().getWindow(), dashBoardControllerInsuranceSurveyor,"InsuranceSurveyorDashBoard.fxml", "Insurance Surveyor Dashboard" );
                } catch (NoResultException e){
                    errorContainer.setText("User Not Found");
                }

            } else if (role.equals("Policy Owner")) {
                try {
                    PolicyOwner policyOwner = CustomerRead.getPolicyOwnerWithLoginCredentials(entityManager, email, password, userId);
                    PolicyOwnerDashBoardController dashBoardController_policyOwner = new PolicyOwnerDashBoardController(policyOwner, entityManager);
                    StageBuilder.showStage((Stage) logInButton.getScene().getWindow(), dashBoardController_policyOwner,"PolicyOwnerDashBoard.fxml", "Policy Owner Dashboard" );
                } catch (NoResultException e){
                    errorContainer.setText("User Not Found");
                }

            } else if (role.equals("Policy Holder")) {
                try {
                    PolicyHolder policyHolder = CustomerRead.getPolicyHolderWithLoginCredentials(entityManager, email, password, userId);
                    PolicyHolderDashBoardController dashBoardController_policyHolder = new PolicyHolderDashBoardController(policyHolder, entityManager);
                    StageBuilder.showStage((Stage) logInButton.getScene().getWindow(), dashBoardController_policyHolder,"PolicyHolderDashBoard.fxml", "Policy Holder Dashboard" );
                } catch (NoResultException e){
                    errorContainer.setText("User Not Found");
                }



            } else {
                try {
                    Dependant dependant = CustomerRead.getDependentWithLoginCredentials(entityManager, email, password, userId);
                    DependantDashBoardController dependantDashBoardController = new DependantDashBoardController(dependant, entityManager);
                    StageBuilder.showStage((Stage) logInButton.getScene().getWindow(), dependantDashBoardController,"DependantDashBoard.fxml", "Dependant Dashboard" );
                } catch (NoResultException e){
                    errorContainer.setText("User Not Found");
                }

//                    Dependant dependant = (Dependant) CustomerRead.getCustomerWithCredentials(entityManager, userId, email, password, "Dependant");

            }

        });


    }




}
