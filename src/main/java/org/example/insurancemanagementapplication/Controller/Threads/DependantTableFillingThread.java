package org.example.insurancemanagementapplication.Controller.Threads;

import Entity.Dependant;
import org.example.insurancemanagementapplication.Controller.DashBoardController.TableFillingController.DependantTableFilling;

import java.util.List;


public class DependantTableFillingThread extends Thread {
    protected List<Dependant> dependantList;
    protected DependantTableFilling dependantTableFilling;

    public DependantTableFillingThread(List<Dependant> dependantList, DependantTableFilling dependantTableFilling) {
        this.dependantList = dependantList;
        this.dependantTableFilling = dependantTableFilling;
    }

    public void dependantTableFilling() {
        dependantTableFilling.fillingDependantTable(dependantTableFilling.getEntityManager(), dependantTableFilling.getUser(), dependantList);

    }


    @Override
    public void start() {
        dependantTableFilling();
    }
}
