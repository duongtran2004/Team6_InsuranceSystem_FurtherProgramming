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
import org.example.insurancemanagementapplication.Utility.InputValidator;

import java.io.File;
import java.net.URL;
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
    private TextField passwordField;
    @FXML
    private TextField passwordValidationField;
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
    @FXML Button viewClaimButton;

    String[] Status = {"New", "Need Info", "Processing", "Approved", "Rejected"};
    // Initialize Supabase client and storage
    

           ;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //1st case: Update Claim => If claim exist

        if (claim != null) {

            //If user is an  InsuranceSurveyor: can only update status
            updateClaim();
        }

        //2nd case: Create claim => If claim does NOT exist

        if (claim == null){}


        //If use is a policy holder and policy owner are the ones who can create claims

        if (user instanceof PolicyHolder || user instanceof PolicyOwner) {
            //read text fields => store to variables
            String bankAccountName = bankAccountNameField.getText();
            String bankAccountNumber = bankAccountNumberField.getText();
            String bankName = bankNameField.getText();
            String stringClaimAmount = claimAmountField.getText();
            //convert claimAmount to integer => if parsing fails => return error not an integer
            Integer claimAmount = Integer.parseInt(stringClaimAmount);
            String password = passwordField.getText();


            // input validator for Claim's Field

            InputValidator.validatingClaim(entityManager, claimAmount, bankName, bankAccountName, bankAccountNumber);
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

    private void uploadDocument(String s, File file) {
    }

    private boolean handleInput(){
        //get input

        //Validate input

    }



    private  void updateClaim() {
        // textField.setText = claim.getAttributes
    }
}
