package org.example.insurancemanagementapplication.Interfaces;

import Entity.InsuranceManager;
import Entity.InsuranceSurveyor;
import Entity.SystemAdmin;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;

import java.util.List;

/**
 * @author Luong Thanh Trung
 * @version ${}
 * @created 27/04/2024 04:57
 * @project InsuranceManagementTeamProject
 */
public interface EmployeeRead {
    public static List<InsuranceManager> getAllInsuranceManager(EntityManager entityManager){
        return entityManager.createQuery(
                "SELECT e FROM InsuranceManager e").getResultList();

    }
    public static List<InsuranceSurveyor> getAllInsuranceSurveyor(EntityManager entityManager){
        return entityManager.createQuery(
                "SELECT e FROM InsuranceSurveyor e").getResultList();

    }

    public static InsuranceSurveyor findInsuranceSurveyorById(EntityManager entityManager, String id){
        InsuranceSurveyor insuranceSurveyor = entityManager.find(InsuranceSurveyor.class, id);
        return insuranceSurveyor;
    }

    public static InsuranceManager findInsuranceManagerById(EntityManager entityManager, String id){
        InsuranceManager insuranceManager = entityManager.find(InsuranceManager.class, id);
        return insuranceManager;
    }
    //FOR LOGIN

    public static SystemAdmin getSystemAdminWithCredential(EntityManager entityManager, String id, String email, String password){
        SystemAdmin employee = (SystemAdmin) entityManager.createQuery("SELECT c FROM SystemAdmin c WHERE c.id LIKE ?1 AND c.password LIKE ?2 AND c.email LIKE ?3").setParameter(1, id).setParameter(2, password).setParameter(3, email).getSingleResult();
        return employee;
    }
    public static InsuranceManager getInsuranceManagerWithCredential(EntityManager entityManager, String id, String email, String password){
        InsuranceManager employee = (InsuranceManager) entityManager.createQuery("SELECT c FROM InsuranceManager c WHERE c.id LIKE ?1 AND c.password LIKE ?2 AND c.email LIKE ?3").setParameter(1, id).setParameter(2, password).setParameter(3, email).getSingleResult();
        return employee;
    }
    public static InsuranceSurveyor getInsuranceSurveyorWithCredential(EntityManager entityManager, String id, String email, String password){
        InsuranceSurveyor employee = (InsuranceSurveyor) entityManager.createQuery("SELECT c FROM InsuranceSurveyor c WHERE c.id LIKE ?1 AND c.password LIKE ?2 AND c.email LIKE ?3").setParameter(1, id).setParameter(2, password).setParameter(3, email).getSingleResult();
        return employee;
    }
    //FOR RETRIEVING OBJECT
    public static InsuranceManager getInsuranceManagerByCredential(EntityManager entityManager, String fullName, String email, String password, String phoneNumber, String address) {
        try {
            InsuranceManager insuranceManager = (InsuranceManager) entityManager.createQuery(
                            "SELECT c FROM InsuranceManager c WHERE c.email = :email AND c.password = :password AND c.fullName = :fullName AND c.phoneNumber = :phoneNumber AND c.address = :address")
                    .setParameter("email", email)
                    .setParameter("password", password)
                    .setParameter("fullName", fullName)
                    .setParameter("phoneNumber", phoneNumber)
                    .setParameter("address", address)
                    .getSingleResult();
            return insuranceManager;
        } catch (NoResultException e) {
            return null; // Handle case where no matching policy owner is found
        }
    }

    public static InsuranceSurveyor getInsuranceSurveyorByCredential(EntityManager entityManager, String fullName, String email, String password, String phoneNumber, String address) {
        try {
            InsuranceSurveyor insuranceSurveyor = (InsuranceSurveyor) entityManager.createQuery(
                            "SELECT c FROM InsuranceSurveyor c WHERE c.email = :email AND c.password = :password AND c.fullName = :fullName AND c.phoneNumber = :phoneNumber AND c.address = :address")
                    .setParameter("email", email)
                    .setParameter("password", password)
                    .setParameter("fullName", fullName)
                    .setParameter("phoneNumber", phoneNumber)
                    .setParameter("address", address)
                    .getSingleResult();
            return insuranceSurveyor;
        } catch (NoResultException e) {
            return null; // Handle case where no matching policy owner is found
        }
    }

    public static SystemAdmin getSystemAdminByCredential(EntityManager entityManager, String fullName, String email, String password, String phoneNumber, String address) {
        try {
            SystemAdmin systemAdmin = (SystemAdmin) entityManager.createQuery(
                            "SELECT c FROM SystemAdmin c WHERE c.email = :email AND c.password = :password AND c.fullName = :fullName AND c.phoneNumber = :phoneNumber AND c.address = :address")
                    .setParameter("email", email)
                    .setParameter("password", password)
                    .setParameter("fullName", fullName)
                    .setParameter("phoneNumber", phoneNumber)
                    .setParameter("address", address)
                    .getSingleResult();
            return systemAdmin;
        } catch (NoResultException e) {
            return null; // Handle case where no matching policy owner is found
        }
    }



}
