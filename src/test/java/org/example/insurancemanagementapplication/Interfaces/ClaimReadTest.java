package org.example.insurancemanagementapplication.Interfaces;

import Entity.Claim;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.Test;

import java.io.FileOutputStream;

class ClaimReadTest {
    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    @Test
    public void downloadDocument  () {
        Claim claim = entityManager.find(Claim.class, "F44453446");
        byte[] cover = claim.getDocumentFile();

        try (FileOutputStream fos
                     = new FileOutputStream("F:\\Code\\shrek.jpg")) {
            fos.write(cover);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}