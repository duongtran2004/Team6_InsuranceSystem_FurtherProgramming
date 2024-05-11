package org.example.insurancemanagementapplication.Utility;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.insurancemanagementapplication.Interfaces.Controller;
import org.example.insurancemanagementapplication.MainEntryPoint;

import java.io.IOException;

public class StageBuilder {

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
