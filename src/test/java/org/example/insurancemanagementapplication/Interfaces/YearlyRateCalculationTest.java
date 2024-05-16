package org.example.insurancemanagementapplication.Interfaces;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

class YearlyRateCalculationTest {
    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
    EntityManager entityManager = entityManagerFactory.createEntityManager();

// //@Test
//    void countPolicyHoldersOfAPolicyOwner() {
//        PolicyOwner policyOwner = entityManager.find(PolicyOwner.class, "PO4059407832");
//        int policyHolderCount = YearlyRateCalculation.countPolicyHoldersOfAPolicyOwner(policyOwner);
//        System.out.println(policyHolderCount);
//    }



//    @Test
    void countDependantsOfAPolicyOwner() {
    }

//    @Test
    void dividePolicyOwnerIntoPHYearlyRateLevel() {
    }

//    @Test
    void getPHYearlyRateBasedOnLevel() {
    }

//    @Test
    void calculateYearlyRateOfAPolicyOwner() {
    }
//
// @Test
//    void calculateTotalYearlyRateOfAllPolicyOwners() {
//     PolicyOwner policyOwner = entityManager.find(PolicyOwner.class, "PO6173753721");
//     int dependantCount = YearlyRateCalculation.countDependantsOfAPolicyOwner(policyOwner);
//     int policyHolderCount = YearlyRateCalculation.countPolicyHoldersOfAPolicyOwner(policyOwner);
//     int beneficiariesCount = dependantCount + policyHolderCount;
//     String level = YearlyRateCalculation.dividePolicyOwnerIntoPHYearlyRateLevel(beneficiariesCount);
//     int PHrate = YearlyRateCalculation.getPHYearlyRateBasedOnLevel(level);
//     int DErate = (int) Math.round(PHrate * 0.6);
//     int totalYearlyRate = YearlyRateCalculation.calculateYearlyRateOfAPolicyOwner(policyOwner);
//
//
//     //display the result for debugging
//     System.out.println("dependantCount " + dependantCount);
//     System.out.println("policyHolderCount" + policyHolderCount);
//     System.out.println(" beneficiaries " + beneficiariesCount);
//     System.out.println("level " + level);
//     System.out.println("PHrate " + PHrate);
//     System.out.println("DErate " + DErate);
//
//     System.out.println("totalYearlyRate " + totalYearlyRate);
//
//    }
}