package org.example.insurancemanagementapplication.Controller.DashBoardController.TableFillingController;

import Entity.ActionHistory;
import Entity.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.insurancemanagementapplication.Controller.RootController;
import org.example.insurancemanagementapplication.Interfaces.ActionHistoryCreate;
import org.example.insurancemanagementapplication.Interfaces.ActionHistoryRead;

import java.io.IOException;
import java.sql.Timestamp;

public class ActionHistoryTableFilling extends RootController {
    //This class is to de-serialize from file and display action history to user

    protected User user;
    private ObservableList<ActionHistory> actionHistoryListOfUser = FXCollections.observableArrayList();
    //Import necessary FXML
    //Table View and Columns
    //remember to include these ids and element in fxml file (use scene builder)
    @FXML
    protected TableView<ActionHistory> actionHistoryTable;
    @FXML
    protected TableColumn<ActionHistory, String> actionId;

    @FXML
    protected TableColumn<ActionHistory, String> actionDescription;
    @FXML
    protected TableColumn<ActionHistory, Timestamp> time;


    //constructor


    public ActionHistoryTableFilling(User user) {
        this.user = user;
    }


    //org.example.insurancemanagementapplication.main method to fill the actionHistoryTable

    public void fillingActionHistoryTable(User user) {
        //try to fill table
        //catch the exception if file not exist => create new action history file for user right here
        //=> not wasting time and memory to map table and entity even if no file exist

        try {
            //Mapping table columns to Entity Attributes
            actionId.setCellValueFactory(new PropertyValueFactory<ActionHistory, String>("actionId"));
            actionDescription.setCellValueFactory(new PropertyValueFactory<ActionHistory, String>("actionDescription"));
            time.setCellValueFactory(new PropertyValueFactory<ActionHistory, Timestamp>("time"));
            //fill the table with the Collection of Action History (different for each user)
            ActionHistoryRead.readFromActionHistoryFile(user.getId(), actionHistoryListOfUser);
            actionHistoryTable.setItems(actionHistoryListOfUser); //it requires observable list here, but we are passing a normal list


        } catch (IOException e) {
            ActionHistoryCreate.createActionHistoryFileIfEmpty(user.getId());
        }



    }

    public User getUser() {
        return user;
    }
}
