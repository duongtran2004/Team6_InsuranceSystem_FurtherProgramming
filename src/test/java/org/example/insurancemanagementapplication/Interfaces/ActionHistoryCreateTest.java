package org.example.insurancemanagementapplication.Interfaces;

import Entity.ActionHistory;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;

class ActionHistoryCreateTest {

    @Test
    void writeToActionHistoryObjectToFile() {
        // Create a Timestamp object for the current time
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        ActionHistory actionHistory = new ActionHistory("I663", "Hello", currentTime);
        ActionHistoryCreate.writeToActionHistoryObjectToFile("SA90987611", actionHistory);
    }
}