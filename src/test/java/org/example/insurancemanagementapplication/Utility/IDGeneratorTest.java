package org.example.insurancemanagementapplication.Utility;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IDGeneratorTest {

    @Test
    void generateId() {
        String newID =
        IDGenerator.generateId("IM");
        System.out.println(newID);
    }
}