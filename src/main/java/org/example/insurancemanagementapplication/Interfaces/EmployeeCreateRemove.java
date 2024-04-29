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
    public static boolean createInsuranceManager(EntityManager entityManager, Label errorContainer, String fullName, String address, String phoneNumber, String email, String password, String passwordValidation){
        EntityTransaction transaction = entityManager.getTransaction();
        try{
            transaction.begin();
            InsuranceManager insuranceManager = new InsuranceManager();
            insuranceManager.setFullName(fullName);
            insuranceManager.setPhoneNumber(phoneNumber);
            insuranceManager.setAddress(address);
            insuranceManager.setEmail(email);
            if (password.equals(passwordValidation)){
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
            else {
                transaction.rollback();
                errorContainer.setText("Passwords Do not Match Please Try Again");
                return false;
            }

        }
        finally {
            if (transaction.isActive()){
                transaction.rollback();
            }

        }
        return true;
    }

    public static boolean createInsuranceSurveyor(EntityManager entityManager, Label errorContainer, String fullName, String address, String phoneNumber, String email, String password, InsuranceManager insuranceManager, String passwordValidation){
        EntityTransaction transaction = entityManager.getTransaction();
        try{
            transaction.begin();
            InsuranceSurveyor insuranceSurveyor = new InsuranceSurveyor();
            insuranceSurveyor.setFullName(fullName);
            insuranceSurveyor.setPhoneNumber(phoneNumber);
            insuranceSurveyor.setAddress(address);
            insuranceSurveyor.setEmail(email);
            if (password.equals(passwordValidation)){
                insuranceSurveyor.setPassword(password);
                String id = "IM";
                Random random = new Random();
                for (int i = 0; i < 8; i++){
                    id = id + random.nextInt(0, 10);
                }
                insuranceSurveyor.setId(id);
                entityManager.persist(insuranceSurveyor);
                transaction.commit();
            }
            else {
                transaction.rollback();
                errorContainer.setText("Passwords Do not Match. Please Try Again");
                return false;
            }

        }
        finally {
            if (transaction.isActive()){
                transaction.rollback();
            }

        }
        return true;
    }
}
