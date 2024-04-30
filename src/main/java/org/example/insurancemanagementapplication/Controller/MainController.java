package org.example.insurancemanagementapplication.Controller;

import Entity.SystemAdmin;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.insurancemanagementapplication.HelloApplication;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Luong Thanh Trung
 * @version ${}
 * @created 27/04/2024 04:50
 * @project InsuranceManagementTeamProject
 */
public class MainController implements Initializable {
    EntityManager entityManager;

    public MainController(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    @FXML
    private TextField userIdField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField passwordField;
    @FXML
    private ChoiceBox<String> roleSelectionBox;
    @FXML
    private Button logInButton;
    @FXML
    private Label errorContainer;
    private String[] roles = {"System Admin", "Insurance Manager", "Insurance Surveyor", "Policy Owner", "Policy Holder", "Dependant"};


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Hello World");
        roleSelectionBox.getItems().addAll(roles);
        logInButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String email = emailField.getText();
                String userId = userIdField.getText();
                String password = passwordField.getText();
                String role = roleSelectionBox.getValue();
                EntityTransaction transaction = entityManager.getTransaction();
                try{
                    transaction.begin();
                    Stage stage = (Stage) logInButton.getScene().getWindow();
                    logInButton.setOnAction(event1 -> {
                        if(role.equalsIgnoreCase("system admin")){
                            SystemAdmin user = (SystemAdmin) entityManager.createQuery("SELECT o FROM SystemAdmin o WHERE o.id LIKE ?1 AND o.email LIKE ?2 AND o.password LIKE ?3 ").setParameter(1, userId).setParameter(2, email).setParameter(3, password).getSingleResult();
                            DashBoardController_SystemAdmin dashBoardControllerSystemAdmin = new DashBoardController_SystemAdmin(entityManager, user);
                            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("DashBoard_SystemAdmin.fxml"));
                            fxmlLoader.setController(dashBoardControllerSystemAdmin);
                            Scene scene = null;
                            try {
                                scene = new Scene(fxmlLoader.load());
                                stage.setTitle("Hello!");
                                stage.setScene(scene);
                                stage.show();
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            stage.setTitle("Hello!");
                            stage.setScene(scene);
                            stage.show();
                        }
                    });

                } finally {
                    transaction.rollback();
                }

            }
        });

    }

    public void logIn(){
        String email = emailField.getText();
        String userId = userIdField.getText();
        String password = passwordField.getText();
        String role = roleSelectionBox.getValue();
        EntityTransaction transaction = entityManager.getTransaction();
        System.out.println(email);
        /**
        try{
            transaction.begin();
        }finally {
            if (transaction.isActive()){
                transaction.rollback();
            }
        }
         **/
    }

}
