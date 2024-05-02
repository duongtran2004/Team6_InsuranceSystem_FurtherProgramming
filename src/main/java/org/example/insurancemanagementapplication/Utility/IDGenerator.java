package org.example.insurancemanagementapplication.Utility;

import java.util.Random;

/**
 * @author Luong Thanh Trung
 * @version ${}
 * @created 03/05/2024 05:25
 * @project InsuranceManagementTeamProject
 */
public class IDGenerator {
    public static String idGenerate(String prefix){
        Random random = new Random();
        String id = prefix;
        for (int i = 0; i < 10; i++){
            id = id + random.nextInt(0, 10);
        }
        return id;
    }
}
