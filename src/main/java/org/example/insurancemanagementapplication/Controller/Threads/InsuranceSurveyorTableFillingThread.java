package org.example.insurancemanagementapplication.Controller.Threads;

import Entity.InsuranceSurveyor;
import org.example.insurancemanagementapplication.Controller.DashBoardController.TableFillingController.InsuranceSurveyorTableFilling;

import java.util.List;

public class InsuranceSurveyorTableFillingThread extends Thread {
    protected List<InsuranceSurveyor> insuranceSurveyorList;
    protected InsuranceSurveyorTableFilling insuranceSurveyorTableFilling;


    public InsuranceSurveyorTableFillingThread(List<InsuranceSurveyor> insuranceSurveyorList, InsuranceSurveyorTableFilling insuranceSurveyorTableFilling) {
        this.insuranceSurveyorList = insuranceSurveyorList;
        this.insuranceSurveyorTableFilling = insuranceSurveyorTableFilling;
    }

    public void insuranceSurveyorTableFilling() {
        insuranceSurveyorTableFilling.fillingInsuranceSurveyorTable(insuranceSurveyorTableFilling.getEntityManager(), insuranceSurveyorTableFilling.getUser(), insuranceSurveyorList);

    }

    @Override
    public void start() {
        insuranceSurveyorTableFilling();
    }
}
