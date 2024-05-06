package org.example.insurancemanagementapplication;

import Entity.PolicyOwner;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.insurancemanagementapplication.Controller.DashBoardController.DashBoardController_PolicyOwner;

import java.io.IOException;

public class MainEntryPoint extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        //run different fxml files for testing
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        //Load System Admin DashBoard
//        FXMLLoader fxmlLoader = new FXMLLoader(MainEntryPoint.class.getResource("DashBoard_SystemAdmin.fxml"));
//        SystemAdmin systemAdmin = entityManager.find(SystemAdmin.class, "SA90987611");
//        DashBoardController_SystemAdmin dashBoardControllerSystemAdmin = new DashBoardController_SystemAdmin(entityManager, systemAdmin);
//        fxmlLoader.setController(dashBoardControllerSystemAdmin);

//
//        //Load login page
//        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("LogInPage.fxml"));
//        LogInPageController logInPageController = new LogInPageController(entityManager);
//        fxmlLoader.setController(logInPageController);




        //Load Policy Owner Dashboard
        FXMLLoader fxmlLoader = new FXMLLoader(MainEntryPoint.class.getResource("DashBoard_PolicyOwner.fxml"));

        PolicyOwner policyOwner = entityManager.find(PolicyOwner.class, "PO6173753721");

        DashBoardController_PolicyOwner dashBoardController_policyOwner = new DashBoardController_PolicyOwner(policyOwner,entityManager);
        fxmlLoader.setController(dashBoardController_policyOwner);

//                //Load Policy Holder Dashboard
//                FXMLLoader fxmlLoader = new FXMLLoader(MainEntryPoint.class.getResource("DashBoard_PolicyHolder.fxml"));
//
//
//        PolicyHolder policyHolder = entityManager.find(PolicyHolder.class, "PH5264972101");
//
//
//        DashBoardController_PolicyHolder dashBoardController_policyHolder = new DashBoardController_PolicyHolder(policyHolder,entityManager);
//        fxmlLoader.setController(dashBoardController_policyHolder);

//        //Load Dependent Dashboard
//        FXMLLoader fxmlLoader = new FXMLLoader(MainEntryPoint.class.getResource("DashBoard_InsuranceSurveyor.fxml"));
//
//
//        PolicyHolder policyHolder = entityManager.find(PolicyHolder.class, "PH5264972101");
//
//
//        DashBoardController_PolicyHolder dashBoardController_policyHolder = new DashBoardController_PolicyHolder(policyHolder,entityManager);
//        fxmlLoader.setController(dashBoardController_policyHolder);





        //set controller

        //Load scenes (for all fxml pages)
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();

    }
}