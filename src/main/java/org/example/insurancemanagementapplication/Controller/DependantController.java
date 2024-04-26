package org.example.insurancemanagementapplication.Controller;

import Entity.Claim;
import Entity.Dependant;

/**
 * @author Luong Thanh Trung
 * @version ${}
 * @created 27/04/2024 04:53
 * @project InsuranceManagementTeamProject
 */
public class DependantController {
    private final Dependant dependant;
    private Claim claim;

    public DependantController(Dependant dependant) {
        this.dependant = dependant;
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
}
