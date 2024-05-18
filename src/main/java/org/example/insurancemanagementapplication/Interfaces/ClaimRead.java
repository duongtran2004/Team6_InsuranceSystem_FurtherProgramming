package org.example.insurancemanagementapplication.Interfaces;

import Entity.*;
import jakarta.persistence.EntityManager;
import javafx.scene.Node;
import javafx.stage.Stage;
import org.example.insurancemanagementapplication.Controller.Page404Controller;
import org.example.insurancemanagementapplication.Utility.StageBuilder;

import java.util.ArrayList;
import java.util.List;

public interface ClaimRead {

    /**
     * @author Luong Thanh Trung
     * @version ${}
     * @created 27/04/2024 04:59
     * @project InsuranceManagementTeamProject
     */

    public static List<Claim> getAllClaims(Node node, User user, EntityManager entityManager) {
        try{
            return entityManager.createQuery("SELECT c FROM Claim c").getResultList();
        } catch (Exception e){
            Page404Controller page404Controller = new Page404Controller(user, entityManager);
            StageBuilder.showStage((Stage) node.getScene().getWindow(), page404Controller, "Page404.fxml", "An Error has occurred");
            return null;
        }


    }


    public static List<Claim> getAllClaimsFromBeneficiariesOfAPolicyOwner(Node node, User user, EntityManager entityManager, String policyOwnerID) {
        try{
            List<Beneficiaries> listOfBeneficiariesOfPolicyOwner = CustomerRead.getAllBeneficiariesOfAPolicyOwner(node, user, entityManager, policyOwnerID);
            List<Claim> listOfClaimsOfPolicyOwner = new ArrayList<>(listOfBeneficiariesOfPolicyOwner.size());
            for (Beneficiaries beneficiary : listOfBeneficiariesOfPolicyOwner) {
                listOfClaimsOfPolicyOwner.addAll(beneficiary.getListOfClaims());
            }

            return listOfClaimsOfPolicyOwner;
        } catch (Exception e){
            Page404Controller page404Controller = new Page404Controller(user, entityManager);
            StageBuilder.showStage((Stage) node.getScene().getWindow(), page404Controller, "Page404.fxml", "An Error has occurred");
            return null;
        }

    }



//FOR SYSTEM ADMIN

    //TOTAL SUCCESSFUL CLAIM AMOUNT


    public static int getTotalSuccessfulClaimAmountMadeByABeneficiary(Node node, User user, EntityManager entityManager, Beneficiaries beneficiary) {
        try {
            int totalAmount = 0;
            if(beneficiary.getListOfClaims() == null || beneficiary.getListOfClaims().isEmpty()) {
                return totalAmount;
            }
            for (Claim claim : beneficiary.getListOfClaims()) {
                if (claim.getStatus().equals("APPROVED")) {
                    totalAmount = totalAmount + claim.getClaimAmount();
                }
            }
            return totalAmount;
        } catch (Exception e){
            Page404Controller page404Controller = new Page404Controller(user, entityManager);
            StageBuilder.showStage((Stage) node.getScene().getWindow(), page404Controller, "Page404.fxml", "An Error has occurred");
            return 0;
        }

    }

    //    policyOwner = entityManager.merge(policyOwner);
//        Hibernate.initialize(policyOwner.getListOfClaims());
    public static int getTotalSuccessfulClaimAmountMadeByAPolicyOwner(Node node, User user, EntityManager entityManager, List<Claim> policyOwnerListOfClaims) {
        // Ensure the listOfClaims is initialized within an active session
        //policyOwner = entityManager.merge(policyOwner);
        //Hibernate.initialize(policyOwner.getListOfClaims());
        try{
            int totalAmount = 0;
            if(policyOwnerListOfClaims == null || policyOwnerListOfClaims.isEmpty()) {
                return totalAmount;
            }
            for (Claim claim : policyOwnerListOfClaims) {
                if (claim.getStatus().equals("APPROVED")) {
                    totalAmount = totalAmount + claim.getClaimAmount();
                }
            }
            return totalAmount;
        } catch (Exception e){
            Page404Controller page404Controller = new Page404Controller(user, entityManager);
            StageBuilder.showStage((Stage) node.getScene().getWindow(), page404Controller, "Page404.fxml", "An Error has occurred");
            return 0;
        }

    }





//public static int getTotalSuccessfulClaimAmountInATimeRange(EntityManager entityManager, Date startDate, Date endDate) {
//
//}




    public static int countTotalResolvedClaimOfAnInsuranceSurveyor(Node node, User user, EntityManager entityManager, InsuranceSurveyor insuranceSurveyor) {
        try {
            int resolvedClaims = 0;
            if (insuranceSurveyor.getListOfClaims() == null || insuranceSurveyor.getListOfClaims().isEmpty()) {
                return resolvedClaims;
            }
            for (Claim claim : insuranceSurveyor.getListOfClaims()) {
                if (claim.getStatus().equals("APPROVED") || claim.getStatus().equals("REJECTED")) {
                    resolvedClaims++;
                }
            }
            return resolvedClaims;
        } catch (Exception e){
            Page404Controller page404Controller = new Page404Controller(user, entityManager);
            StageBuilder.showStage((Stage) node.getScene().getWindow(), page404Controller, "Page404.fxml", "An Error has occurred");
            return 0;
        }

    }

    public static int countTotalResolvedClaimOfAnInsuranceManager(Node node, User user, EntityManager entityManager, InsuranceManager insuranceManager) {
        try {
            int resolvedClaims = 0;
            if (insuranceManager.getListOfClaims() == null || insuranceManager.getListOfClaims().isEmpty()) {
                return resolvedClaims;
            }
            for (Claim claim : insuranceManager.getListOfClaims()) {
                if (claim.getStatus().equals("APPROVED") || claim.getStatus().equals("REJECTED")) {
                    resolvedClaims++;
                }
            }


            return resolvedClaims;
        } catch (Exception e){
            Page404Controller page404Controller = new Page404Controller(user, entityManager);
            StageBuilder.showStage((Stage) node.getScene().getWindow(), page404Controller, "Page404.fxml", "An Error has occurred");
            return 0;
        }

    }




//}

}







