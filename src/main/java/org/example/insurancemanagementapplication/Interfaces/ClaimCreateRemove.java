package org.example.insurancemanagementapplication.Interfaces;

import Entity.Claim;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.Date;

/**
 * @author Luong Thanh Trung
 * @version ${}
 * @created 27/04/2024 04:59
 * @project InsuranceManagementTeamProject
 */
public interface ClaimCreateRemove {

    public static boolean createClaim(EntityManager entityManager, String claimId, Date creationDate, Date settlementDate, String status, float claimAmount, String insuredPersonId, String policyOwnerId, String cardNumber, String insuranceManagerId, String insuranceSurveyorId, String bankName, String accountName, String accountNumber, byte[] documentImage){
        EntityTransaction transaction = entityManager.getTransaction();
        try{
            transaction.begin();
            Claim claim = new Claim();
            transaction.commit();
        } finally {
            if (transaction.isActive()){
                transaction.rollback();
            }
        }
        return true;
    }


    public static boolean removeClaim(EntityManager entityManager, Claim claim){
        EntityTransaction transaction = entityManager.getTransaction();
        try{
            transaction.begin();
            entityManager.remove(claim);
            transaction.commit();
        } finally {
            if (transaction.isActive()){
                transaction.rollback();
            }
        }
        return true;
    }
}
