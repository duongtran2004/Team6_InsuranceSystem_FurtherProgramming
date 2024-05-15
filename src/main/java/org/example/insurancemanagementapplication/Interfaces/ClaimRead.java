package org.example.insurancemanagementapplication.Interfaces;

import Entity.Beneficiaries;
import Entity.Claim;
import Entity.PolicyOwner;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

import java.util.ArrayList;
import java.util.List;

public interface    ClaimRead {

    /**
     * @author Luong Thanh Trung
     * @version ${}
     * @created 27/04/2024 04:59
     * @project InsuranceManagementTeamProject
     */

    public static List<Claim> getAllClaims(EntityManager entityManager) {
        return entityManager.createQuery("SELECT c FROM Claim c").getResultList();
    }

    public static List<Claim> getAllClaimsFromABeneficiary(EntityManager entityManager, String beneficiaryID) {
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


    public static List<Claim> getAllClaimsProcessByAnInsuranceSurveyor(EntityManager entityManager, String insuranceSurveyorID) {
        Query query = entityManager.createQuery("SELECT c FROM Claim c WHERE c.insuranceSurveyorId = ?1");
        query.setParameter(1, insuranceSurveyorID);


        List<Claim> claims = query.getResultList();
        return claims;
    }

    public static List<Claim> getAllClaimsProcessByAnInsuranceManager(EntityManager entityManager, String insuranceManagerID) {
        Query query = entityManager.createQuery("SELECT c FROM Claim c WHERE c.insuranceManagerId = ?1");
        query.setParameter(1, insuranceManagerID);


        List<Claim> claims = query.getResultList();
        return claims;
    }
//FOR SYSTEM ADMIN

    //TOTAL SUCCESSFUL CLAIM AMOUNT
    public static int getTotalSuccessfulClaimAmount(EntityManager entityManager) {
        Query query = entityManager.createQuery("SELECT SUM(c.claimAmount) FROM Claim c WHERE c.status = 'APPROVED'");
        Integer totalAmount = (Integer) query.getSingleResult();
        if (totalAmount != null) {
            return totalAmount;
        } else {
            return 0;
        }
    }


    public static int getTotalSuccessfulClaimAmountProcessedByAnEmployee(EntityManager entityManager, String employeeID, String role) {
        int totalAmount = 0;
        if (role == "InsuranceManager") {
            totalAmount = getTotalSuccessfulClaimAmountApprovedByAnInsuranceManager(entityManager, employeeID);
        }
        if (role == "InsuranceSurveyor") {
            totalAmount = getTotalSuccessfulClaimAmountProcessedByAnInsuranceSurveyor(entityManager, employeeID);
        }

        return totalAmount;
    }




    public static int getTotalSuccessfulClaimAmountMadeByABeneficiary (Beneficiaries beneficiary) {
        int totalAmount = 0;
        for (Claim claim : beneficiary.getListOfClaims()) {
            if (claim.getStatus().equals("APPROVED")) {
                totalAmount = totalAmount+  claim.getClaimAmount();
            }
        }
        return totalAmount;
    }

    public static int getTotalSuccessfulClaimAmountMadeByAPolicyOwner(EntityManager entityManager, PolicyOwner policyOwner) {
        // Ensure the listOfClaims is initialized within an active session
//        policyOwner = entityManager.merge(policyOwner);
//        Hibernate.initialize(policyOwner.getListOfClaims());
        int totalAmount = 0;
        for (Claim claim : policyOwner.getListOfClaims()) {
            if (claim.getStatus().equals("APPROVED")) {
                totalAmount = totalAmount + claim.getClaimAmount();
            }
        }
        return totalAmount;
    }




    public static int getTotalSuccessfulClaimAmountProcessedByAnInsuranceSurveyor(EntityManager entityManager, String insuranceSurveyorID) {
        Query query = entityManager.createQuery("SELECT SUM(c.claimAmount) FROM Claim c WHERE c.status = 'APPROVED' AND c.insuranceSurveyorId = ?1").setParameter(1, insuranceSurveyorID);
        Integer totalAmount = (Integer) query.getSingleResult();
        if (totalAmount != null) {
            return totalAmount;
        } else {
            return 0;
        }

    }

    public static int getTotalSuccessfulClaimAmountApprovedByAnInsuranceManager(EntityManager entityManager, String insuranceManagerID) {
        Query query = entityManager.createQuery("SELECT SUM(c.claimAmount) FROM Claim c WHERE c.status = 'APPROVED' AND c.insuranceManagerId = ?1").setParameter(1, insuranceManagerID);
        Integer totalAmount = (Integer) query.getSingleResult();
        if (totalAmount != null) {
            return totalAmount;
        } else {
            return 0;
        }
    }

//public static int getTotalSuccessfulClaimAmountInATimeRange(EntityManager entityManager, Date startDate, Date endDate) {
//
//}


// GET NUMBER OF SUCCESSFUL CLAIMS

    public static int getTotalNumberOfSuccessfulClaims(EntityManager entityManager) {
        Query query = entityManager.createQuery("SELECT c FROM Claim c");
        List<Claim> claims = query.getResultList();
        return claims.size();
    }

    public static int getTotalNumberOfSuccessfulClaimsMadeByACustomer(EntityManager entityManager, String customerID) {
        Query query = entityManager.createQuery("SELECT c FROM Claim c");
        List<Claim> claims = query.getResultList();
        return claims.size();
    }


    public static int getTotalNumberOfSuccessfulClaimsProcessedByAnEmployee(EntityManager entityManager, String employeeID) {
        Query query = entityManager.createQuery("SELECT c FROM Claim c");
        List<Claim> claims = query.getResultList();
        return claims.size();
    }
//get finished claim by employee


    public static List<Claim> getFinishedClaimOfAnEmployee(EntityManager entityManager, String employeeID, String role) {
        List<Claim> listOfFinishedClaim = new ArrayList<>();
        if (role == "InsuranceManager") {
            listOfFinishedClaim = getFinishedClaimOfAnInsuranceManager(entityManager, employeeID);

        }
        if (role == "InsuranceSurveyor") {
            listOfFinishedClaim = getFinishedClaimOfAnInsuranceSurveyor(entityManager, employeeID);
        }
        return listOfFinishedClaim;

    }

    public static List<Claim> getFinishedClaimOfAnInsuranceManager(EntityManager entityManager, String insuranceManagerID) {

        Query query = entityManager.createQuery("SELECT c FROM Claim c WHERE c.status = 'FINISHED' OR c.status = 'REJECTED'AND c.insuranceManagerId = ?1");
        query.setParameter(1, insuranceManagerID);
        List<Claim> claims = query.getResultList();
        return claims;
    }

    public static List<Claim> getFinishedClaimOfAnInsuranceSurveyor(EntityManager entityManager, String insuranceSurveyorID) {
        Query query = entityManager.createQuery("SELECT c FROM Claim c WHERE c.status = 'FINISHED' OR c.status = 'REJECTED'AND c.insuranceSurveyorId = ?1");
        query.setParameter(1, insuranceSurveyorID);
        List<Claim> claims = query.getResultList();
        return claims;
    }


    //get number of finishedClaim (status = reject or approved)
    public static int getNumberOfFinishedClaimsOfAnEmployee(EntityManager entityManager, String employeeID, String role) {
         int numberOfFinishedClaims = getFinishedClaimOfAnEmployee(entityManager, employeeID, role).size();
         return numberOfFinishedClaims;
    }


//public static int getTotalNumberOfSuccessfulClaimsInATimeRange(EntityManager entityManager, Date startDate, Date endDate) {
//    Query query = entityManager.createQuery("SELECT c FROM Claim c");
//    List<Claim> claims = query.getResultList();
//    return claims.size();
//}


// AVERAGE SUCCESSFUL CLAIM AMOUNT

    public static double getAverageSuccessfulClaimAmountPerClaim(EntityManager entityManager) {
        int totalSuccessfulClaimAmount = getTotalSuccessfulClaimAmount(entityManager);
        int totalSuccessfulClaimsMade = getTotalNumberOfSuccessfulClaims(entityManager);
        return (totalSuccessfulClaimAmount / totalSuccessfulClaimsMade);
    }

//
//public static double getAverageSuccessfulClaimAmountInATimeRange(EntityManager entityManager, Date startDate, Date endDate) {
//}

}







