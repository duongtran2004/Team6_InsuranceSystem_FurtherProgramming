package Threads.TableFillingThreads;

import Entity.User;
import jakarta.persistence.EntityManager;
import org.example.insurancemanagementapplication.Controller.DashBoardController.TableFillingController.InsuranceSurveyorTableFilling;
import org.example.insurancemanagementapplication.Interfaces.EmployeeRead;

public class InsuranceSurveyorTableFillingThread extends Thread {

    private EntityManager entityManager;
    private User user;

    public InsuranceSurveyorTableFillingThread(EntityManager entityManager, User user) {

        this.entityManager = entityManager;
        this.user = user;
    }

    @Override
    public void run() {
        InsuranceSurveyorTableFilling insuranceSurveyorTableFilling = new InsuranceSurveyorTableFilling(entityManager, user);
        insuranceSurveyorTableFilling.fillingInsuranceSurveyorTable(entityManager, user, EmployeeRead.getAllInsuranceSurveyor(entityManager));

    }
}
