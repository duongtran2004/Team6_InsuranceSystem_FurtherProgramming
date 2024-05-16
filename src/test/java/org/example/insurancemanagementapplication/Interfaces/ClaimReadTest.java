package org.example.insurancemanagementapplication.Interfaces;

import Entity.Claim;
import Entity.PolicyOwner;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.Test;

class ClaimReadTest {
    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    /*
    @Test
     void downloadDocument  () {
        Claim claim = entityManager.find(Claim.class, "F44453446");
        byte[] cover = claim.getDocumentFile();

        try (FileOutputStream fos
                     = new FileOutputStream("F:\\Code\\shrek.jpg")) {
            fos.write(cover);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

//    @Test
//    void getTotalSuccessfulClaimAmountOfAPolicyOwner(){
//        PolicyOwner policyOwner = entityManager.find(PolicyOwner.class, "PO6173753721");
//        int amount = ClaimRead.getTotalSuccessfulClaimAmountMadeByAPolicyOwner(policyOwner);
//        System.out.println("List of claims: " + policyOwner.getListOfClaims());
//        System.out.println(amount);
//
//    }

    //@Test
    void getAllClaimOfAPolicyOwner() {
        PolicyOwner policyOwner = entityManager.find(PolicyOwner.class, "PO6173753721");
        for (Claim claim : policyOwner.getListOfClaims()) {
            System.out.println(claim.toString());
        }
    }

    @Test
    void getTotalSuccesfulClaimAmountOfAPolicyOwner() {
        PolicyOwner policyOwner = entityManager.find(PolicyOwner.class, "PO6173753721");
        int amount = 0;
        for (Claim claim : policyOwner.getListOfClaims()) {
            if (claim.getStatus().equals("APPROVED")) {
                amount = amount + claim.getClaimAmount();
            }
        }
        System.out.println(amount);
    }

}