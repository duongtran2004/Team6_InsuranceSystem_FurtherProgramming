package org.example.insurancemanagementapplication.Controller;

import Entity.Claim;
import Entity.Customer;
import Entity.InsuranceManager;
import Entity.InsuranceSurveyor;
import jakarta.persistence.EntityManager;

/**
 * @author Luong Thanh Trung
 * @version ${}
 * @created 27/04/2024 04:54
 * @project InsuranceManagementTeamProject
 */
public class InsuranceManagerController {
    private final EntityManager entityManager;
    private final InsuranceManager insuranceManager;
    private InsuranceSurveyor insuranceSurveyor;
    private Customer customer;
    private Claim claim;

    public InsuranceManagerController(InsuranceManager insuranceManager, EntityManager entityManager) {
        this.insuranceManager = insuranceManager;
        this.entityManager = entityManager;
    }

    public InsuranceManager getInsuranceManager() {
        return insuranceManager;
    }

    public InsuranceSurveyor getInsuranceSurveyor() {
        return insuranceSurveyor;
    }

    public void setInsuranceSurveyor(InsuranceSurveyor insuranceSurveyor) {
        this.insuranceSurveyor = insuranceSurveyor;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
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
