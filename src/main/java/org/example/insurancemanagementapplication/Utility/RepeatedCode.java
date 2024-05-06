package org.example.insurancemanagementapplication.Utility;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.insurancemanagementapplication.Interfaces.Controller;
import org.example.insurancemanagementapplication.MainEntryPoint;

import java.io.IOException;
import java.util.Random;

/**
 * @author Luong Thanh Trung
 * @version ${}
 * @created 03/05/2024 05:25
 * @project InsuranceManagementTeamProject
 */
public class RepeatedCode {
    public static String idGenerate(String prefix){
        Random random = new Random();
        String id = prefix;
        for (int i = 0; i < 10; i++){
            id = id + random.nextInt(0, 10);
        }
        return id;
    }

    public static void showStage(Stage stage, Controller controller, String fxmlFile, String title)  {
        FXMLLoader fxmlLoader = new FXMLLoader(MainEntryPoint.class.getResource(fxmlFile));
        fxmlLoader.setController(controller);
        Scene scene;
        try {
            scene = new Scene(fxmlLoader.load());
            stage.setTitle("");
            stage.setScene(scene);
            stage.setTitle(title);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
