package org.example.insurancemanagementapplication.Interfaces;

public interface YearlyRateCalculation {

    //FOR POLICY OWNER
    //count number of policy holders of a policy owner
//
//    public static int countPolicyHoldersOfAPolicyOwner(EntityManager entityManager, String policyOwnerID) {
//        Query query = entityManager.createQuery("SELECT COUNT(c) FROM Beneficiaries c WHERE c.policyOwnerId = ?1 AND c.type = 'PH'");
//        query.setParameter(1, policyOwnerID);
//        long policyHolderCount = (long) query.getSingleResult();
//        return (int) policyHolderCount;
//    }
//
//
//    //count number of dependants of a policy owner
//
//    public static int countDependantsOfAPolicyOwner(EntityManager entityManager, String policyOwnerID) {
//        Query query = entityManager.createQuery("SELECT COUNT(c) FROM Beneficiaries c WHERE c.policyOwnerId = ?1 AND c.type = 'DE'");
//        query.setParameter(1, policyOwnerID);
//        long dependantCount = (long) query.getSingleResult();
//        return (int) dependantCount;
//    }
//
//    public static int calculateYearlyRateOfAPolicyOwner(EntityManager entityManager, String policyOwnerID) {
//        int dependantCount = countDependantsOfAPolicyOwner(entityManager, policyOwnerID);
//        int policyHolderCount = countPolicyHoldersOfAPolicyOwner(entityManager, policyOwnerID);
//        PolicyOwner policyOwner = entityManager.getReference(PolicyOwner.class, policyOwnerID); //lazy loading object
//        int yearlyRate = policyOwner.getYearlyRate();
//        int totalYearlyRate = yearlyRate * policyHolderCount + yearlyRate * (60 / 100) * dependantCount;
//        return totalYearlyRate;
//    }
//
//
//    //FOR SYSTEM ADMIN
//    //calculate total yearly rate from all policy owner
//    public static int calculateTotalYearlyRateOfAllPolicyOwners(EntityManager entityManager) {
//
//        Query query = entityManager.createQuery("SELECT SUM(c.yearlyRate) FROM PolicyOwner c ");
//        int totalYearlyRate = (int) query.getSingleResult();
//        return totalYearlyRate;
//    }
//
//
//    //rank all policy owners by total yearly rate (vip customer)
//
//    //more advance feature if we have time:
//
//    //calculate prospect revenue
//    // by a time range
//    // by this week, by this month, by this quarter, by this year
//
//

}
