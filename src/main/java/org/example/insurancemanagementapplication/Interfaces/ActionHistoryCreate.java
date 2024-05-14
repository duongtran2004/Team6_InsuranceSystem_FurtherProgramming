package org.example.insurancemanagementapplication.Interfaces;

import Entity.ActionHistory;
import org.example.insurancemanagementapplication.Utility.IDGenerator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.Timestamp;

public interface ActionHistoryCreate {

    //This class is to create ActionHistory Objects, then write to file using Serialization

    static void createActionHistoryFileIfEmpty(String userId)  {
        // Define the file name based on the userId
        String fileName = userId + "_action_history.txt";
        String filePath = "src/main/resources/org/example/insurancemanagementapplication/ActionHistoryFiles" + "\\" + fileName;


        // Create a File object with the file path
        File file = new File(filePath);

        // Check if the file exists
        if (!file.exists()) { //how to: the system cannot find the path specifie
            try {
                // Create a new file
                if (file.createNewFile()) {
                    System.out.println("New action history file created for user: " + userId);
                } else {
                    System.out.println("Failed to create action history file for user: " + userId);
                }
            } catch (IOException e) {
                // Handle IOException
                e.printStackTrace();
            }
        } else {
            System.out.println("Action history file already exists for user: " + userId);
        }
    }


//event handler for submit button at CreationAndUpdatePage controllers + Delete Button at DashBoardController to change the "action" String

    //this method aim to create action history object
    static ActionHistory createActionHistoryObject(String action, String modifiedTable, String modifiedObjectID) {
        //ActionHistory's attributes
        String actionId = IDGenerator.generateId("A");
        String actionDescription = "";
        Timestamp time = new Timestamp(System.currentTimeMillis()); //current moment

        if (action.equals("UPDATE")) {
            actionDescription = "update " + modifiedTable + " with ID "+ modifiedObjectID ;

        }
        if (action.equals("CREATE")) {
            actionDescription = "create " + modifiedTable + " with ID " + modifiedObjectID;

        }
        if (action.equals("DELETE")) {
            actionDescription = "delete " + modifiedTable + " with ID " + modifiedObjectID;

        }
        ActionHistory actionHistory = new ActionHistory(actionId, actionDescription, time);
        return actionHistory;
    }

    static void writeToActionHistoryObjectToFile(String userId, ActionHistory actionHistory) {// using Serialization Object Input Stream and File Input Stream
        String fileName = userId + "_action_history.txt";

        String filePath = "src/main/resources/org/example/insurancemanagementapplication/ActionHistoryFiles" + "/" + fileName;
//String filePath = "ActionHistoryFiles" + "\\" + fileName;
        // Create a File object with the file path
        File file = new File(filePath);
        //check condition if file is empty
        if (file.length() == 0) {
            try {
                ObjectOutputStream emptyFileStream = new ObjectOutputStream(new FileOutputStream(filePath));
                emptyFileStream.writeObject(actionHistory);
                emptyFileStream.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        } else {
            try {
                ObjectOutputStream nonEmptyFileStream = new ObjectOutputStream(new FileOutputStream(filePath, true)) {
                    protected void writeStreamHeader() throws IOException {
                        reset();
                    }
                };
                nonEmptyFileStream.writeObject(actionHistory);
                nonEmptyFileStream.close();

            } catch (IOException e) {
                System.out.println(e.getMessage());
            }

        }

    }
}



