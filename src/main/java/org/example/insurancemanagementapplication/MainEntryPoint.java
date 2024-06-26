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
    @Override
    public void start(Stage stage) throws IOException {

        //run different fxml files for testing
        FXMLLoader fxmlLoader = new FXMLLoader(MainEntryPoint.class.getResource("LogInPage.fxml"));
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        LogInPageController logInPageController = new LogInPageController(entityManager);
        fxmlLoader.setController(logInPageController);



//        //Load Policy Owner Dashboard
//        FXMLLoader fxmlLoader = new FXMLLoader(MainEntryPoint.class.getResource("PolicyOwnerDashBoard.fxml"));
//
//        PolicyOwner policyOwner = entityManager.find(PolicyOwner.class, "PO6173753721");
//
//        PolicyOwnerDashBoardController dashBoardController_policyOwner = new PolicyOwnerDashBoardController(policyOwner,entityManager);
//        fxmlLoader.setController(dashBoardController_policyOwner);




//                //Load Policy Holder Dashboard
//                FXMLLoader fxmlLoader = new FXMLLoader(MainEntryPoint.class.getResource("PolicyHolderDashBoard.fxml"));
//
//
//        PolicyHolder policyHolder = entityManager.find(PolicyHolder.class, "PH5264972101");
//
//
//        PolicyHolderDashBoardController dashBoardController_policyHolder = new PolicyHolderDashBoardController(policyHolder,entityManager);
//        fxmlLoader.setController(dashBoardController_policyHolder);





        //Load Dependent Dashboard
//        FXMLLoader fxmlLoader = new FXMLLoader(MainEntryPoint.class.getResource("DependantDashBoardController"));
//
//
//
//        Dependant dependant = entityManager.find(Dependant.class, "DE9791594810");
//
//        DependantDashBoardController dashBoardController_dependant = new DependantDashBoardController(dependant, entityManager);
//        fxmlLoader.setController(dashBoardController_dependant);





        //set controller

        //Load scenes (for all fxml pages)
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Log in");
// Set screen size
        stage.setWidth(800); // Set width to 800 pixels
        stage.setHeight(600); // Set height to 600 pixels
        stage.setScene(scene);
        stage.setOnCloseRequest(windowEvent -> {
            entityManager.close();
            entityManagerFactory.close();
        });
        stage.show();
    }

    public static void launchApp(){
        launch();
    }


}