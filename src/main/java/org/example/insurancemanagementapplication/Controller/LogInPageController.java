package org.example.insurancemanagementapplication.Controller;

import Entity.*;
import jakarta.persistence.EntityManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.insurancemanagementapplication.Controller.DashBoardController.*;
import org.example.insurancemanagementapplication.Interfaces.CustomerRead;
import org.example.insurancemanagementapplication.Interfaces.EmployeeRead;
import org.example.insurancemanagementapplication.MainEntryPoint;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Luong Thanh Trung
 * @version ${}
 * @created 27/04/2024 04:50
 * @project InsuranceManagementTeamProject
 */
public class LogInPageController implements Initializable, CustomerRead, EmployeeRead {
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
    private TextField passwordField;
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
        logInButton.setOnAction(event -> {
            String email = emailField.getText();
            String userId = userIdField.getText();
            String password = passwordField.getText();
            String role = roleSelectionBox.getValue();
            Stage stage = (Stage) logInButton.getScene().getWindow();
            //try-catch block to catch NoResultException (user not found)
//            try {
                if (role.equals("System Admin")){
                    SystemAdmin systemAdmin = EmployeeRead.getSystemAdminWithCredential(entityManager, userId, email, password);
                    if (systemAdmin != null){
                        FXMLLoader fxmlLoader = new FXMLLoader(MainEntryPoint.class.getResource("DashBoard_SystemAdmin.fxml"));
                        DashBoardController_SystemAdmin dashBoardControllerSystemAdmin = new DashBoardController_SystemAdmin(entityManager, systemAdmin);
                        fxmlLoader.setController(dashBoardControllerSystemAdmin);
                        try {
                            Scene scene = new Scene(fxmlLoader.load());
                            stage.setScene(scene);
                            stage.show();

                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }

                }
                else if (role.equals("Insurance Manager")){
                    InsuranceManager insuranceManager = EmployeeRead.getInsuranceManagerWithCredential(entityManager, userId, email, password);
                    if (insuranceManager != null){
                        FXMLLoader fxmlLoader = new FXMLLoader(MainEntryPoint.class.getResource("DashBoard_InsuranceManager.fxml"));
                        DashBoardController_InsuranceManager dashBoardControllerInsuranceManager = new DashBoardController_InsuranceManager(insuranceManager, entityManager);
                        fxmlLoader.setController(dashBoardControllerInsuranceManager);
                        try {
                            Scene scene = new Scene(fxmlLoader.load());
                            stage.setScene(scene);
                            stage.show();

                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }

                }
                else if (role.equals("Insurance Surveyor")){
                    InsuranceSurveyor insuranceSurveyor = EmployeeRead.getInsuranceSurveyorWithCredential(entityManager, userId, email, password);
                    if (insuranceSurveyor != null){
                        FXMLLoader fxmlLoader = new FXMLLoader(MainEntryPoint.class.getResource("DashBoard_InsuranceSurveyor.fxml"));
                        DashBoardController_InsuranceSurveyor dashBoardControllerInsuranceSurveyor = new DashBoardController_InsuranceSurveyor(insuranceSurveyor, entityManager);
                        fxmlLoader.setController(dashBoardControllerInsuranceSurveyor);
                        try {
                            Scene scene = new Scene(fxmlLoader.load());
                            stage.setScene(scene);
                            stage.show();

                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }

                }
                else if (role.equals("Policy Owner")){
                    PolicyOwner policyOwner = CustomerRead.getPolicyOwnerWithLoginCredentials(entityManager, email, password, userId);
                    if (policyOwner != null){
                        FXMLLoader fxmlLoader = new FXMLLoader(MainEntryPoint.class.getResource("DashBoard_PolicyOwner.fxml"));
                        DashBoardController_PolicyOwner dashBoardController_policyOwner = new DashBoardController_PolicyOwner(policyOwner, entityManager);
                        fxmlLoader.setController(dashBoardController_policyOwner);
                        try {
                            Scene scene = new Scene(fxmlLoader.load());
                            stage.setScene(scene);
                            stage.show();

                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }

                }

                else if (role.equals("Policy Holder")){
                    PolicyHolder policyHolder = CustomerRead.getPolicyHolderWithLoginCredentials(entityManager, email, password, userId);
                    if (policyHolder != null){
                        FXMLLoader fxmlLoader = new FXMLLoader(MainEntryPoint.class.getResource("DashBoard_PolicyHolder.fxml"));
                        DashBoardController_PolicyHolder dashBoardController_policyHolder = new DashBoardController_PolicyHolder(policyHolder, entityManager);
                        fxmlLoader.setController(dashBoardController_policyHolder);
                        try {
                            Scene scene = new Scene(fxmlLoader.load());
                            stage.setScene(scene);
                            stage.show();

                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }

                }
                else if (role.equals("Dependant")){

//                    Dependant dependant = (Dependant) CustomerRead.getCustomerWithCredentials(entityManager, userId, email, password, "Dependant");
                    Dependant dependant = CustomerRead.getDependentWithLoginCredentials(entityManager, email, password, userId);
                    if (dependant != null){
                        FXMLLoader fxmlLoader = new FXMLLoader(MainEntryPoint.class.getResource("DashBoard_Dependant.fxml"));
                        DashBoardController_Dependant dashBoardController_dependant = new DashBoardController_Dependant(dependant, entityManager);
                        fxmlLoader.setController(dashBoardController_dependant);
                        try {
                            Scene scene = new Scene(fxmlLoader.load());
                            stage.setScene(scene);
                            stage.show();

                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }

//            }catch (NoResultException noResultException){
//                errorContainer.setText("User Not Found. Please Try Again");
//            }

        });


    }



}
