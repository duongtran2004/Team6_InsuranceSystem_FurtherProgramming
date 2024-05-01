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
public interface ClaimCreateRemove {

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
