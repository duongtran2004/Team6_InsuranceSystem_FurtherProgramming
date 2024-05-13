package org.example.insurancemanagementapplication.Controller.Threads;

import Entity.Claim;
import org.example.insurancemanagementapplication.Controller.DashBoardController.TableFillingController.ClaimTableFilling;

import java.util.List;

//Inner class for thread
public  class ClaimTableFillingThread extends Thread {
    protected List<Claim> claimList;

    protected ClaimTableFilling claimTableFilling;

    public ClaimTableFillingThread(List<Claim> claimList, ClaimTableFilling claimTableFilling) {
        this.claimList = claimList;
        this.claimTableFilling = claimTableFilling;
    }

    public void claimTableFilling() {
        claimTableFilling.fillingClaimTable(claimTableFilling.getEntityManager(), claimTableFilling.getUser(), claimList);

    }

    @Override
    public void start() {
        claimTableFilling();
    }
}