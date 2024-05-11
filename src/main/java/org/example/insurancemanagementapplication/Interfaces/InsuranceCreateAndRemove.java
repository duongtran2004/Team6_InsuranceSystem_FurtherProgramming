package org.example.insurancemanagementapplication.Interfaces;

import Entity.Beneficiaries;
import Entity.InsuranceCard;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.sql.Date;
import java.util.List;

public interface InsuranceCreateAndRemove {



    public static boolean createInsuranceCard(EntityManager entityManager, String cardNumber, Date expirationDate, String cardHolderId, String policyOwnerId){
        EntityTransaction transaction = entityManager.getTransaction();
        try{
            transaction.begin();
            InsuranceCard insuranceCard = new InsuranceCard();
            insuranceCard.setCardNumber(cardNumber);
            insuranceCard.setExpirationDate(expirationDate);
            insuranceCard.setCardHolderId(cardHolderId);
            insuranceCard.setPolicyOwnerId(policyOwnerId);
            entityManager.persist(insuranceCard);
            transaction.commit();
        }
        finally {
            if (transaction.isActive()){
                transaction.rollback();
            }
        }
        return true;
    }
    public static boolean removeInsuranceCard(EntityManager entityManager, InsuranceCard insuranceCard){
        EntityTransaction transaction = entityManager.getTransaction();
        try{
            transaction.begin();
            entityManager.remove(insuranceCard);
            transaction.commit();
            // Update the insurance card field of all its beneficiaries
            updateBeneficiariesInsuranceCardField(entityManager, insuranceCard);
        } finally {
            if (transaction.isActive()){
                transaction.rollback();
            }
        }
        return true;
    }

    private static void updateBeneficiariesInsuranceCardField(EntityManager entityManager, InsuranceCard insuranceCard) {
        // Retrieve all beneficiaries associated with the insurance card
        List<Beneficiaries> beneficiaries = entityManager.createQuery("SELECT b FROM Beneficiaries b WHERE b.insuranceCard = :insuranceCard", Beneficiaries.class)
                .setParameter("insuranceCard", insuranceCard)
                .getResultList();

        // Update the insurance card field of each beneficiary
        entityManager.getTransaction().begin();
        for (Beneficiaries beneficiary : beneficiaries) {
            beneficiary.setInsuranceCard(null); // Set insurance card field to null
            entityManager.merge(beneficiary); // Update the beneficiary in the database
        }
        entityManager.getTransaction().commit();
    }
}
