package org.example.insurancemanagementapplication;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.insurancemanagementapplication.Controller.DashBoardController_PolicyOwner;

import java.io.IOException;

public class MainEntryPoint extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainEntryPoint.class.getResource("DashBoard_PolicyOwner.fxml")); //fxml file name here
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager(); //create entity manager object


        fxmlLoader.setController(new DashBoardController_PolicyOwner(entityManager)); // remember to set different Controller for different fxml file
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();

    }
}