package org.example.insurancemanagementapplication.Interfaces;

import Entity.Dependant;
import Entity.InsuranceCard;
import Entity.PolicyHolder;
import Entity.PolicyOwner;
import jakarta.persistence.EntityManager;

import java.util.List;

/**
 * @author Luong Thanh Trung
 * @version ${}
 * @created 27/04/2024 04:56
 * @project InsuranceManagementTeamProject
 */
public interface CustomerAnalytics {
    public static List<PolicyOwner> getAllPolicyOwner(EntityManager entityManager){
        return entityManager.createQuery(
                "SELECT o FROM PolicyHolder o").getResultList();

    }
    public static List<PolicyHolder> getAllPolicyHolder(EntityManager entityManager){
        return entityManager.createQuery(
                "SELECT e FROM PolicyHolder e").getResultList();

    }

    public static List<Dependant> getAllDependant(EntityManager entityManager){
        return entityManager.createQuery(
                "SELECT d FROM Dependant d").getResultList();

    }

    public static List<InsuranceCard> getAllInsuranceCard(EntityManager entityManager){
        return entityManager.createQuery(
                "SELECT c FROM InsuranceCard c").getResultList();

    }
}
