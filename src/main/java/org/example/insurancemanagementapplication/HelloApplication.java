package org.example.insurancemanagementapplication;

import Entity.SystemAdmin;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(""));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try{
            transaction.begin();
            SystemAdmin systemAdmin = new SystemAdmin();
            systemAdmin.setId("SA9252678");
            systemAdmin.setFullName("Tran Ba Dao");
            systemAdmin.setPassword("Lacussaber123");
            systemAdmin.setEmail("trungluong0806@gmail.com");
            systemAdmin.setPhoneNumber("0909154614");
            systemAdmin.setAddress("819 Swanston street Carlton 3053 VIC");
            entityManager.persist(systemAdmin);

            transaction.commit();
        } finally {
            if (transaction.isActive()){
                transaction.rollback();
            }
            entityManager.close();
            entityManagerFactory.close();
        }

    }
}