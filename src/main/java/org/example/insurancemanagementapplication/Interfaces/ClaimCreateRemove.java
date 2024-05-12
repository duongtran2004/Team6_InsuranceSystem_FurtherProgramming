package org.example.insurancemanagementapplication.Interfaces;

import Entity.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.sql.Date;

/**
 * @author Luong Thanh Trung
 * @version ${}
 * @created 27/04/2024 04:59
 * @project InsuranceManagementTeamProject
 */
public interface ClaimCreateRemove {

    public static boolean createClaim(EntityManager entityManager, String claimId, Date creationDate, Beneficiaries beneficiaries, PolicyOwner policyOwner, InsuranceCard insuranceCard, InsuranceManager insuranceManager, String bankName, String accountName, String accountNumber, byte[] documentFile){
        EntityTransaction transaction = entityManager.getTransaction();
        try{
            transaction.begin();
            Claim claim = new Claim();
            claim.setClaimId(claimId);
            claim.setCreationDate(creationDate);
            claim.setInsuranceManager(insuranceManager);
            claim.setStatus("NEW");
            claim.setInsuredPerson(beneficiaries);
            claim.setPolicyOwner(policyOwner);
            claim.setInsuranceCard(insuranceCard);
            claim.setBankName(bankName);
            claim.setBankAccountName(accountName);
            claim.setBankAccountNumber(accountNumber);
            claim.setDocumentFile(documentFile);
            entityManager.persist(claim);
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
