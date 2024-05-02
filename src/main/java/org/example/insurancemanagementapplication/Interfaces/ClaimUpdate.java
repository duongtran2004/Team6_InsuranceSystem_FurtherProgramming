package org.example.insurancemanagementapplication.Interfaces;

import Entity.Claim;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

/**
 * @author Luong Thanh Trung
 * @version ${}
 * @created 27/04/2024 04:59
 * @project InsuranceManagementTeamProject
 */
public interface ClaimUpdate {

    //overloading 3 methods
    //update claim for PolicyHolder and PolicyOwner
    static boolean updateClaim(EntityManager entityManager, Claim claim, String bankName, String bankAccountName, String accountNumber) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(claim);
            claim.setBankName(bankName);
            claim.setBankAccountName(bankAccountName);
            claim.setBankAccountNumber(accountNumber);
            transaction.commit();
        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
            }
        }
        return true;
    }
//update claim for insurance surveyor
    static boolean updateClaim(EntityManager entityManager, Claim claim,String status) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(claim);
            claim.setStatus(status);
            transaction.commit();
        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
            }
        }
        return true;
    }

    //update claim for insurance manager
    static boolean updateClaim(EntityManager entityManager, Claim claim, int claimAmount, String insuranceSurveyorId, String status) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(claim);
            claim.setClaimAmount(claimAmount);
            claim.setInsuranceSurveyorId(insuranceSurveyorId);
            claim.setStatus(status);
            transaction.commit();
        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
            }
        }
        return true;
    }


}
