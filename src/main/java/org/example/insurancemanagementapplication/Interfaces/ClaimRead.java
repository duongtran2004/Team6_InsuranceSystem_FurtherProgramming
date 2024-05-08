package org.example.insurancemanagementapplication.Interfaces;

import Entity.Beneficiaries;
import Entity.Claim;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

import java.util.ArrayList;
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

    public static List<Claim> getAllClaimsFromABeneficiary(EntityManager entityManager, String beneficiaryID){
        Query query = entityManager.createQuery("SELECT c FROM Claim c WHERE c.insuredPersonId = ?1");
        query.setParameter(1, beneficiaryID);


        List<Claim> claims = query.getResultList();
        return claims;
    }
    public static List<Claim> getAllClaimsFromBeneficiariesOfAPolicyOwner(EntityManager entityManager, String policyOwnerID) {
        List<Beneficiaries> listOfBeneficiariesOfPolicyOwner = CustomerRead.getAllBeneficiariesOfAPolicyOwner(entityManager, policyOwnerID);
        List<Claim> listOfClaimsOfPolicyOwner = new ArrayList<>(listOfBeneficiariesOfPolicyOwner.size());

        for (Beneficiaries beneficiary : listOfBeneficiariesOfPolicyOwner) {
            listOfClaimsOfPolicyOwner.addAll(beneficiary.getListOfClaims());
        }

        return listOfClaimsOfPolicyOwner;
    }


    public static List<Claim> getAllClaimsProcessByAnInsuranceSurveyor(EntityManager entityManager, String insuranceSurveyorID){
        Query query = entityManager.createQuery("SELECT c FROM Claim c WHERE c.insuranceSurveyorId = ?1");
        query.setParameter(1, insuranceSurveyorID);


        List<Claim> claims = query.getResultList();
        return claims;
    }

    public static List<Claim> getAllClaimsProcessByAnInsuranceManager(EntityManager entityManager, String insuranceManagerID){
        Query query = entityManager.createQuery("SELECT c FROM Claim c WHERE c.insuranceManagerId = ?1");
        query.setParameter(1, insuranceManagerID);

        List<Claim> claims = query.getResultList();
        return claims;
    }


}
