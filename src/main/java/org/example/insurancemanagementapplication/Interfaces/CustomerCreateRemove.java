package org.example.insurancemanagementapplication.Interfaces;

import Entity.Dependant;
import Entity.PolicyHolder;
import Entity.PolicyOwner;
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
public interface CustomerCreateRemove {
    public static boolean createPolicyOwner(EntityManager entityManager, Label errorContainer,  String fullName, String address, String phoneNumber, String email, String password, String passwordValidator){
        EntityTransaction transaction = entityManager.getTransaction();
        try{
            transaction.begin();
            if (!password.equals(passwordValidator)){
                errorContainer.setText("Passwords Do Not Match. Please Try Again");
                transaction.rollback();
                return false;
            }
            PolicyOwner policyOwner = new PolicyOwner();
            policyOwner.setFullName(fullName);
            policyOwner.setPhoneNumber(phoneNumber);
            policyOwner.setAddress(address);
            policyOwner.setEmail(email);
            policyOwner.setPassword(password);
            String id = "PO";
            Random random = new Random();
            for (int i = 0; i < 8; i++){
                id = id + random.nextInt(0, 10);
            }
            policyOwner.setId(id);
            entityManager.persist(policyOwner);
            transaction.commit();


        }
        finally {
            if (transaction.isActive()){
                transaction.rollback();
            }
        }
        return true;
    }
    public static boolean createPolicyHolder(EntityManager entityManager, Label errorContainer, String fullName, String address, String phoneNumber, String email, String password, String passwordValidator, PolicyOwner policyOwner){
        EntityTransaction transaction = entityManager.getTransaction();
        try{
            transaction.begin();
            if (!password.equals(passwordValidator)){
                errorContainer.setText("Passwords Do Not Match. Please Try Again");
                transaction.rollback();
                return false;
            }
            PolicyHolder policyHolder = new PolicyHolder();
            policyHolder.setFullName(fullName);
            policyHolder.setPhoneNumber(phoneNumber);
            policyHolder.setAddress(address);
            policyHolder.setEmail(email);
            policyHolder.setPassword(password);
            String id = "PH";
            Random random = new Random();
            for (int i = 0; i < 8; i++){
                id = id + random.nextInt(0, 10);
            }
            policyHolder.setId(id);
            policyHolder.setPolicyOwner(policyOwner);
            entityManager.persist(policyHolder);
            transaction.commit();
        }
        finally {
            if (transaction.isActive()){
                transaction.rollback();
            }
        }
        return true;
    }

    public static boolean createDependant(EntityManager entityManager, Label errorContainer, String fullName, String address, String phoneNumber, String email, String password, String passwordValidator, PolicyHolder policyHolder){
        EntityTransaction transaction = entityManager.getTransaction();
        try{
            transaction.begin();
            if (!password.equals(passwordValidator)){
                errorContainer.setText("Passwords Do Not Match. Please Try Again");
                transaction.rollback();
                return false;
            }
            Dependant dependant = new Dependant();
            dependant.setFullName(fullName);
            dependant.setPhoneNumber(phoneNumber);
            dependant.setAddress(address);
            dependant.setEmail(email);
            dependant.setPassword(password);
            String id = "DE";
            Random random = new Random();
            for (int i = 0; i < 8; i++){
                id = id + random.nextInt(0, 10);
            }
            dependant.setId(id);
            dependant.setPolicyHolder(policyHolder);
            dependant.setPolicyOwner(policyHolder.getPolicyOwner());
            entityManager.persist(dependant);
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
