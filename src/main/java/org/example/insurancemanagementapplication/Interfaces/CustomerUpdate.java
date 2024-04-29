package org.example.insurancemanagementapplication.Interfaces;

import Entity.Dependant;
import Entity.PolicyHolder;
import Entity.PolicyOwner;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import javafx.scene.control.Label;

/**
 * @author Luong Thanh Trung
 * @version ${}
 * @created 27/04/2024 04:57
 * @project InsuranceManagementTeamProject
 */
public interface CustomerUpdate {
    static boolean updatePolicyOwner(EntityManager entityManager, PolicyOwner policyOwner, Label errorContainer, String address, String phoneNumber, String email, String password, String passwordValidator){
        EntityTransaction transaction = entityManager.getTransaction();
        try{
            transaction.begin();
            if (!password.equals(passwordValidator)){
                errorContainer.setText("Passwords Do Not Match. Please Try Again");
                transaction.rollback();
                return false;
            }
            entityManager.persist(policyOwner);
            policyOwner.setAddress(address);
            policyOwner.setPhoneNumber(phoneNumber);
            policyOwner.setEmail(email);
            transaction.commit();


        }finally {
            if (transaction.isActive()){
                transaction.rollback();
            }
        }
        return true;
    }

    static boolean updatePolicyHolder(EntityManager entityManager, PolicyHolder policyHolder, Label errorContainer, String address, String phoneNumber, String email, String password, String passwordValidator){
        EntityTransaction transaction = entityManager.getTransaction();
        try{
            transaction.begin();
            if (!password.equals(passwordValidator)){
                errorContainer.setText("Passwords Do Not Match. Please Try Again");
                transaction.rollback();
                return false;
            }
            entityManager.persist(policyHolder);
            policyHolder.setAddress(address);
            policyHolder.setPhoneNumber(phoneNumber);
            policyHolder.setEmail(email);
            transaction.commit();


        }finally {
            if (transaction.isActive()){
                transaction.rollback();
            }
        }
        return true;
    }

    static boolean updateDependant(EntityManager entityManager, Dependant dependant, Label errorContainer, String address, String phoneNumber, String email, String password, String passwordValidator){
        EntityTransaction transaction = entityManager.getTransaction();
        try{
            transaction.begin();
            if (!password.equals(passwordValidator)){
                errorContainer.setText("Passwords Do Not Match. Please Try Again");
                transaction.rollback();
                return false;
            }
            entityManager.persist(dependant);
            dependant.setAddress(address);
            dependant.setPhoneNumber(phoneNumber);
            dependant.setEmail(email);
            transaction.commit();


        }finally {
            if (transaction.isActive()){
                transaction.rollback();
            }
        }
        return true;
    }
}
