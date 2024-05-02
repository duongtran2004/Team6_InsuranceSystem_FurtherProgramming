package org.example.insurancemanagementapplication.Controller;

import Entity.*;
import jakarta.persistence.EntityManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.example.insurancemanagementapplication.Interfaces.EmployeeAnalytics;
import org.example.insurancemanagementapplication.Utility.InputValidator;

import java.net.URL;
import java.util.List;
import java.util.Random;
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
    private TextField cardNumberField;


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

            // UPDATE CASE 1: INSURANCE SURVEYOR
            //if user is Insurance Surveyor: can only view document and change status
            if (user instanceof InsuranceSurveyor) {
                //disable necessary fields
                bankAccountNameField.setDisable(true);
                bankAccountNumberField.setDisable(true);
                bankNameField.setDisable(true);
                claimAmountField.setDisable(true);
                cardNumberField.setDisable(true);
                //set disable for insurance manager and insurance surveyor field
                insuranceSurveyorIdField.setDisable(true);
                insuranceManagerIdField.setDisable(true);

                //set Array String for status choice box
                String[] Status = {"NEED INF", "PROCESSING"};
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
                cardNumberField.setDisable(true);
                insuranceSurveyorIdField.setDisable(true);
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
                //disable necessary fields
                statusChoiceBox.setDisable(true);
                claimAmountField.setDisable(true);
                insuranceManagerIdField.setDisable(true);
                insuranceSurveyorIdField.setDisable(true);
                cardNumberField.setDisable(true);
                //not allowed to update insured person id
                //After use clicked on submit button
                submitButton.setOnAction(event -> {
//read text fields => store to variables
                    String bankAccountName = bankAccountNameField.getText();
                    String bankAccountNumber = bankAccountNumberField.getText();
                    String bankName = bankNameField.getText();
                    // input validator for Claim's Field
                    errorContainer.setText(InputValidator.ClaimUpdateValidator(entityManager, bankName, bankAccountName, bankAccountNumber));
                    if (errorContainer.getText() == "Success") {
                        claim.setBankAccountNumber(bankAccountNumber);
                        claim.setBankAccountName(bankAccountName);
                        claim.setBankName(bankName);
                    }


                });


            }
        }

        //2nd case: Create claim => If claim does NOT exist

        if (claim == null) {
            //no need to set the condition if user is instance of policyHolder or policyOwner (Front-end logic handled that)
            //disable necessary fields
            statusChoiceBox.setDisable(true);
            claimAmountField.setDisable(true);
            insuranceManagerIdField.setDisable(true);
            insuranceSurveyorIdField.setDisable(true);
            cardNumberField.setDisable(true);
            submitButton.setOnAction(event -> {
                //read text fields => store to variables
                String bankAccountName = bankAccountNameField.getText();
                String bankAccountNumber = bankAccountNumberField.getText();
                String bankName = bankNameField.getText();
                // input validator for Claim's Field
                errorContainer.setText(InputValidator.ClaimUpdateValidator(entityManager, bankName, bankAccountName, bankAccountNumber));
                if (errorContainer.getText() == "Success") {
                    claim.setBankAccountName(bankAccountName);
                    claim.setBankAccountNumber(bankAccountNumber);
                    claim.setBankName(bankName);
                    randomlyAssignClaimsToInsuranceManager(entityManager, claim);
                }


            });
        }
    }


    public static void randomlyAssignClaimsToInsuranceManager(EntityManager entityManager, Claim claim) {
        List<InsuranceManager> insuranceManagers = EmployeeAnalytics.getAllInsuranceManager(entityManager);
        Random random = new Random();
        int randomIndex = random.nextInt(insuranceManagers.size());
        InsuranceManager randomManager = insuranceManagers.get(randomIndex);
        claim.setInsuranceManager(randomManager);
    }


//        public static void initializeUpdateCases () {
//
//
//        }
//
//        public static void initializeCreateCases () {
//
//
//        }

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
