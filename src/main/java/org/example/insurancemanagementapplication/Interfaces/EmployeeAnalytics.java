package org.example.insurancemanagementapplication.Interfaces;

import Entity.InsuranceManager;
import Entity.InsuranceSurveyor;
import Entity.SystemAdmin;
import jakarta.persistence.EntityManager;

import java.util.List;

/**
 * @author Luong Thanh Trung
 * @version ${}
 * @created 27/04/2024 04:57
 * @project InsuranceManagementTeamProject
 */
public interface EmployeeAnalytics {
    public static List<InsuranceManager> getAllInsuranceManager(EntityManager entityManager){
        return entityManager.createQuery(
                "SELECT e FROM InsuranceManager e").getResultList();

    }
    public static List<InsuranceSurveyor> getAllInsuranceSurveyor(EntityManager entityManager){
        return entityManager.createQuery(
                "SELECT e FROM InsuranceSurveyor e").getResultList();

    }

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
}
