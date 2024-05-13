package org.example.insurancemanagementapplication.Controller.Threads;

import Entity.PolicyHolder;
import org.example.insurancemanagementapplication.Controller.DashBoardController.TableFillingController.PolicyHolderTableFilling;

import java.util.List;

public class PolicyOwnerTableFillingThread extends Thread {
    protected List<PolicyHolder> policyHolderList;
    protected PolicyHolderTableFilling policyHolderTableFilling;


    public PolicyOwnerTableFillingThread(List<PolicyHolder> policyHolderList, PolicyHolderTableFilling policyHolderTableFilling) {
        this.policyHolderList = policyHolderList;
        this.policyHolderTableFilling = policyHolderTableFilling;
    }

    public void policyOwnerTableFilling() {
        policyHolderTableFilling.fillingPolicyHolderTable(policyHolderTableFilling.getEntityManager(), policyHolderTableFilling.getUser(), policyHolderList);
    }

    @Override
    public void run() {
        policyOwnerTableFilling();
    }
}
