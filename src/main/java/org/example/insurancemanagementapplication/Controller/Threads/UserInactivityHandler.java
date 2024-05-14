package org.example.insurancemanagementapplication.Controller.Threads;

import Entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.example.insurancemanagementapplication.Controller.DashBoardController.TableFillingController.ActionHistoryTableFilling;
import org.example.insurancemanagementapplication.Controller.LogInPageController;
import org.example.insurancemanagementapplication.Utility.StageBuilder;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class UserInactivityHandler extends ActionHistoryTableFilling {
    private static UserInactivityHandler instance; //Singleton Design Pattern for global access point
    private Timer refreshCountDownTimer;
    private Timer AFKCountDownTimer;
    private List<Button> buttonList;


    public UserInactivityHandler(User user, Timer refreshCountDownTimer, Timer AFKCountDownTimer, List<Button> buttonList) {
        super(user);
        this.refreshCountDownTimer = refreshCountDownTimer;
        this.AFKCountDownTimer = AFKCountDownTimer;
        this.buttonList = buttonList;
    }


    public static UserInactivityHandler getInstance() {
        if (instance == null) {
            throw new IllegalStateException("UserInactivityHandler must be initialized before accessing the instance.");
        }
        return instance;
    }

    public void initialize(List<Button> buttonList) {
        this.buttonList = buttonList;

    }

    public void startRefreshCountDown() {
        // Check if buttonList  is properly initialized
        if (buttonList == null || buttonList.isEmpty()) {
            throw new IllegalStateException("Button list and log out callback must be initialized before starting countdown.");
        }
        TimerTask pauseUserToForceRefresh = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    for (Button button : buttonList) {
                        button.setDisable(true);

                    }
                    // Show Refresh Alert
                    Alert refreshAlert = new Alert(Alert.AlertType.INFORMATION);
                    refreshAlert.setTitle("Refresh Alert");
                    refreshAlert.setHeaderText(null);
                    refreshAlert.setContentText("Please click on the refresh button to reload the dashboard and continue using other features. Otherwise you will be logout in next 15 minutes.");
                    // Set the size of the dialog window
                    Stage stage = (Stage) refreshAlert.getDialogPane().getScene().getWindow();
                    stage.setWidth(700); // Set the width
                    stage.setHeight(400); // Set the height
                    refreshAlert.show();
                    refreshCountDownTimer.cancel();

                    startAFKCountDown();
                });
            }
        };
        refreshCountDownTimer.schedule(pauseUserToForceRefresh, 120 * 10 * 1000); //120 seconds = 2 minutes
//        refreshCountDownTimer.schedule(pauseUserToForceRefresh,  10 * 1000); //10 seconds just for testing
    }

    private void startAFKCountDown() {
        TimerTask forceLogOut = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    if (!buttonList.isEmpty()) {
                        Button firstButton = buttonList.get(0);
                        user = null;
                        Stage stage = (Stage) firstButton.getScene().getWindow();
                        //create entity manager
                        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
                        EntityManager localEntityManager = entityManagerFactory.createEntityManager();
                        StageBuilder.showStage(stage, new LogInPageController(localEntityManager), "LogInPage.fxml", "Login Page");
                        entityManagerFactory.close();
                        localEntityManager.close();
                        cancelTimers();
                        // Show Logout Alert
                        Alert logoutAlert = new Alert(Alert.AlertType.INFORMATION);
                        logoutAlert.setTitle("Logout Alert");
                        logoutAlert.setHeaderText(null);
                        logoutAlert.setContentText("You have been logged out due to inactivity.");
                        AFKCountDownTimer.cancel();
                        logoutAlert.show();

                    } else {
                        System.err.println("Button list is empty. Cannot log out.");
                    }
                });
            }
        };
        AFKCountDownTimer.schedule(forceLogOut, 900 * 10 * 1000); // 900 seconds = 15 min

//        AFKCountDownTimer.schedule(forceLogOut,   10 * 1000); // 10 seconds just for testing
    }

    public void cancelTimers() {
        if (refreshCountDownTimer != null) {
            refreshCountDownTimer.cancel();
        }
        if (AFKCountDownTimer != null) {
            AFKCountDownTimer.cancel();
        }
    }
}
