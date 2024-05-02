package org.example.insurancemanagementapplication.Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

public class ViewClaimDocumentController implements Initializable {

    //dummy Button for testing,
    @FXML
    Button viewClaimDocumentButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        viewClaimDocumentButton.setOnAction(event -> {
            // Replace this with your logic to retrieve the document URL from your database
            String documentName = "Cannot load fxml.pdf"; // Example document name => replace it with getDocumentName to read the cell's data

            // Format the document name to replace spaces with "%20"
            String formattedDocumentName = documentName.replaceAll(" ", "%20");

            // Construct the full URL to the document hosted on Supabase
            String supabaseUrl = "https://lfmakwarfqeintgjcseo.supabase.co/storage/v1/object/public/CustomerDocuments/";
            String documentUrl = supabaseUrl + formattedDocumentName;

            // Open the document URL in the user's default browser
            openUrlInBrowser(documentUrl);
        });

    }

    // Method to open a URL in the default browser
    private void openUrlInBrowser(String url) {
        try {
            Desktop.getDesktop().browse(new URI(url));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
            // Handle exception (e.g., log error, display error message to user)
        }
    }
}
