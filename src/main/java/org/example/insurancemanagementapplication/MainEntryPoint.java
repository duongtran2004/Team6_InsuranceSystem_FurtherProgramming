package org.example.insurancemanagementapplication;

import Entity.SystemAdmin;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.insurancemanagementapplication.Controller.DashBoardController.SystemAdminDashBoardController;

import java.io.IOException;

public class MainEntryPoint extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        //run different fxml files for testing
        System.out.println("Hello After Before Manager");
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        SystemAdmin systemAdmin = entityManager.find(SystemAdmin.class, "SA90987611");

//        //Load System Admin DashBoard
//        FXMLLoader fxmlLoader = new FXMLLoader(MainEntryPoint.class.getResource("SystemAdminDashBoard.fxml"));
//        SystemAdmin systemAdmin = entityManager.find(SystemAdmin.class, "SA90987611");
//        SystemAdminDashBoardController dashBoardControllerSystemAdmin = new SystemAdminDashBoardController(entityManager, systemAdmin);
//        fxmlLoader.setController(dashBoardControllerSystemAdmin);


        //Load login page
        System.out.println("Hello After Entity Manager");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SystemAdminDashBoard.fxml"));
//        LogInPageController logInPageController = new LogInPageController(entityManager);
        SystemAdminDashBoardController systemAdminDashBoardController = new SystemAdminDashBoardController(entityManager, systemAdmin);
        fxmlLoader.setController(systemAdminDashBoardController);




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
        stage.setTitle("Hello!");
// Set screen size
        stage.setWidth(800); // Set width to 800 pixels
        stage.setHeight(600); // Set height to 600 pixels
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();

    }
}