package org.example.insurancemanagementapplication.Interfaces;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

class EmployeeReadTest {
    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
    EntityManager entityManager = entityManagerFactory.createEntityManager();
//    @Test
//    void findRandomInsuranceManager() {
//        InsuranceManager insuranceManager = EmployeeRead.findRandomInsuranceManager(entityManager);
//        System.out.println(insuranceManager);
//    }


}