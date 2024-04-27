package org.example.insurancemanagementapplication.Controller;

import Entity.*;
import jakarta.persistence.EntityManager;
import org.example.insurancemanagementapplication.Interfaces.EmployeeCreateRemove;

/**
 * @author Luong Thanh Trung
 * @version ${}
 * @created 27/04/2024 04:55
 * @project InsuranceManagementTeamProject
 */
public class SystemAdminController implements EmployeeCreateRemove {
    private final EntityManager entityManager;
    private final SystemAdmin systemAdmin;
    private InsuranceSurveyor insuranceSurveyor;
    private InsuranceManager insuranceManager;
    private Customer customer;
    private Claim claim;

    public SystemAdminController(EntityManager entityManager, SystemAdmin systemAdmin) {
        this.entityManager = entityManager;
        this.systemAdmin = systemAdmin;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public SystemAdmin getSystemAdmin() {
        return systemAdmin;
    }

    public InsuranceSurveyor getInsuranceSurveyor() {
        return insuranceSurveyor;
    }

    public void setInsuranceSurveyor(InsuranceSurveyor insuranceSurveyor) {
        this.insuranceSurveyor = insuranceSurveyor;
    }

    public InsuranceManager getInsuranceManager() {
        return insuranceManager;
    }

    public void setInsuranceManager(InsuranceManager insuranceManager) {
        this.insuranceManager = insuranceManager;
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
}
