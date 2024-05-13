package org.example.insurancemanagementapplication.Controller.Threads;

import Entity.PolicyOwner;
import org.example.insurancemanagementapplication.Controller.DashBoardController.TableFillingController.PolicyOwnerTableFilling;

import java.util.List;

public class PolicyOwnerTableFillingThread extends Thread {
    protected List<PolicyOwner> policyOwnerList;
    protected PolicyOwnerTableFilling policyOwnerTableFilling;


    public PolicyOwnerTableFillingThread(List<PolicyOwner> policyOwnerList, PolicyOwnerTableFilling policyOwnerTableFilling) {
        this.policyOwnerList = policyOwnerList;
        this.policyOwnerTableFilling = policyOwnerTableFilling;
    }

    public void policyOwnerTableFilling() {
        policyOwnerTableFilling.fillingPolicyOwnerTable(policyOwnerTableFilling.getEntityManager(), policyOwnerTableFilling.getUser(), policyOwnerList);
    }

    @Override
    public void run() {
        policyOwnerTableFilling();
    }
}
