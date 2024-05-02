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

    String[] Status = {"New", "Need Info", "Processing", "Approved", "Rejected"};
    // Initialize Supabase client and storage
    

           ;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //1st case: Update Claim => If claim exist

        if (claim != null) {
            updateClaim();
        }

        //2nd case: Create claim => If claim does NOT exist

        if (claim == null){}


        //policy holder and policy owner are the ones who can create claims
        // 2nd: InsuranceManager OR InsuranceSurveyor:
        if (user instanceof PolicyHolder || user instanceof PolicyOwner) {
            //read text fields => pass
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
