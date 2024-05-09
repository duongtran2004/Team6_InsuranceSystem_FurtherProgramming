package org.example.insurancemanagementapplication.Interfaces;

import Entity.InsuranceCard;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InsuranceCardReadTest {
    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
    EntityManager entityManager = entityManagerFactory.createEntityManager();

//    @Test
//    void getAllInsuranceCard() {
//    }
//
//    @Test
//    void getAllInsuranceCardsOfPolicyOwner() {
//    }

    @Test
    void getAllInsuranceCardsProcessByInsuranceSurveyor() {
        List<InsuranceCard> insuranceCards = InsuranceCardRead.getAllInsuranceCardsProcessByInsuranceSurveyor(entityManager,"IM0000000001");
        System.out.println(insuranceCards.toString());
    }

//    @Test
//    void getAllInsuranceCardProcessByInsuranceManager() {
//    }
}