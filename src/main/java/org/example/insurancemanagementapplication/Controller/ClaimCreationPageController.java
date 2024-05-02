package org.example.insurancemanagementapplication.Controller;

import Entity.*;
import jakarta.persistence.EntityManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.example.insurancemanagementapplication.Interfaces.ClaimCreateRemove;
import org.example.insurancemanagementapplication.Interfaces.ClaimUpdate;
import org.example.insurancemanagementapplication.Interfaces.EmployeeAnalytics;
import org.example.insurancemanagementapplication.Utility.InputValidator;

import java.util.Random;
import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

class ClaimCreationPageController implements Initializable {
    private EntityManager entityManager;
    private User user;
    private Beneficiaries beneficiary;
    private PolicyOwner policyOwner;
    private Claim claim;


    //create first, update later
    //import necessary FXML

    @FXML
    private TextField bankAccountNumberField;
    @FXML
    private TextField bankAccountNameField;
    @FXML
    private TextField bankNameField;
    @FXML
    private TextField claimAmountField;
    @FXML
    private TextField insuranceSurveyorIdField;
    @FXML
    private TextField insuranceManagerIdField;


    @FXML
    private Label errorContainer;
    @FXML
    private Button submitButton;
    @FXML
    private Button returnButton;
    @FXML
    private ChoiceBox<String> statusChoiceBox;
    @FXML
    private Button updloadDocumentButton;
    @FXML
    Button viewDocumentButton;


    // Initialize Supabase client and storage


    ;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //1st case: Update Claim
        if (claim != null) {//=> If claim exist
            //Before user click on Submit Button to Update Claim
            //read Claim object and set Claim's current attributes to textField
            bankAccountNumberField.setText(claim.getBankAccountNumber());
            bankAccountNameField.setText(claim.getBankAccountName());
            bankNameField.setText(claim.getBankName());
            claimAmountField.setText(String.valueOf(claim.getClaimAmount()));

            // UPDATE CASE 2: INSURANCE MANAGER
            //if user is Insurance Surveyor: can only view document and change status
            if (user instanceof InsuranceSurveyor) {
                //disable necessary fields
                bankAccountNameField.setDisable(true);
                bankAccountNumberField.setDisable(true);
                bankNameField.setDisable(true);
                claimAmountField.setDisable(true);
                //set disable for insurance manager and insurance surveyor field
                insuranceSurveyorIdField.setDisable(true);
                insuranceManagerIdField.setDisable(true);

                //set Array String for status choice box
                String[] Status = {"Need Info", "Processing"};
                statusChoiceBox.getItems().addAll(Status);

                //After user click on  Submit Button to Update Claim
                submitButton.setOnAction(event -> {
                    String status = (String) statusChoiceBox.getSelectionModel().getSelectedItem(); //get Status from choiceBox
                    claim.setStatus(status);
                });
            }
            // UPDATE CASE 2: INSURANCE MANAGER
            // if user is InsuranceManager: can only change claimAmount and Status (to either "Rejected or Approved")
            if (user instanceof InsuranceManager) {
                bankAccountNameField.setDisable(true);
                bankAccountNumberField.setDisable(true);
                bankNameField.setDisable(true);
                insuranceManagerIdField.setDisable(true);
                //set Array String for status choice box

                if (claim.getStatus() == "PROCESSING") {
                    String[] status = {"APPROVED", "REJECTED"};
                    statusChoiceBox.getItems().addAll(status);
                } else {
                    if (claim.getStatus() == "NEW") {
                        String[] status = {"NEW"};
                        statusChoiceBox.getItems().addAll(status);
                    }
                    if (claim.getStatus() == "NEED INFO") {
                        String[] status = {"NEED INFO"};
                        statusChoiceBox.getItems().addAll(status);
                    }
                }


                //After user click on  Submit Button to Update Claim
                submitButton.setOnAction(event -> {
                    String status = (String) statusChoiceBox.getSelectionModel().getSelectedItem(); //get Status from choiceBox
                    //get claimAmount from TextField
                    String stringClaimAmount = claimAmountField.getText();
                    int claimAmount = Integer.parseInt(stringClaimAmount);
                    //input validator for claim amount
                    errorContainer.setText(InputValidator.ClaimUpdateValidator(entityManager, claimAmount, bankNameField.getText(), bankAccountNameField.getText(), bankAccountNumberField.getText(), (InsuranceManager) user, insuranceSurveyorIdField.getText()));
                    //if errorContainer announce success, process to update Claim
                    if (errorContainer.getText() == "Success") {
                        claim.setClaimAmount(claimAmount);
                        claim.setStatus(status);
                        // set  insurance surveyor object to claim
                        claim.setInsuranceSurveyorId(insuranceSurveyorIdField.getText());
                    }
                });
            }
            // UPDATE CASE 3: POLICY HOLDER OR POLICY OWNER
            if (user instanceof PolicyOwner || user instanceof PolicyHolder) {
                //read text fields => store to variables
                String bankAccountName = bankAccountNameField.getText();
                String bankAccountNumber = bankAccountNumberField.getText();
                String bankName = bankNameField.getText();
                String stringClaimAmount = claimAmountField.getText();
                // input validator for Claim's Field
                errorContainer.setText(InputValidator.ClaimUpdateValidator(entityManager, bankName, bankAccountName, bankAccountNumber));
                if (errorContainer.getText() == "Success") {
                    String status = (String) statusChoiceBox.getSelectionModel().getSelectedItem();
                    //If user is an  InsuranceSurveyor: can only update status
                    ClaimUpdate.updateClaim(entityManager, claim, status);
                }
            }
        }

        //2nd case: Create claim => If claim does NOT exist

        if (claim == null) {

            // Randomly assign claim to Insurance Manager

        }


        //If use is a policy holder and policy owner are the ones who can create claims

        if (user instanceof PolicyHolder || user instanceof PolicyOwner) {

            //

            // pass parameter to create Claim
            ClaimCreateRemove.createClaim();

            statusChoiceBox.getItems().addAll(Status);


            updloadDocumentButton.setOnAction(event -> {
                // Handle button click event to upload document
                uploadDocument("public/avatar1.png", new File("path/to/your/file.png"));
            });
        }


    }

    public static void randomlyAssignClaimsToInsuranceManager() {
    }

    public static void randomlyAssignClaimsToInsuranceManager(EntityManager entityManager, Claim claim) {
        List<InsuranceManager> insuranceManagers = EmployeeAnalytics.getAllInsuranceManager(entityManager);
        Random random = new Random();
        int randomIndex = random.nextInt(insuranceManagers.size());
        InsuranceManager randomManager = insuranceManagers.get(randomIndex);
        claim.setInsuranceManager(randomManager);
    }




public static void initializeUpdateCases() {


}

public static void initializeCreateCases() {


}

//    private void uploadDocument(String s, File file) {
//    }

//    private boolean handleInput() {
//        //get input
//
//        //Validate input
//
//    }

//
//    private void updateClaim() {
//        // textField.setText = claim.getAttributes
//    }
}
