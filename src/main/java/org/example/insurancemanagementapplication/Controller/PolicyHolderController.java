package org.example.insurancemanagementapplication.Controller;

import Entity.Claim;
import Entity.Dependant;
import Entity.PolicyHolder;

/**
 * @author Luong Thanh Trung
 * @version ${}
 * @created 27/04/2024 04:54
 * @project InsuranceManagementTeamProject
 */
public class PolicyHolderController {
    private final PolicyHolder policyHolder;
    private Dependant dependant;
    private Claim claim;

    public PolicyHolderController(PolicyHolder policyHolder) {
        this.policyHolder = policyHolder;
    }

    public PolicyHolder getPolicyHolder() {
        return policyHolder;
    }

    public Dependant getDependant() {
        return dependant;
    }

    public void setDependant(Dependant dependant) {
        this.dependant = dependant;
    }

    public Claim getClaim() {
        return claim;
    }

    public void setClaim(Claim claim) {
        this.claim = claim;
    }
}
