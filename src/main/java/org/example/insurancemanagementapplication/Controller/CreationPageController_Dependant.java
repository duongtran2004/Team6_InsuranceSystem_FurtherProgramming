package org.example.insurancemanagementapplication.Controller;

import Entity.*;
import jakarta.persistence.EntityManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.insurancemanagementapplication.MainEntryPoint;
import org.example.insurancemanagementapplication.Interfaces.CustomerCreateRemove;
import org.example.insurancemanagementapplication.Interfaces.CustomerUpdate;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Luong Thanh Trung
 * @version ${}
 * @created 29/04/2024 11:50
 * @project InsuranceManagementTeamProject
 */
public class CreationPageController_Dependant implements CustomerCreateRemove, CustomerUpdate, Initializable {
    private EntityManager entityManager;
    private User user;
    private PolicyHolder policyHolder;
    private Dependant dependant;

    @FXML
    private TextField fullNameField;
    @FXML
    private TextField addressField;
    @FXML
    private TextField phoneNumberField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField passwordField;
    @FXML
    private TextField passwordValidationField;
    @FXML
    private Label errorContainer;
    @FXML
    private Button submitButton;
    @FXML
    private Button returnButton;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (dependant != null){
            fullNameField.setText(dependant.getFullName());
            fullNameField.setDisable(true);
            addressField.setText(dependant.getAddress());
            phoneNumberField.setText(dependant.getPhoneNumber());
            emailField.setText(dependant.getEmail());
            passwordField.setText(dependant.getPassword());
            passwordValidationField.setText(dependant.getPassword());
            submitButton.setOnAction(event -> {
                CustomerUpdate.updateDependant(entityManager, dependant, errorContainer, addressField.getText(), phoneNumberField.getText(), emailField.getText(), passwordField.getText(), passwordValidationField.getText());
            });
        }
        else {
            submitButton.setOnAction(event -> {
                CustomerCreateRemove.createDependant(entityManager, errorContainer, fullNameField.getText(), addressField.getText(), phoneNumberField.getText(), emailField.getText(), passwordField.getText(), passwordValidationField.getText(), policyHolder);
            });
        }
        returnButton.setOnAction(event -> {
            if (user instanceof SystemAdmin){
                DashBoardController_SystemAdmin dashBoardControllerSystemAdmin = new DashBoardController_SystemAdmin(entityManager, (SystemAdmin) user);
                Stage stage = (Stage) returnButton.getScene().getWindow();
                FXMLLoader fxmlLoader = new FXMLLoader(MainEntryPoint.class.getResource("DashBoard_SystemAdmin.fxml"));
                fxmlLoader.setController(dashBoardControllerSystemAdmin);
                try {
                    Scene scene = new Scene(fxmlLoader.load());
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            else if (user instanceof InsuranceManager){
                DashBoardController_InsuranceManager dashBoardControllerInsuranceManager = new DashBoardController_InsuranceManager((InsuranceManager) user, entityManager);
                Stage stage = (Stage) returnButton.getScene().getWindow();
                FXMLLoader fxmlLoader = new FXMLLoader(MainEntryPoint.class.getResource("DashBoard_InsuranceManager.fxml"));
                fxmlLoader.setController(dashBoardControllerInsuranceManager);
                try {
                    Scene scene = new Scene(fxmlLoader.load());
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            else if (user instanceof InsuranceSurveyor){
                DashBoardController_InsuranceSurveyor dashBoardControllerInsuranceSurveyor = new DashBoardController_InsuranceSurveyor((InsuranceSurveyor) user, entityManager);
                Stage stage = (Stage) returnButton.getScene().getWindow();
                FXMLLoader fxmlLoader = new FXMLLoader(MainEntryPoint.class.getResource("DashBoard_InsuranceSurveyor.fxml"));
                fxmlLoader.setController(dashBoardControllerInsuranceSurveyor);
                try {
                    Scene scene = new Scene(fxmlLoader.load());
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            else if (user instanceof PolicyOwner){
                DashBoardController_PolicyOwner dashBoardController_policyOwner = new DashBoardController_PolicyOwner((PolicyOwner) user, entityManager);
                Stage stage = (Stage) returnButton.getScene().getWindow();
                FXMLLoader fxmlLoader = new FXMLLoader(MainEntryPoint.class.getResource("DashBoard_PolicyOwner.fxml"));
                fxmlLoader.setController(dashBoardController_policyOwner);
                try {
                    Scene scene = new Scene(fxmlLoader.load());
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            else if (user instanceof PolicyHolder){
                DashBoardController_PolicyHolder dashBoardControllerPolicyHolder = new DashBoardController_PolicyHolder((PolicyHolder) user, entityManager);
                Stage stage = (Stage) returnButton.getScene().getWindow();
                FXMLLoader fxmlLoader = new FXMLLoader(MainEntryPoint.class.getResource("DashBoard_PolicyHolder.fxml"));
                fxmlLoader.setController(dashBoardControllerPolicyHolder);
                try {
                    Scene scene = new Scene(fxmlLoader.load());
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }


        });
    }

    public CreationPageController_Dependant(EntityManager entityManager, User user, PolicyHolder policyHolder) {
        this.entityManager = entityManager;
        this.user = user;
        this.policyHolder = policyHolder;
    }

    public CreationPageController_Dependant(EntityManager entityManager, User user, Dependant dependant) {
        this.entityManager = entityManager;
        this.user = user;
        this.dependant = dependant;
    }
}
