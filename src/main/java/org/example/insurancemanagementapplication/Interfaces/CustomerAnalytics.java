package org.example.insurancemanagementapplication.Interfaces;

import Entity.*;
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
                "SELECT o FROM PolicyOwner o").getResultList();

    }
    public static List<PolicyHolder> getAllPolicyHolder(EntityManager entityManager){
        return entityManager.createQuery(
                "SELECT e FROM PolicyHolder e WHERE e.type LIKE ?1").setParameter(1, "PH").getResultList();

    }

    public static List<Dependant> getAllDependant(EntityManager entityManager){
        return entityManager.createQuery(
                "SELECT d FROM Beneficiaries d WHERE d.type LIKE ?1").setParameter(1, "DE").getResultList();

    }

    public static List<InsuranceCard> getAllInsuranceCard(EntityManager entityManager){
        return entityManager.createQuery(
                "SELECT c FROM InsuranceCard c").getResultList();

    }

    public static Customer getCustomerWithCredentials(EntityManager entityManager, String email, String password, String id, String role ){
        if (role.equals("Policy Owner")){
            PolicyOwner customer = (PolicyOwner) entityManager.createQuery("SELECT c FROM PolicyOwner c WHERE c.id LIKE ?1 AND c.password LIKE ?2 AND c.email LIKE ?3").setParameter(1, id).setParameter(2, password).setParameter(3, email).getSingleResult();
            return customer;
        }
        else {
            if (role.equals("Dependant")){
                Dependant customer = (Dependant) entityManager.createQuery("SELECT c FROM Beneficiaries c WHERE c.id LIKE ?1 AND c.password LIKE ?2 AND c.email LIKE ?3 AND c.type = ?4").setParameter(1, id).setParameter(2, password).setParameter(3, email).setParameter(4, "DE").getSingleResult();
                return customer;
            }
            else {
                PolicyHolder customer = (PolicyHolder) entityManager.createQuery("SELECT c FROM Beneficiaries c WHERE c.id LIKE ?1 AND c.password LIKE ?2 AND c.email LIKE ?3 AND c.type = ?4").setParameter(1, id).setParameter(2, password).setParameter(3, email).setParameter(4, "PH").getSingleResult();
                return customer;
            }
        }

    }
}
