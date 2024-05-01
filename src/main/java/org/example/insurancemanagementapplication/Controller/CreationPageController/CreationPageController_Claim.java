package org.example.insurancemanagementapplication.Controller.CreationPageController;

import Entity.Beneficiaries;
import Entity.Claim;
import Entity.User;
import jakarta.persistence.EntityManager;

/**
 * @author Luong Thanh Trung
 * @version ${}
 * @created 01/05/2024 09:28
 * @project InsuranceManagementTeamProject
 */
public class CreationPageController_Claim {
    private EntityManager entityManager;
    private User user;
    private Claim claim;
    private Beneficiaries beneficiary;

    public CreationPageController_Claim(EntityManager entityManager, User user, Claim claim) {
        this.entityManager = entityManager;
        this.user = user;
        this.claim = claim;
    }

    public CreationPageController_Claim(EntityManager entityManager, User user, Beneficiaries beneficiary) {
        this.entityManager = entityManager;
        this.user = user;
        this.beneficiary = beneficiary;
    }
}
