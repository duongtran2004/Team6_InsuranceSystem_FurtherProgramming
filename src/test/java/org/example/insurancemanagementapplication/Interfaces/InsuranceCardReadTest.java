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
    void getAllInsuranceCardsTakeChargeByAnEmployee() {
        List<InsuranceCard> insuranceCards = InsuranceCardRead.getAllInsuranceCardsTakeChargeByAnEmployee(entityManager,"IM7685824369", "InsuranceManager");
        for (InsuranceCard insuranceCard : insuranceCards) {
            System.out.println(insuranceCard);
        }
    }

//    @Test
//    void getAllInsuranceCardProcessByInsuranceManager() {
//    }
}