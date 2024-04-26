package org.example.insurancemanagementapplication.Controller;

import Entity.*;

/**
 * @author Luong Thanh Trung
 * @version ${}
 * @created 27/04/2024 04:54
 * @project InsuranceManagementTeamProject
 */
public class PolicyOwnerController {
    private final PolicyOwner policyOwner;
    private Beneficiaries beneficiary;
    private Claim claim;

    public PolicyOwnerController(PolicyOwner policyOwner) {
        this.policyOwner = policyOwner;
    }

    public PolicyOwner getPolicyOwner() {
        return policyOwner;
    }

    public Beneficiaries getBeneficiary() {
        return beneficiary;
    }

    public void setBeneficiary(Beneficiaries beneficiary) {
        this.beneficiary = beneficiary;
    }

    public Claim getClaim() {
        return claim;
    }

    public void setClaim(Claim claim) {
        this.claim = claim;
    }
}
