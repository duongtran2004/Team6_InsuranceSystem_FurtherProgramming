package org.example.insurancemanagementapplication.Controller;

import Entity.Claim;
import Entity.Dependant;
import jakarta.persistence.EntityManager;

/**
 * @author Luong Thanh Trung
 * @version ${}
 * @created 27/04/2024 04:53
 * @project InsuranceManagementTeamProject
 */
public class DependantDashBoardController {
    private final EntityManager entityManager;
    private final Dependant dependant;
    private Claim claim;


    public DependantDashBoardController(Dependant dependant, EntityManager entityManager) {
        this.dependant = dependant;
        this.entityManager = entityManager;
    }

    public Dependant getDependant() {
        return dependant;
    }

    public Claim getClaim() {
        return claim;
    }

    public void setClaim(Claim claim) {
        this.claim = claim;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }
}
