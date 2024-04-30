package org.example.insurancemanagementapplication;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.insurancemanagementapplication.Controller.LogInPageController;

import java.io.IOException;

public class MainEntryPoint extends Application {
    public static EntityManager entityManager;
    @Override
    public void start(Stage stage) throws IOException {
        System.out.println("Hello Word");
        FXMLLoader fxmlLoader = new FXMLLoader(MainEntryPoint.class.getResource("LogInPage.fxml"));
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        fxmlLoader.setController(new LogInPageController(entityManager));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();

    }
}