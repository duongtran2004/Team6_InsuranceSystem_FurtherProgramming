package org.example.insurancemanagementapplication.Controller;

import jakarta.persistence.EntityManager;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * @author Luong Thanh Trung
 * @version ${}
 * @created 27/04/2024 04:50
 * @project InsuranceManagementTeamProject
 */
public class MainController {
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

    private TextField useridField;
    private TextField emailField;
    private TextField passwordField;
    private ChoiceBox<String> roleSelectionBox;
    private Button loginButton;
    private Label errorContainer;


}
