package org.example.insurancemanagementapplication.Interfaces;

import Entity.Dependant;
import Entity.InsuranceCard;
import Entity.PolicyHolder;
import Entity.PolicyOwner;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

/**
 * @author Luong Thanh Trung
 * @version ${}
 * @created 27/04/2024 04:57
 * @project InsuranceManagementTeamProject
 */
public interface CustomerUpdate {
    static boolean updatePolicyOwner(EntityManager entityManager, PolicyOwner policyOwner, String address, String phoneNumber, String email, String password){
        EntityTransaction transaction = entityManager.getTransaction();
        try{
            transaction.begin();
       entityManager.persist(policyOwner);
            policyOwner.setAddress(address);
            policyOwner.setPhoneNumber(phoneNumber);
            policyOwner.setEmail(email);
            policyOwner.setPassword(password);
            transaction.commit();


        }finally {
            if (transaction.isActive()){
                transaction.rollback();
            }
        }
        return true;
    }

    static boolean updatePolicyHolder(EntityManager entityManager, PolicyHolder policyHolder, String address, String phoneNumber, String email, String password){
        EntityTransaction transaction = entityManager.getTransaction();
        try{
            transaction.begin();
            entityManager.persist(policyHolder);
            policyHolder.setAddress(address);
            policyHolder.setPhoneNumber(phoneNumber);
            policyHolder.setEmail(email);
            policyHolder.setPassword(password);
            transaction.commit();


        }finally {
            if (transaction.isActive()){
                transaction.rollback();
            }
        }
        return true;
    }

    static boolean updateDependant(EntityManager entityManager, Dependant dependant, String address, String phoneNumber, String email, String password){
        EntityTransaction transaction = entityManager.getTransaction();
        try{
            transaction.begin();
            entityManager.persist(dependant);
            dependant.setAddress(address);
            dependant.setPhoneNumber(phoneNumber);
            dependant.setEmail(email);
            dependant.setPassword(password);
            transaction.commit();


        }finally {
            if (transaction.isActive()){
                transaction.rollback();
            }
        }
        return true;
    }

    static boolean updatePolicyHolder(EntityManager entityManager, PolicyHolder policyHolder, InsuranceCard newCard){
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(policyHolder);
            policyHolder.setInsuranceCard(newCard);
            transaction.commit();
        } finally {
            if (transaction.isActive()){
                transaction.rollback();
            }
        }
        return true;
    }

    static boolean updateDependant(EntityManager entityManager, Dependant dependant, InsuranceCard newCard){
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(dependant);
            dependant.setInsuranceCard(newCard);
            transaction.commit();
        } finally {
            if (transaction.isActive()){
                transaction.rollback();
            }
        }
        return true;
    }
}
