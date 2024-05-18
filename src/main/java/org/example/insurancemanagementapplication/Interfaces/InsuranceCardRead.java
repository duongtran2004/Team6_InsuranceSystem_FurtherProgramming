package org.example.insurancemanagementapplication.Interfaces;

import Entity.Claim;
import Entity.InsuranceCard;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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


    public static List<InsuranceCard> getAllInsuranceCardsTakeChargeByAnEmployee(EntityManager entityManager, String employeeID, String role) {
        List<InsuranceCard> insuranceCards = new ArrayList<>();
        Set<String> processedIds = new HashSet<>();

        String queryString;
        if (role.equals("InsuranceSurveyor")) {
            // Query for insurance surveyors
            queryString = "SELECT c FROM Claim c WHERE c.insuranceSurveyorId = :employeeId";
        } else if (role.equals("InsuranceManager")) {
            // Query for insurance managers
            queryString = "SELECT c FROM Claim c WHERE c.insuranceManagerId = :employeeId";
        } else {//invalid role
            return insuranceCards;
        }

        // Create a query
        Query query = entityManager.createQuery(queryString);
        query.setParameter("employeeId", employeeID);

        // Execute the query to get the claims
        List<Claim> claims = query.getResultList();

        // Iterate through each claim to retrieve the dependant objects
        for (Claim claim : claims) {
            // Retrieve the insurance card object
            InsuranceCard insuranceCard = claim.getInsuranceCard();
            // Check if the card number has already been processed
            if (!processedIds.contains(insuranceCard.getCardNumber())) {
                insuranceCards.add(insuranceCard);
                processedIds.add(insuranceCard.getCardNumber());
            }


        }
        return insuranceCards;
    }


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
