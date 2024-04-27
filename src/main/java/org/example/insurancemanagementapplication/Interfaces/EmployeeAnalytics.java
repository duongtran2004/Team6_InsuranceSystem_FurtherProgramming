package org.example.insurancemanagementapplication.Interfaces;

import Entity.InsuranceManager;
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
                "SELECT m FROM InsuranceManager m").getResultList();

    }
}
