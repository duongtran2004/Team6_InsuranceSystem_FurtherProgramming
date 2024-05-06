package org.example.insurancemanagementapplication.Interfaces;

import Entity.Claim;
import jakarta.persistence.EntityManager;

import java.util.List;

/**
 * @author Luong Thanh Trung
 * @version ${}
 * @created 27/04/2024 04:59
 * @project InsuranceManagementTeamProject
 */
public interface ClaimRead {
    public static List<Claim> getAllClaims(EntityManager entityManager){
        return entityManager.createQuery("SELECT c FROM Claim c").getResultList();
    }
}
