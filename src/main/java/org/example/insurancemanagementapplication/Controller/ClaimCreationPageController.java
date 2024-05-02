package org.example.insurancemanagementapplication.Controller;

import Entity.PolicyHolder;
import Entity.PolicyOwner;
import Entity.User;
import jakarta.persistence.EntityManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class ClaimCreationPageController implements Initializable {
    private EntityManager entityManager;
    private User user;
    private PolicyHolder policyHolder;
    private PolicyOwner policyOwner;

  
    //create first, update later
    @FXML
    private ChoiceBox<String> statusChoiceBox;
    @FXML
    private Button updloadDocumentButton;

    String[] Status = {"New", "Need Info", "Processing", "Approved", "Rejected"};
    // Initialize Supabase client and storage
    

           ;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //policy holder and policy owner are the ones who can create claims
        if (user instanceof PolicyHolder || user instanceof PolicyOwner) {

            statusChoiceBox.getItems().addAll(Status);


            updloadDocumentButton.setOnAction(event -> {
                // Handle button click event to upload document
                uploadDocument("public/avatar1.png", new File("path/to/your/file.png"));
            });
        }


    }

    private void uploadDocument(String s, File file) {
    }
}
