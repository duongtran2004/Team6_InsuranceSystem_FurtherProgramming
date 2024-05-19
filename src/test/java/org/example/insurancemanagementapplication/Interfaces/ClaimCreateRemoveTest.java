package org.example.insurancemanagementapplication.Interfaces;

import Entity.Dependant;
import Entity.InsuranceManager;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Date;

class ClaimCreateRemoveTest {
    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
    EntityManager entityManager = entityManagerFactory.createEntityManager();

    @Test
    void createClaim() throws IOException {

        Date today = new Date();
        java.sql.Date sqlToday = new java.sql.Date(today.getTime());
        Dependant dependant = entityManager.find(Dependant.class, "DE4123996892");
        InsuranceManager insuranceManager = EmployeeRead.findRandomInsuranceManager(entityManager);
        File file = new File("src/org.example.insurancemanagementapplication.main/resources/org/example/insurancemanagementapplication/SampleImage/shrek.jpg");

       ClaimCreateRemove.createClaim(entityManager, "F44453446", sqlToday, dependant, dependant.getPolicyOwner(), dependant.getInsuranceCard(), insuranceManager, "ggd", "gegds", "3344", Files.readAllBytes(Path.of(file.getPath())));


        //public static boolean createClaim(EntityManager entityManager, String claimId,
        // Date creationDate, Beneficiaries beneficiaries, PolicyOwner policyOwner,
        // InsuranceCard insuranceCard, InsuranceManager insuranceManager, String bankName,
        // String accountName, String accountNumber, byte[] documentFile){
    }
}