package org.example.insurancemanagementapplication.Controller.DashBoardController;

import Entity.SystemAdmin;
import Entity.User;
import jakarta.persistence.EntityManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.example.insurancemanagementapplication.Controller.CreationPageController.CreationPageController_InsuranceManager;
import org.example.insurancemanagementapplication.Controller.CreationPageController.CreationPageController_PolicyOwner;
import org.example.insurancemanagementapplication.Controller.DashBoardController.TableFillingController.InsuranceManagerTableFilling;
import org.example.insurancemanagementapplication.Interfaces.*;
import org.example.insurancemanagementapplication.Utility.InputValidator;
import org.example.insurancemanagementapplication.Utility.StageBuilder;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Luong Thanh Trung
 * @version ${}
 * @created 27/04/2024 04:55
 * @project InsuranceManagementTeamProject
 */
public class SystemAdminDashBoardController extends InsuranceManagerTableFilling implements ClaimRead, EmployeeCreateRemove, CustomerCreateRemove, Initializable, EmployeeRead, Controller {


    //import necessary FXML controller object for the creation and update form at the top
    @FXML
    private Button updateInfoButton;
    @FXML
    private Button addPolicyOwnerButton;
    @FXML
    private Button addManagerButton;

    //constructor


    public SystemAdminDashBoardController(EntityManager entityManager, User user) {
        super(entityManager, user);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //UPDATE INFO
        //See the ClaimTableFilling Class for this method
        //automatically fill the top form with current user info
        userFillingData();
        //
        updateInfoButton.setOnAction(event -> {
            String message = InputValidator.validatingUser(emailField.getText(), passwordField.getText(), phoneNumberField.getText(), addressField.getText(), passwordValidationField.getText());
            if (message.equals("Success")){
                EmployeeUpdate.updateSystemAdmin(entityManager, (SystemAdmin) user, addressField.getText(), phoneNumberField.getText(), addressField.getText(), passwordField.getText());
            }
            else {
                errorContainer.setText(message);
            }

        });
        // ADD A NEW POLICY OWNER USER

        //Add handler to the addPolicyOwnerButton. this function creates a CreationPage Controller for Policy Owner and then transfer the user to the Policy Owner Creation Page
        addPolicyOwnerButton.setOnAction(event -> {
            CreationPageController_PolicyOwner creationPageControllerPolicyOwner = new CreationPageController_PolicyOwner(entityManager, user);
            //See the RepeatedCode class for this method
            StageBuilder.showStage((Stage) addPolicyOwnerButton.getScene().getWindow(), creationPageControllerPolicyOwner, "PolicyOwnerCreationPage.fxml", "Policy Owner Creation Page");
        });

        // ADD A NEW INSURANCE MANAGER USER
        //Add handler to the addManagerButton. this function creates a CreationPage Controller for Insurance Manager and then transfer the user to the Insurance Manager Creation Page
        addManagerButton.setOnAction(event -> {
            CreationPageController_InsuranceManager creationPageControllerInsuranceManager = new CreationPageController_InsuranceManager(entityManager, user);
            StageBuilder.showStage((Stage) addManagerButton.getScene().getWindow(), creationPageControllerInsuranceManager, "InsuranceManagerCreationPage.fxml", "Insurance Manager Creation Page");
        });

        //FILL ALL THE NECESSARY TABLE (CALL METHODS IN TABLE FILLING CLASS)
        //METHOD FROM TABLE FILLING CLASS CALLS "READ ALL" METHODS IN READ INTERFACES (PASS ENTITY MANAGER AS ARGUMENT)

        //Task: Create a separate thread to fill in Insurance Manager Table
        fillingInsuranceManagerTable(entityManager, user, EmployeeRead.getAllInsuranceManager(entityManager));
        //Task: Create a separate thread to fill in the Insurance Surveyor Table
        fillingInsuranceSurveyorTable(entityManager, user, EmployeeRead.getAllInsuranceSurveyor(entityManager));
        //Task: Create a separate thread to fill in the Policy Owner Table
        fillingPolicyOwnerTable(entityManager, user, CustomerRead.getAllPolicyOwner(entityManager));
        //Task: Create a separate thread to fill in the Policy Holder Table
        fillingPolicyHolderTable(entityManager, user, CustomerRead.getAllPolicyHolder(entityManager));
        //Task: Create a separate thread to fill in the Dependant Table
        fillingDependantTable(entityManager, user, CustomerRead.getAllDependant(entityManager));
        //Task: Create a separate thread to fill in the Insurance Claim Table
        fillingInsuranceCardTable(entityManager, user, InsuranceCardRead.getAllInsuranceCard(entityManager));
        //Task: Create a separate thread to fill in the Claim Table
        fillingClaimTable(entityManager, user, ClaimRead.getAllClaims(entityManager));

    }



    public EntityManager getEntityManager() {
        return entityManager;
    }




}
