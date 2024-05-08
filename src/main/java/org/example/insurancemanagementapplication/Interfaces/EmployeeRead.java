package org.example.insurancemanagementapplication.Interfaces;

import Entity.InsuranceManager;
import Entity.InsuranceSurveyor;
import Entity.SystemAdmin;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Luong Thanh Trung
 * @version ${}
 * @created 27/04/2024 04:57
 * @project InsuranceManagementTeamProject
 */
public interface EmployeeRead {
    public static List<InsuranceManager> getAllInsuranceManager(EntityManager entityManager) {
        return entityManager.createQuery(
                "SELECT e FROM InsuranceManager e").getResultList();

    }

    public static List<InsuranceSurveyor> getAllInsuranceSurveyor(EntityManager entityManager) {
        return entityManager.createQuery(
                "SELECT e FROM InsuranceSurveyor e").getResultList();

    }

    public static InsuranceSurveyor findInsuranceSurveyorById(EntityManager entityManager, String id) {
        InsuranceSurveyor insuranceSurveyor = entityManager.find(InsuranceSurveyor.class, id);
        return insuranceSurveyor;
    }

    public static InsuranceManager findInsuranceManagerById(EntityManager entityManager, String id) {
        InsuranceManager insuranceManager = entityManager.find(InsuranceManager.class, id);
        return insuranceManager;
    }
    //FOR LOGIN

    public static SystemAdmin getSystemAdminWithCredential(EntityManager entityManager, String id, String email, String password) {
        SystemAdmin employee = (SystemAdmin) entityManager.createQuery("SELECT c FROM SystemAdmin c WHERE c.id LIKE ?1 AND c.password LIKE ?2 AND c.email LIKE ?3").setParameter(1, id).setParameter(2, password).setParameter(3, email).getSingleResult();
        return employee;
    }

    public static InsuranceManager getInsuranceManagerWithCredential(EntityManager entityManager, String id, String email, String password) {
        InsuranceManager employee = (InsuranceManager) entityManager.createQuery("SELECT c FROM InsuranceManager c WHERE c.id LIKE ?1 AND c.password LIKE ?2 AND c.email LIKE ?3").setParameter(1, id).setParameter(2, password).setParameter(3, email).getSingleResult();
        return employee;
    }

    public static InsuranceSurveyor getInsuranceSurveyorWithCredential(EntityManager entityManager, String id, String email, String password) {
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

    public static List<InsuranceSurveyor> getAllInsuranceSurveyorOfAnInsuranceManager(EntityManager entityManager, String insuranceManagerID) {
        return entityManager.createQuery("SELECT c FROM InsuranceSurveyor c WHERE c.insuranceManagerId = ?1").setParameter(1, insuranceManagerID).getResultList();
    }


//should I use map instead for this ranking method, if I want to display both user and their sucessful claim amount in the front-end ?

    public static Map<Object, Integer> rankAllEmployeeByTotalFinishedClaims(EntityManager entityManager) {
        List<Object> allEmployees = new ArrayList<>();
        List<InsuranceManager> allInsuranceManagers = EmployeeRead.getAllInsuranceManager(entityManager);
        List<InsuranceSurveyor> allInsuranceSurveyors = EmployeeRead.getAllInsuranceSurveyor(entityManager);
        allEmployees.addAll(allInsuranceManagers);
        allEmployees.addAll(allInsuranceSurveyors);

        // Calculate the total finished claim count for each employee
        Map<Object, Integer> totalFinishedClaimsMap = allEmployees.stream()
                .collect(Collectors.toMap(
                        employee -> employee,
                        employee -> {
                            if (employee instanceof InsuranceManager) {
                                return ClaimRead.getTotalSuccessfulClaimAmountProcessedByAnEmployee(entityManager, ((InsuranceManager) employee).getId(), "InsuranceManager");
                            } else if (employee instanceof InsuranceSurveyor) {
                                return ClaimRead.getTotalSuccessfulClaimAmountProcessedByAnEmployee(entityManager, ((InsuranceSurveyor) employee).getId(), "InsuranceSurveyor");
                            }
                            return 0;
                        }
                ));
        // Sort the employees based on their total finished claim count
        Map<Object, Integer> rankedEmployees = totalFinishedClaimsMap.entrySet().stream()
                .sorted((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue())) // Sort by value in descending order
                .collect(Collectors.toMap(
                        entry -> entry.getKey(), // Use lambda expression to get the key
                        entry -> entry.getValue(), // Use lambda expression to get the value
                        (oldValue, newValue) -> oldValue, // Keep existing values (not necessary here)
                        LinkedHashMap::new // Preserve order
                ));

        return rankedEmployees;
    }


}
