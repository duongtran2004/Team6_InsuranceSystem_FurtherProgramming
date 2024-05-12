package org.example.insurancemanagementapplication.Interfaces;

import Entity.ActionHistory;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public interface ActionHistoryRead {

    //This class is to read file using de-serialization, then create Action History Object, then store as Collection to be used in ActionHistoryTableFilling Class
    //Store Action History objects into Collections (like List)
    static List<ActionHistory> readFromActionHistoryFile(String userId, ObservableList<ActionHistory> actionHistories) throws IOException {
        String fileName = userId + "_action_history.txt";

        String filePath = "src/main/resources/org/example/insurancemanagementapplication/ActionHistoryFiles" + "/" + fileName;
        List<ActionHistory> actionHistoryList = new ArrayList<>();

        try (FileInputStream fileInputStream = new FileInputStream(filePath);
             ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {

            // Read objects until EOFException is caught
            while (true) {
                try {
                    // Deserialize and read the ActionHistory object from the file
                    ActionHistory actionHistory = (ActionHistory) objectInputStream.readObject();
                    actionHistories.add(actionHistory);
                } catch (EOFException e) {
                    // End of stream reached, no more object to read
                    break;
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
            System.out.println("Action history read from file for user: " + userId);
        }
        return actionHistoryList;
    }




}


