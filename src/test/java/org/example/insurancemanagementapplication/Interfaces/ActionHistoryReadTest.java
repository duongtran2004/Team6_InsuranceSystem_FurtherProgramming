package org.example.insurancemanagementapplication.Interfaces;

import Entity.ActionHistory;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ActionHistoryReadTest {

    @Test
    void readFromActionHistoryFile() throws IOException {
        List<ActionHistory> actionHistoryList =
        ActionHistoryRead.readFromActionHistoryFile("SA90987611");
        System.out.println(actionHistoryList);
    }
}