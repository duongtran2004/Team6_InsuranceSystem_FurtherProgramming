package org.example.insurancemanagementapplication.Interfaces;

import Entity.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;

import java.util.List;

/**
 * @author Luong Thanh Trung
 * @version ${}
 * @created 27/04/2024 04:56
 * @project InsuranceManagementTeamProject
 */
public interface CustomerRead {
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

    //For input validation

    public static PolicyOwner getPolicyOwnerByCredential(EntityManager entityManager, String fullName, String email, String password, String phoneNumber, String address) {
        try {
            PolicyOwner policyOwner = (PolicyOwner) entityManager.createQuery(
                            "SELECT c FROM PolicyOwner c WHERE c.email = :email AND c.password = :password AND c.fullName = :fullName AND c.phoneNumber = :phoneNumber AND c.address = :address")
                    .setParameter("email", email)
                    .setParameter("password", password)
                    .setParameter("fullName", fullName)
                    .setParameter("phoneNumber", phoneNumber)
                    .setParameter("address", address)
                    .getSingleResult();
            return policyOwner;
        } catch (NoResultException e) {
            return null; // Handle case where no matching policy owner is found
        }
    }

    public static Dependant getDependantByCredential(EntityManager entityManager, String fullName, String email, String password, String phoneNumber, String address) {
        try {
            Dependant dependant = (Dependant) entityManager.createQuery(
                            "SELECT c FROM Dependant c WHERE c.email = :email AND c.password = :password AND c.fullName = :fullName AND c.phoneNumber = :phoneNumber AND c.address = :address")
                    .setParameter("email", email)
                    .setParameter("password", password)
                    .setParameter("fullName", fullName)
                    .setParameter("phoneNumber", phoneNumber)
                    .setParameter("address", address)
                    .getSingleResult();
            return dependant;
        } catch (NoResultException e) {
            return null; // Handle case where no matching policy owner is found
        }
    }
    public static PolicyHolder getPolicyHolderByCredential(EntityManager entityManager, String fullName, String email, String password, String phoneNumber, String address) {
        try {
            PolicyHolder policyHolder = (PolicyHolder) entityManager.createQuery(
                            "SELECT c FROM PolicyHolder c WHERE c.email = :email AND c.password = :password AND c.fullName = :fullName AND c.phoneNumber = :phoneNumber AND c.address = :address")
                    .setParameter("email", email)
                    .setParameter("password", password)
                    .setParameter("fullName", fullName)
                    .setParameter("phoneNumber", phoneNumber)
                    .setParameter("address", address)
                    .getSingleResult();
            return policyHolder;
        } catch (NoResultException e) {
            return null; // Handle case where no matching policy owner is found
        }
    }



}
