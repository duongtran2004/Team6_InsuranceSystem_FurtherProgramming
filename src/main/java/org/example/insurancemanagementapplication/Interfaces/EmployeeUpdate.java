package org.example.insurancemanagementapplication.Interfaces;

import Entity.InsuranceManager;
import Entity.InsuranceSurveyor;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import javafx.scene.control.Label;

/**
 * @author Luong Thanh Trung
 * @version ${}
 * @created 27/04/2024 04:58
 * @project InsuranceManagementTeamProject
 */
public interface EmployeeUpdate {
    static boolean updateInsuranceManager(EntityManager entityManager, InsuranceManager insuranceManager, Label errorContainer, String address, String phoneNumber, String email, String password, String passwordValidator){
        EntityTransaction transaction = entityManager.getTransaction();
        try{
            transaction.begin();
            if (!password.equals(passwordValidator)){
                errorContainer.setText("Passwords Do Not Match. Please Try Again");
                transaction.rollback();
                return false;
            }
            entityManager.persist(insuranceManager);
            insuranceManager.setAddress(address);
            insuranceManager.setPhoneNumber(phoneNumber);
            insuranceManager.setEmail(email);
            transaction.commit();


        }finally {
            if (transaction.isActive()){
                transaction.rollback();
            }
        }
        return true;
    }

    static boolean updateInsuranceSurveyor(boolean reassign, EntityManager entityManager, Label errorContainer, InsuranceSurveyor insuranceSurveyor, String managerId, String address, String phoneNumber, String email, String password, String passwordValidator){
        EntityTransaction transaction = entityManager.getTransaction();
        try{
            transaction.begin();
            if (!password.equals(passwordValidator)){
                errorContainer.setText("Passwords Do Not Match. Please Try Again");
                transaction.rollback();
                return false;
            }
            entityManager.persist(insuranceSurveyor);
            if (reassign){
                //Test Case: What happens if not insurance manager is found?
                InsuranceManager insuranceManager = entityManager.find(InsuranceManager.class, managerId);
                insuranceSurveyor.setInsuranceManager(insuranceManager);

            }
            insuranceSurveyor.setAddress(address);
            insuranceSurveyor.setPhoneNumber(phoneNumber);
            insuranceSurveyor.setEmail(email);
            insuranceSurveyor.setPassword(password);
            transaction.commit();

        } finally {
            if (transaction.isActive()){
                transaction.rollback();
            }
        }
        return true;

    }
}
