package org.example.insurancemanagementapplication.Controller;

import jakarta.persistence.EntityManager;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.example.insurancemanagementapplication.Interfaces.CustomerCreateRemove;
import org.example.insurancemanagementapplication.Interfaces.EmployeeCreateRemove;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Luong Thanh Trung
 * @version ${}
 * @created 27/04/2024 09:11
 * @project InsuranceManagementTeamProject
 */
public class TestingController implements Initializable, CustomerCreateRemove, EmployeeCreateRemove {
    private final EntityManager entityManager;

    public TestingController(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    @FXML
    private TextField fullNameField;
    @FXML
    private TextField addressField;
    @FXML
    private TextField phoneNumberField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField passwordField;
    @FXML
    private TextField passwordValidationField;
    @FXML
    private Button submitButton;
    @FXML
    private Label errorContainer;




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        submitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                createInsuranceManager();
            }
        });
    }

    public void createInsuranceManager(){
        if (passwordField.getText().equals(passwordValidationField.getText())){
            EmployeeCreateRemove.createInsuranceManager(entityManager, fullNameField.getText(), addressField.getText(), phoneNumberField.getText(), emailField.getText(), passwordField.getText());
        }
        else{
            errorContainer.setText("Password Validation Fails. Please make sure that the 2 passwords match");
        }


    }
}
