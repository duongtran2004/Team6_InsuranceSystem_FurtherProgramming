package org.example.insurancemanagementapplication.Interfaces;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomerReadTest {
    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
    EntityManager entityManager = entityManagerFactory.createEntityManager();
//    @Test
//    void getDependentWithLoginCredentials() {
//        CustomerRead.getDependentWithLoginCredentials(entityManager,"szanettini9@dion.ne.jp","55903705", "DE5087972690" );
//
//    }

    @Test
    void getPolicyHolderWithLoginCredential() {
        CustomerRead.getPolicyHolderWithLoginCredentials(entityManager,"swalhedd0@scribd.com","33902597", "PH5264972101");
    }


}