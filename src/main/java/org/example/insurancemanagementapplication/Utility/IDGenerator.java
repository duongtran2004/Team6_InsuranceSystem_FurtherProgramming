package org.example.insurancemanagementapplication.Utility;

import java.util.Random;

public class IDGenerator {
    public static String generateId(String prefix){
        Random random = new Random();
        String id = prefix;
        for (int i = 0; i < 10; i++){
            id = id + random.nextInt(0, 10);
        }
        return id;
    }
}
