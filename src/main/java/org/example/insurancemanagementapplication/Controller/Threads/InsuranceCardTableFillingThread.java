package org.example.insurancemanagementapplication.Controller.Threads;

import Entity.InsuranceCard;
import org.example.insurancemanagementapplication.Controller.DashBoardController.TableFillingController.InsuranceCardTableFilling;

import java.util.List;


public class InsuranceCardTableFillingThread extends Thread {
    protected List<InsuranceCard> insuranceCardList;
    protected InsuranceCardTableFilling insuranceCardTableFilling;

    public InsuranceCardTableFillingThread(List<InsuranceCard> insuranceCardList, InsuranceCardTableFilling insuranceCardTableFilling) {
        this.insuranceCardList = insuranceCardList;
        this.insuranceCardTableFilling = insuranceCardTableFilling;
    }


    public  void insuranceCardTableFilling() {
     insuranceCardTableFilling.fillingInsuranceCardTable(insuranceCardTableFilling.getEntityManager(), insuranceCardTableFilling.getUser(), insuranceCardList );

    }

    @Override
    public void start() {
        insuranceCardTableFilling();
    }
}
