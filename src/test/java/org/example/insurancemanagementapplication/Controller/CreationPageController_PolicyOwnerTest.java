package org.example.insurancemanagementapplication.Controller;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class CreationPageController_PolicyOwnerTest  {

    @Test
    void initialize() throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(org.example.insurancemanagementapplication.MainEntryPoint.class.getResource("DashBoard_PolicyOwner.fxml")); //fxml file name here
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager(); //create entity manager object


        fxmlLoader.setController(new DashBoardController_PolicyOwner(entityManager)); // remember to set different Controller for different fxml file
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();

    }


}




