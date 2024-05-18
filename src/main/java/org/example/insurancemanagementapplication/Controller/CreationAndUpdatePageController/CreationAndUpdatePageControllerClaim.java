package org.example.insurancemanagementapplication.Controller.CreationAndUpdatePageController;

import Entity.*;
import jakarta.persistence.EntityManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import org.example.insurancemanagementapplication.Interfaces.ClaimCreateRemove;
import org.example.insurancemanagementapplication.Interfaces.ClaimUpdate;
import org.example.insurancemanagementapplication.Interfaces.Controller;
import org.example.insurancemanagementapplication.Interfaces.EmployeeRead;
import org.example.insurancemanagementapplication.Utility.IDGenerator;
import org.example.insurancemanagementapplication.Utility.InputValidator;

import javax.swing.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

public class CreationAndUpdatePageControllerClaim extends CreationAndUpdatePageController implements Initializable, Controller {

    private Beneficiaries beneficiary;
    private Claim claim;
    private boolean updateFile;

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
    private TextField insuranceSurveyorIDField;


    @FXML
    private Label filePathLabel;
    @FXML
    private Label errorContainer;
    @FXML
    private Button submitButton;
    @FXML
    private Button returnButton;
    @FXML
    private ChoiceBox<String> statusChoiceBox;
    @FXML
    private Button uploadDocumentButton;
    @FXML
    private Button viewDocumentButton;
    @FXML
    private Label pageTittleLabel;
    @FXML
    private Button updateDocumentButton;


    //for file upload
    private byte[] file;


    public String FolderChooser() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select a Folder");
        File newDirectory = directoryChooser.showDialog((Stage) viewDocumentButton.getScene().getWindow());

        if (newDirectory != null) {
            String fileName = "claim" + claim.getClaimId() + "document.jpg";
            String filePath = newDirectory.getAbsolutePath() + File.separator + fileName;
            return filePath;
        }
        return null;

    }

    public void downloadDocument(String filePath) {
        byte[] cover = claim.getDocumentFile();

        try (FileOutputStream fos
                     = new FileOutputStream(filePath)) {
            fos.write(cover);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        updateDocumentButton.setOnAction(event -> {
            updateFile = !updateFile;
            if (updateFile) {
                uploadDocumentButton.setDisable(false);
                file = claim.getDocumentFile();
            }
            else {
                uploadDocumentButton.setDisable(true);

            }
        });

        viewDocumentButton.setOnAction(event -> {
            String filePath = FolderChooser();
            if (filePath != null) {
                downloadDocument(filePath);
            }
        });
        uploadDocumentButton.setOnAction(event -> {
            JFileChooser jFileChooser = new JFileChooser();
            int response = jFileChooser.showOpenDialog(null);

            if (response == JFileChooser.APPROVE_OPTION) {
                File uploadedfile = new File(jFileChooser.getSelectedFile().getAbsolutePath());
                filePathLabel.setText(uploadedfile.getAbsolutePath());
                try {
                    file = Files.readAllBytes(Path.of(uploadedfile.getPath()));

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }

        });
        setActionReturnButton();
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
                updateModeInsuranceSurveyor();
            }
            // UPDATE CASE 2: INSURANCE MANAGER
            // if user is InsuranceManager: can only change claimAmount and Status (to either "Rejected or Approved")
            if (user instanceof InsuranceManager) {
                updateModeInsuranceManager();
            }

            // UPDATE CASE 3: POLICY HOLDER OR POLICY OWNER
            if (user instanceof PolicyOwner || user instanceof PolicyHolder) {
                updateModeCustomer();
            }

            //VIEW ONLY CASE: FOR SYSTEM ADMIN
            if (user instanceof SystemAdmin) {
                viewModeSystemAdmin();
            }


        }
        //2nd case: Create claim => If claim does NOT exist

        if (claim == null) {
            createMode();
        }
    }

    public void viewModeSystemAdmin() {
        //disable necessary fields and hide necessary buttons
        bankAccountNameField.setDisable(true);
        bankAccountNumberField.setDisable(true);
        bankNameField.setDisable(true);
        claimAmountField.setDisable(true);

        insuranceSurveyorIDField.setDisable(true);

        statusChoiceBox.setDisable(true);

        submitButton.setDisable(true);
        uploadDocumentButton.setDisable(true);
        updateDocumentButton.setDisable(true);

    }

    //This method is called when the user is an insurance surveyor and the claim attribute of the object is not null
    public void updateModeInsuranceSurveyor() {
        //disable necessary fields
        changePageTitleInUpdateMode("CLAIM UPDATE PAGE");
        bankAccountNameField.setDisable(true);
        bankAccountNumberField.setDisable(true);
        bankNameField.setDisable(true);
        claimAmountField.setDisable(true);
        uploadDocumentButton.setDisable(true);
        updateDocumentButton.setDisable(true);

        //set disable for insurance manager and insurance surveyor field
        insuranceSurveyorIDField.setDisable(true);


        //set Array String for status choice box
        String[] Status = {"NEED INF", "PROCESSING"};
        statusChoiceBox.getItems().addAll(Status);

        //After user click on  Submit Button to Update Claim
        submitButton.setOnAction(event -> {
            ClaimUpdate.updateClaim(submitButton, user, entityManager, claim, statusChoiceBox.getValue());
        });
    }

    //This method is called when the user is an insurance manager and the claim attribute of the object is not null
    public void updateModeInsuranceManager() {
        changePageTitleInUpdateMode("CLAIM UPDATE PAGE");
        bankAccountNameField.setDisable(true);
        bankAccountNumberField.setDisable(true);
        bankNameField.setDisable(true);
        uploadDocumentButton.setDisable(true);
        updateDocumentButton.setDisable(true);


        claimAmountField.setDisable(true);
        //set Array String for status choice box


        // If user is a manager and the selected claim has "NEW" status, the only option in the choice box will be "NEW", and the insurance surveyor field will not be disabled
        if (claim.getStatus().equals("NEW")) {
            String[] status = {"NEW"};
            statusChoiceBox.getItems().addAll(status);
            String insuranceSurveyorID = insuranceSurveyorIDField.getText();
            //Define handler for the submit button in case the claim's status is "NEW". The manager could only update the claim's Insurance Surveyor in this case
            submitButton.setOnAction(event -> {
                ArrayList<InsuranceSurveyor> insuranceSurveyors = (ArrayList<InsuranceSurveyor>) ((InsuranceManager) user).getListOfSurveyors();
                for (int i = 0; i < insuranceSurveyors.size(); i++) {
                    if (insuranceSurveyors.get(i).getId().equals(insuranceSurveyorID)) {
                        ClaimUpdate.updateClaim(submitButton, user, entityManager, claim, insuranceSurveyors.get(i));
                        break;
                    } else {
                        errorContainer.setText("Insurance Surveyor either does not exist or not belong to you");
                    }
                }

            });
        }

        //If user is a manager and the selected claim has one of the statuses, the insurance surveyor field will be disabled
        else {
            insuranceSurveyorIDField.setDisable(true);
            //If the selected claim has "NEED INFO" status, the manager cannot select any other option. The submit button will also be disabled in this case
            if (claim.getStatus().equals("NEED INFO")) {
                String[] status = {"NEED INFO"};
                statusChoiceBox.getItems().addAll(status);
                submitButton.setDisable(true);
            }
            //If the selected claim has "PROCESSING" status, the manager can select either "APPROVED" or "REJECTED"
            else if (claim.getStatus().equals("PROCESSING")) {
                String[] status = {"APPROVED", "REJECTED"};
                statusChoiceBox.getItems().addAll(status);
                //Monitoring for changes in the choice box's value. If the new value is "APPROVED", manager will have access to the claimAmountField
                statusChoiceBox.valueProperty().addListener((observable, oldVal, newVal) -> {
                    if (newVal.equals("APPROVED")) {
                        claimAmountField.setDisable(false);
                    } else {
                        claimAmountField.setDisable(true);
                    }
                });

            }
            //The choice box will be disabled in other cases
            else {
                statusChoiceBox.setDisable(true);
                submitButton.setDisable(true);
            }

            //Defining handler for the submit button in case the user selects either "APPROVED" or "REJECTED".
            submitButton.setOnAction(event -> {
                String status = statusChoiceBox.getValue(); //get Status from choiceBox
                Date date = new Date();
                java.sql.Date settlementDate = new java.sql.Date(date.getTime());
                //get claimAmount from TextField
                if (status.equals("APPROVED")) {
                    String stringClaimAmount = claimAmountField.getText();
                    int claimAmount = Integer.parseInt(stringClaimAmount);
                    String message = InputValidator.ClaimUpdateValidator(entityManager, claimAmount, (InsuranceManager) user);
                    if (message.equals("Success")) {
                        //If the message is "Success", update the claim in the database
                        //Selecting "APPROVED" will update claim's amount, claim's settlement date, and claim's status
                        ClaimUpdate.updateClaim(submitButton, user, entityManager, claim, claimAmount, settlementDate, statusChoiceBox.getValue());
                    } else {
                        errorContainer.setText(message);
                    }
                    //input validator for claim amount

                } else {
                    //Selecting "REJECTED" will update claim's settlement date, and claim's status
                    ClaimUpdate.updateClaim(submitButton, user, entityManager, claim, settlementDate, statusChoiceBox.getValue());
                }

            });
        }
    }

    //This method is called when the user is either an owner or a holder, and the claim attribute of the object is not null
    public void updateModeCustomer() {
        changePageTitleInUpdateMode("CLAIM UPDATE PAGE");
        //disable necessary fields
        statusChoiceBox.setDisable(true);
        claimAmountField.setDisable(true);

        insuranceSurveyorIDField.setDisable(true);
        //not allowed to update insured person id
        //After use clicked on submit button
        submitButton.setOnAction(event -> {
//read text fields => store to variables
            String bankAccountName = bankAccountNameField.getText();
            String bankAccountNumber = bankAccountNumberField.getText();
            String bankName = bankNameField.getText();
            // input validator for Claim's Field
            errorContainer.setText(InputValidator.ClaimUpdateValidator(entityManager, bankName, bankAccountName, bankAccountNumber));
            if (errorContainer.getText().equals("Success")) {
                if (updateFile){
                    ClaimUpdate.updateClaim(submitButton, user, entityManager, claim, statusChoiceBox.getValue());

                }else {ClaimUpdate.updateClaim(submitButton, user, entityManager, claim, bankName, bankAccountName, bankAccountNumber, file);}

            }
        });
    }

    //This method is called when the claim attribute of the object is null
    public void createMode() {
        //no need to set the condition if user is instance of policyHolder or policyOwner (Front-end logic handled that)
        //disable necessary fields
        updateDocumentButton.setDisable(true);
        viewDocumentButton.setDisable(true);
        statusChoiceBox.setDisable(true);
        claimAmountField.setDisable(true);

        insuranceSurveyorIDField.setDisable(true);

        submitButton.setOnAction(event -> {
            //read text fields => store to variables
            String bankAccountName = bankAccountNameField.getText();
            String bankAccountNumber = bankAccountNumberField.getText();
            String bankName = bankNameField.getText();
            // input validator for Claim's Field
            errorContainer.setText(InputValidator.ClaimUpdateValidator(entityManager, bankName, bankAccountName, bankAccountNumber));
            if (errorContainer.getText().equals("Success")) {

                Date today = new Date();
                java.sql.Date sqlToday = new java.sql.Date(today.getTime());
                String claimId = IDGenerator.generateId("C");
                InsuranceManager insuranceManager = EmployeeRead.findRandomInsuranceManager(submitButton, user, entityManager);
                System.out.println(insuranceManager);
                ClaimCreateRemove.createClaim(submitButton, entityManager, user, claimId, sqlToday, beneficiary, beneficiary.getPolicyOwner(), beneficiary.getInsuranceCard(), insuranceManager, bankName, bankAccountName, bankAccountNumber, file);


// call method to write action history  to file right here

            }


        });
    }


    public CreationAndUpdatePageControllerClaim(EntityManager entityManager, User user, Beneficiaries beneficiary) {
        super(entityManager, user);
        this.beneficiary = beneficiary;
    }

    public CreationAndUpdatePageControllerClaim(EntityManager entityManager, User user, Claim claim) {
        super(entityManager, user);
        this.claim = claim;
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
