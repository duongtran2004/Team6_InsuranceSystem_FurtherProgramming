package org.example.insurancemanagementapplication.Interfaces;

import Entity.Beneficiaries;
import Entity.PolicyOwner;

import java.util.List;

public interface YearlyRateCalculation {

    //FOR POLICY OWNER







    //    count number of policy holders of a policy owner

//    public static int countPolicyHoldersOfAPolicyOwner(EntityManager entityManager, String policyOwnerID) {
//        Query query = entityManager.createQuery("SELECT COUNT(c) FROM Beneficiaries c WHERE c.policyOwnerId = ?1 AND c.type = 'PH'");
//        query.setParameter(1, policyOwnerID);
//        long policyHolderCount = (long) query.getSingleResult();
//        return (int) policyHolderCount;
//    }


    public static int countPolicyHoldersOfAPolicyOwner(List<Beneficiaries> beneficiariesList) {
        int policyHoldersCount = 0;
        for (Beneficiaries beneficiaries : beneficiariesList ){
            if (beneficiaries.getType().equals("PH")){
                policyHoldersCount++;
            }
        }
        return policyHoldersCount;
    }

    //count number of dependants of a policy owner

//    public static int countDependantsOfAPolicyOwner(EntityManager entityManager, String policyOwnerID) {
//        Query query = entityManager.createQuery("SELECT COUNT(c) FROM Beneficiaries c WHERE c.policyOwnerId = ?1 AND c.type = 'DE'");
//        query.setParameter(1, policyOwnerID);
//        long dependantCount = (long) query.getSingleResult();
//        return (int) dependantCount;
//    }

    public static int countDependantsOfAPolicyOwner(List<Beneficiaries> beneficiariesList) {

        int dependantCount = 0;
        for (Beneficiaries beneficiaries : beneficiariesList){
            if (beneficiaries.getType().equals("DE")){
                dependantCount ++;
            }
        }
        return dependantCount;
    }

    public static String dividePolicyOwnerIntoPHYearlyRateLevel (int beneficiariesCount){
        String level = "";

        if (beneficiariesCount < 5){
            level = "D";
        }
        else if (beneficiariesCount < 10){
            level = "C";

        }
        else if (beneficiariesCount < 20){
            level = "B";
        }
        else {
            level = "A";
        }
        return level;
    }

    public static int getPHYearlyRateBasedOnLevel (String level){ //yearlyRate of A Policy Holder
        int yearlyRate = 0;
        if (level.equals("D")){
            yearlyRate = 400000;
        }
        else if (level.equals("C")){
            yearlyRate = 300000;
        }
        else if (level.equals("B")){
            yearlyRate = 150000;
        }
        else if (level.equals("A")){
            yearlyRate = 100000;
        }
        return yearlyRate;
    }

        public static int calculateYearlyRateOfAPolicyOwner(List<Beneficiaries> beneficiariesList) {
        int dependantCount = countDependantsOfAPolicyOwner(beneficiariesList);
        int policyHolderCount = countPolicyHoldersOfAPolicyOwner(beneficiariesList);
        int beneficiaryCount = dependantCount + policyHolderCount;
        String level = dividePolicyOwnerIntoPHYearlyRateLevel(beneficiaryCount);
        int PHyearlyRate = getPHYearlyRateBasedOnLevel(level);
            // Calculate DEyearlyRate and round to nearest integer
            int DEyearlyRate = (int) Math.round(PHyearlyRate * 0.6);
        int totalYearlyRate = PHyearlyRate * policyHolderCount + DEyearlyRate  * dependantCount;

        return totalYearlyRate;
    }


    //FOR SYSTEM ADMIN
    //calculate total yearly rate from all policy owner
    public static int calculateTotalYearlyRateOfAllPolicyOwners(List<PolicyOwner> policyOwnerList) {
        int POYearlyRateSum = 0;

        for (PolicyOwner policyOwner : policyOwnerList) {
            //add up the sum
            POYearlyRateSum  = POYearlyRateSum + policyOwner.getTotalYearlyRate();
        }
        return POYearlyRateSum;
    }


    //rank all policy owners by total yearly rate (vip customer)

    //more advance feature if we have time:

    //calculate prospect revenue
    // by a time range
    // by this week, by this month, by this quarter, by this year



}
