package org.example.insurancemanagementapplication.Controller.Threads;

import Entity.PolicyHolder;
import org.example.insurancemanagementapplication.Controller.DashBoardController.TableFillingController.PolicyHolderTableFilling;

import java.util.List;

public class PolicyHolderTableFillingThread extends Thread {
    protected List<PolicyHolder> policyHolderList;
    protected PolicyHolderTableFilling policyHolderTableFilling;


    public PolicyHolderTableFillingThread(List<PolicyHolder> policyHolderList, PolicyHolderTableFilling policyHolderTableFilling) {
        this.policyHolderList = policyHolderList;
        this.policyHolderTableFilling = policyHolderTableFilling;
    }

    public void policyHolderTableFilling() {
        policyHolderTableFilling.fillingPolicyHolderTable(policyHolderTableFilling.getEntityManager(), policyHolderTableFilling.getUser(), policyHolderList);

    }

    @Override
    public void start() {
        policyHolderTableFilling();
    }
}
