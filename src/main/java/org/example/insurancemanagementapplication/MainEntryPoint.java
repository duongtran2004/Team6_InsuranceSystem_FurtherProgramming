package org.example.insurancemanagementapplication;

import Entity.SystemAdmin;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.insurancemanagementapplication.Controller.DashBoardController.DashBoardController_SystemAdmin;

import java.io.IOException;

public class MainEntryPoint extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainEntryPoint.class.getResource("DashBoard_SystemAdmin.fxml"));
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        SystemAdmin systemAdmin = entityManager.find(SystemAdmin.class, "SA90987611");
        DashBoardController_SystemAdmin dashBoardControllerSystemAdmin = new DashBoardController_SystemAdmin(entityManager, systemAdmin);



        fxmlLoader.setController(dashBoardControllerSystemAdmin);
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();

    }
}