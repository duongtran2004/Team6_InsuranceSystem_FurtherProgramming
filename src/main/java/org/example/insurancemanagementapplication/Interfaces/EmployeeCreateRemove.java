package org.example.insurancemanagementapplication.Interfaces;

import Entity.InsuranceManager;
import Entity.InsuranceSurveyor;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import javafx.scene.control.Label;

import java.util.Random;

/**
 * @author Luong Thanh Trung
 * @version ${}
 * @created 27/04/2024 04:58
 * @project InsuranceManagementTeamProject
 */
public interface EmployeeCreateRemove {
    public static boolean createInsuranceManager(EntityManager entityManager, Label errorContainer, String fullName, String address, String phoneNumber, String email, String password, String passwordValidator){
        EntityTransaction transaction = entityManager.getTransaction();
        try{
            transaction.begin();
            if (!password.equals(passwordValidator)){
                errorContainer.setText("Passwords Do Not Match. Please Try Again");
                transaction.rollback();
                return false;
            }
            InsuranceManager insuranceManager = new InsuranceManager();
            insuranceManager.setFullName(fullName);
            insuranceManager.setPhoneNumber(phoneNumber);
            insuranceManager.setAddress(address);
            insuranceManager.setEmail(email);
            insuranceManager.setPassword(password);
            String id = "IM";
            Random random = new Random();
            for (int i = 0; i < 8; i++){
                id = id + random.nextInt(0, 10);
            }
            insuranceManager.setId(id);
            entityManager.persist(insuranceManager);
            transaction.commit();

        }
        finally {
            if (transaction.isActive()){
                transaction.rollback();
            }

        }
        return true;
    }

    public static boolean createInsuranceSurveyor(EntityManager entityManager, Label errorContainer, String fullName, String address, String phoneNumber, String email, String password, InsuranceManager insuranceManager, String passwordValidator){
        EntityTransaction transaction = entityManager.getTransaction();
        try{
            transaction.begin();
            if (!password.equals(passwordValidator)){
                errorContainer.setText("Passwords Do Not Match. Please Try Again");
                transaction.rollback();
                return false;
            }
            InsuranceSurveyor insuranceSurveyor = new InsuranceSurveyor();
            insuranceSurveyor.setFullName(fullName);
            insuranceSurveyor.setPhoneNumber(phoneNumber);
            insuranceSurveyor.setAddress(address);
            insuranceSurveyor.setEmail(email);
            insuranceSurveyor.setPassword(password);
            String id = "IM";
            Random random = new Random();
            for (int i = 0; i < 8; i++){
                id = id + random.nextInt(0, 10);
            }
            insuranceSurveyor.setId(id);
            insuranceSurveyor.setInsuranceManager(insuranceManager);
            entityManager.persist(insuranceSurveyor);
            transaction.commit();


        }
        finally {
            if (transaction.isActive()){
                transaction.rollback();
            }

        }
        return true;
    }
}
