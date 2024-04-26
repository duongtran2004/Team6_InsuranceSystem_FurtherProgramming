package org.example.insurancemanagementapplication.Controller;

import Entity.Claim;
import Entity.Customer;
import Entity.InsuranceSurveyor;

/**
 * @author Luong Thanh Trung
 * @version ${}
 * @created 27/04/2024 04:54
 * @project InsuranceManagementTeamProject
 */
public class InsuranceSurveyorController {
    private final InsuranceSurveyor insuranceSurveyor;
    private Customer customer;
    private Claim claim;

    public InsuranceSurveyorController(InsuranceSurveyor insuranceSurveyor) {
        this.insuranceSurveyor = insuranceSurveyor;
    }

    public InsuranceSurveyor getInsuranceSurveyor() {
        return insuranceSurveyor;
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
