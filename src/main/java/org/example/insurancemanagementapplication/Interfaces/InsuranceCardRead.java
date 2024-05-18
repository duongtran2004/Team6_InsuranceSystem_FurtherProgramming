package org.example.insurancemanagementapplication.Interfaces;

import Entity.Claim;
import Entity.InsuranceCard;
import Entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import javafx.scene.Node;
import javafx.stage.Stage;
import org.example.insurancemanagementapplication.Controller.Page404Controller;
import org.example.insurancemanagementapplication.Utility.StageBuilder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public interface InsuranceCardRead {

    //for System Admin

    public static List<InsuranceCard> getAllInsuranceCard(Node node, User user, EntityManager entityManager) {
        try {
            return entityManager.createQuery(
                    "SELECT c FROM InsuranceCard c").getResultList();

        } catch (Exception e){
            Page404Controller page404Controller = new Page404Controller(user, entityManager);
            StageBuilder.showStage((Stage) node.getScene().getWindow(), page404Controller, "Page404.fxml", "An Error has occurred");
            return null;
        }

    }

    // For Policy Owner
    public static List<InsuranceCard> getAllInsuranceCardsOfPolicyOwner(Node node, User user, EntityManager entityManager, String policyOwnerID) {
        try {
            return entityManager.createQuery(
                    "SELECT c FROM InsuranceCard c WHERE c.policyOwnerId = ?1").setParameter(1, policyOwnerID).getResultList();
        } catch (Exception e){
            Page404Controller page404Controller = new Page404Controller(user, entityManager);
            StageBuilder.showStage((Stage) node.getScene().getWindow(), page404Controller, "Page404.fxml", "An Error has occurred");
            return null;
        }


    }
//Employee can only retrieve the insurance card that is related to the claims  they deal with


    public static List<InsuranceCard> getAllInsuranceCardsTakeChargeByAnEmployee(Node node, User user, EntityManager entityManager, String employeeID, String role) {
        try {
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
        } catch (Exception e){
            Page404Controller page404Controller = new Page404Controller(user, entityManager);
            StageBuilder.showStage((Stage) node.getScene().getWindow(), page404Controller, "Page404.fxml", "An Error has occurred");
            return null;
        }

    }


    // For insurance Surveyor

//unit testing for this method


    //Extra feature: Notify when insuranceCard is close to expiring (to customer)
    //1 Month, 1 Week, 1 Day
}
