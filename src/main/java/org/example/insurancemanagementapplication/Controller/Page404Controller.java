package org.example.insurancemanagementapplication.Controller;

import Entity.User;
import jakarta.persistence.EntityManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import org.example.insurancemanagementapplication.Interfaces.Controller;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * name tri
 * Date 13/05/2024
 */
public class Page404Controller extends RootController implements Controller, Initializable {

    @FXML
    Button backToLoginPageButton;

    public Page404Controller(User user, EntityManager entityManager) {
        super(user, entityManager);
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        backToLoginPageButton.setOnAction(event -> {
            returnToDashBoard(user, entityManager, backToLoginPageButton);
        });

    }
}
