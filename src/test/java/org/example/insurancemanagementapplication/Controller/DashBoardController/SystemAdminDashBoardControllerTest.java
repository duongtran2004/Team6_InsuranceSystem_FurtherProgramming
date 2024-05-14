package org.example.insurancemanagementapplication.Controller.DashBoardController;

import Entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SystemAdminDashBoardControllerTest {
    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    User user = new User();
    Thread refreshThread = new Thread();
    Thread afkThread = new Thread();
//    @Test
//    void delayRefreshCountThread() {
//        SystemAdminDashBoardController systemAdminDashBoardController = new SystemAdminDashBoardController(entityManager,user,refreshThread, afkThread);
//        System.out.println("hello");
//        systemAdminDashBoardController.delayRefreshCountThread(refreshThread);
//
//
//
//    }

//    @Test
//    void handleRefreshButton() {
//    }

//    @Test
//    void handleLogOutButton() {
//    }

//    @Test
//    void handleClearCreationDateButton() {
//    }

//    @Test
//    void handleClearSettlementDateButton() {
//    }

//    @Test
//    void handleClearClaimAmountButton() {
//    }

//    @Test
//    void initialize() {
//    }
}