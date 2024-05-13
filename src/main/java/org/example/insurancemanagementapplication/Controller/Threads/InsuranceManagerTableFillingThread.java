package org.example.insurancemanagementapplication.Controller.Threads;

import Entity.InsuranceManager;
import org.example.insurancemanagementapplication.Controller.DashBoardController.TableFillingController.InsuranceManagerTableFilling;

import java.util.List;

//Inner class for thread
public class InsuranceManagerTableFillingThread extends Thread {

    protected List<InsuranceManager> insuranceManagerList;
    protected InsuranceManagerTableFilling insuranceManagerTableFilling;

    public InsuranceManagerTableFillingThread(List<InsuranceManager> insuranceManagerList, InsuranceManagerTableFilling insuranceManagerTableFilling) {
        this.insuranceManagerList = insuranceManagerList;
        this.insuranceManagerTableFilling = insuranceManagerTableFilling;
    }

    public void insuranceManagerTableFilling() {
        insuranceManagerTableFilling.fillingInsuranceManagerTable(insuranceManagerTableFilling.getEntityManager(), insuranceManagerTableFilling.getUser(), insuranceManagerList);

    }

    @Override
    public void start() {
        insuranceManagerTableFilling();

    }
}
