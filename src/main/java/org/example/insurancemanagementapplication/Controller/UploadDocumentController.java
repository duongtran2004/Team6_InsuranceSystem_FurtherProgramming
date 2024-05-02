//package org.example.insurancemanagementapplication.Controller;
//
//
//
//import io.supabase.client.SupabaseClient;
//import io.supabase.client.SupabaseClientBuilder;
//import io.supabase.storage.StorageBucket;
//import io.supabase.storage.StorageFile;
//import javafx.fxml.FXML;
//import javafx.fxml.Initializable;
//import javafx.scene.control.Button;
//import javafx.stage.FileChooser;
//
//import java.io.File;
//import java.net.URL;
//import java.util.ResourceBundle;
//
//public class UploadDocumentController implements Initializable {
//
//    @FXML
//    private Button uploadButton;
//
//    @Override
//    public void initialize(URL location, ResourceBundle resources) {
//        uploadButton.setOnAction(event -> {
//            // Create a Supabase client
//            SupabaseClient client = new SupabaseClientBuilder("your-supabase-url", "your-supabase-key").build();
//
//            // Choose a file to upload
//            FileChooser fileChooser = new FileChooser();
//            File selectedFile = fileChooser.showOpenDialog(null);
//            if (selectedFile != null) {
//                // Get the name of the selected file
//                String fileName = selectedFile.getName();
//
//                // Create a storage bucket
//                StorageBucket storage = client.storage().from("your-storage-bucket");
//
//                // Upload the file
//                StorageFile file = storage.upload(fileName, selectedFile);
//                if (file != null) {
//                    System.out.println("File uploaded successfully: " + file.getUrl());
//                } else {
//                    System.out.println("Failed to upload file.");
//                }
//            }
//        });
//    }
//}
//
