package org.example.insurancemanagementapplication.Interfaces;

import Entity.InsuranceManager;
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
    static boolean updateInsuranceManager(EntityManager entityManager, InsuranceManager insuranceManager, Label errorContainer, String address, String phoneNumber, String email, String password, String passwordValidation){
        EntityTransaction transaction = entityManager.getTransaction();
        try{
            transaction.begin();
            entityManager.persist(insuranceManager);
            insuranceManager.setAddress(address);
            insuranceManager.setPhoneNumber(phoneNumber);
            insuranceManager.setEmail(email);
            if (password.equals(passwordValidation)){
                insuranceManager.setPassword(password);
            }
            else {
                errorContainer.setText("Passwords Do not Match. Please Try Again");
                transaction.rollback();
                return false;
            }
            transaction.commit();


        }finally {
            if (transaction.isActive()){
                transaction.rollback();
            }
        }
        return true;
    }
}
