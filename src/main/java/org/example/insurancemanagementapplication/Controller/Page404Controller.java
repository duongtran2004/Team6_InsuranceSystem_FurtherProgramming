package org.example.insurancemanagementapplication.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * name tri
 * Date 13/05/2024
 */
public class Page404Controller {

    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    ImageView image404;

    @FXML
    Button BackToLoginPageButton;

    public void switchToLoginScreen(javafx.event.ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("LogInPage.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }



}
