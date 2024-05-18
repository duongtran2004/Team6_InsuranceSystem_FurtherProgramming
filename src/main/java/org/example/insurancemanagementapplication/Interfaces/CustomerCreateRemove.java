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
 * @created 27/04/2024 04:58
 * @project InsuranceManagementTeamProject
 */
public interface CustomerCreateRemove {
    public static boolean createPolicyOwner(EntityManager entityManager, String id, String fullName, String address, String phoneNumber, String email, String password){
        EntityTransaction transaction = entityManager.getTransaction();
        try{
            transaction.begin();
            PolicyOwner policyOwner = new PolicyOwner();
            policyOwner.setFullName(fullName);
            policyOwner.setPhoneNumber(phoneNumber);
            policyOwner.setAddress(address);
            policyOwner.setEmail(email);
            policyOwner.setPassword(password);
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
    public static boolean createPolicyHolder(EntityManager entityManager, String id, InsuranceCard insuranceCard, String fullName, String address, String phoneNumber, String email, String password, PolicyOwner policyOwner){
        EntityTransaction transaction = entityManager.getTransaction();
        try{
            transaction.begin();
            PolicyHolder policyHolder = new PolicyHolder();
            policyHolder.setFullName(fullName);
            policyHolder.setPhoneNumber(phoneNumber);
            policyHolder.setAddress(address);
            policyHolder.setEmail(email);
            policyHolder.setPassword(password);
            policyHolder.setId(id);
            policyHolder.setType("PH");
            insuranceCard.setCardHolder(policyHolder);
            insuranceCard.setPolicyOwner(policyOwner);
            policyHolder.setPolicyOwner(policyOwner);
            entityManager.persist(policyHolder);
            entityManager.persist(insuranceCard);
            transaction.commit();
        }
        finally {
            if (transaction.isActive()){
                transaction.rollback();
            }
        }
        return true;
    }

    public static boolean createDependant(EntityManager entityManager, String id, String fullName, String address, String phoneNumber, String email, String password, PolicyHolder policyHolder){
        EntityTransaction transaction = entityManager.getTransaction();
        try{
            transaction.begin();
            Dependant dependant = new Dependant();
            dependant.setFullName(fullName);
            dependant.setPhoneNumber(phoneNumber);
            dependant.setAddress(address);
            dependant.setEmail(email);
            dependant.setPassword(password);
            dependant.setId(id);
            dependant.setType("DE");
            dependant.setPolicyHolder(policyHolder);
            dependant.setPolicyOwner(policyHolder.getPolicyOwner());
            dependant.setInsuranceCard(policyHolder.getInsuranceCard());
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

    public static boolean removePolicyOwner(EntityManager entityManager, PolicyOwner policyOwner){
        EntityTransaction transaction = entityManager.getTransaction();
        try{
            transaction.begin();
            entityManager.remove(policyOwner);
            transaction.commit();
        } finally {
            if (transaction.isActive()){
                transaction.rollback();
            }
        }
        return true;
    }
    public static boolean removePolicyHolder(EntityManager entityManager, PolicyHolder policyHolder){
        EntityTransaction transaction = entityManager.getTransaction();
        try{
            transaction.begin();
            entityManager.remove(policyHolder);
            transaction.commit();
        } finally {
            if (transaction.isActive()){
                transaction.rollback();
            }
        }
        return true;
    }

    public static boolean removeDependant(EntityManager entityManager, Dependant dependant){
        EntityTransaction transaction = entityManager.getTransaction();
        try{
            transaction.begin();
            entityManager.remove(dependant);
            transaction.commit();
        } finally {
            if (transaction.isActive()){
                transaction.rollback();
            }
        }
        return true;
    }


}
