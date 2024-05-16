package org.example.insurancemanagementapplication.Interfaces;

import Entity.InsuranceCard;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.Test;

import java.util.List;

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

//    @Test
//    void getAllInsuranceCardsTakeChargeByAnEmployee() {
//        List<InsuranceCard> insuranceCards = InsuranceCardRead.getAllInsuranceCardsTakeChargeByAnEmployee(entityManager,"IM7685824369", "InsuranceManager");
//        for (InsuranceCard insuranceCard : insuranceCards) {
//            System.out.println(insuranceCard);
//        }
//    }
    //@Test
    void  getALlInsuranceCards() {
        List<InsuranceCard> insuranceCardList =
        InsuranceCardRead.getAllInsuranceCard(entityManager);
        for (InsuranceCard insuranceCard : insuranceCardList) {
            System.out.println(insuranceCard);
        }

    }
    @Test
    void getAnInsuranceCardByEntityManager() {
        InsuranceCard insuranceCard = entityManager.find(InsuranceCard.class, "9087940541");
        System.out.println(insuranceCard);
    }

//    @Test
//    void getAllInsuranceCardProcessByInsuranceManager() {
//    }
}