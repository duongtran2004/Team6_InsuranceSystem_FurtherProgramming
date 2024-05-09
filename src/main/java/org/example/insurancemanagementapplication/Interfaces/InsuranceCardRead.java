package org.example.insurancemanagementapplication.Interfaces;

import Entity.InsuranceCard;
import jakarta.persistence.EntityManager;

import java.util.List;

public interface InsuranceCardRead {

    //for System Admin

    public static List<InsuranceCard> getAllInsuranceCard(EntityManager entityManager) {
        return entityManager.createQuery(
                "SELECT c FROM InsuranceCard c").getResultList();

    }

    // For Policy Owner
    public static List<InsuranceCard> getAllInsuranceCardsOfPolicyOwner(EntityManager entityManager, String policyOwnerID) {
        return entityManager.createQuery(
                "SELECT c FROM InsuranceCard c WHERE c.policyOwnerId = ?1").setParameter(1, policyOwnerID).getResultList();

    }
//Employee can only retrieve the insurance card that is related to the claims  they deal with

    // For insurance Surveyor
//unit testing for this method
    public static List<InsuranceCard> getAllInsuranceCardsProcessByInsuranceSurveyor(EntityManager entityManager, String insuranceSurveyorID) {
        return entityManager.createQuery(
                "SELECT c.insuranceCard FROM Claim c WHERE c.insuranceSurveyorId = ?1").setParameter(1, insuranceSurveyorID).getResultList();
    }


    public static List<InsuranceCard> getAllInsuranceCardProcessByInsuranceManager(EntityManager entityManager, String insuranceManagerID) {
        return entityManager.createQuery(
                "SELECT c.insuranceCard FROM Claim c WHERE c.insuranceManagerId = ?1").setParameter(1, insuranceManagerID).getResultList();

    }


    //Extra feature: Notify when insuranceCard is close to expiring (to customer)
    //1 Month, 1 Week, 1 Day
}
