package org.example.insurancemanagementapplication.Interfaces;

import Entity.InsuranceManager;
import Entity.InsuranceSurveyor;
import Entity.SystemAdmin;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

/**
 * @author Luong Thanh Trung
 * @version ${}
 * @created 27/04/2024 04:58
 * @project InsuranceManagementTeamProject
 */
public interface EmployeeUpdate {

    static boolean updateSystemAdmin(EntityManager entityManager, SystemAdmin systemAdmin, String address, String phoneNumber, String email, String password){
        EntityTransaction transaction = entityManager.getTransaction();
        try{
            transaction.begin();
            entityManager.persist(systemAdmin);
            systemAdmin.setAddress(address);
            systemAdmin.setPhoneNumber(phoneNumber);
            systemAdmin.setEmail(email);
            systemAdmin.setPassword(password);
            transaction.commit();


        }finally {
            if (transaction.isActive()){
                transaction.rollback();
            }
        }
        return true;
    }
    static boolean updateInsuranceManager(EntityManager entityManager, InsuranceManager insuranceManager, String address, String phoneNumber, String email, String password){
        EntityTransaction transaction = entityManager.getTransaction();
        try{
            transaction.begin();
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

    static boolean updateInsuranceSurveyor(EntityManager entityManager, InsuranceSurveyor insuranceSurveyor, String address, String phoneNumber, String email, String password, InsuranceManager insuranceManager){
        EntityTransaction transaction = entityManager.getTransaction();
        try{
            transaction.begin();
            entityManager.persist(insuranceSurveyor);
            insuranceSurveyor.setAddress(address);
            insuranceSurveyor.setPhoneNumber(phoneNumber);
            insuranceSurveyor.setEmail(email);
            insuranceSurveyor.setPassword(password);
            insuranceSurveyor.setInsuranceManager(insuranceManager);
            transaction.commit();

        } finally {
            if (transaction.isActive()){
                transaction.rollback();
            }
        }
        return true;

    }

    static boolean updateInsuranceSurveyor(EntityManager entityManager, InsuranceSurveyor insuranceSurveyor, String address, String phoneNumber, String email, String password){
        EntityTransaction transaction = entityManager.getTransaction();
        try{
            transaction.begin();
            entityManager.persist(insuranceSurveyor);
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
