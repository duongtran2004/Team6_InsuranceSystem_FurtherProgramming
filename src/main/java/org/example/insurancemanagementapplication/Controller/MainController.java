package org.example.insurancemanagementapplication.Controller;

import jakarta.persistence.EntityManager;

/**
 * @author Luong Thanh Trung
 * @version ${}
 * @created 27/04/2024 04:50
 * @project InsuranceManagementTeamProject
 */
public class MainController {
    EntityManager entityManager;

    public MainController(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
