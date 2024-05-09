package Threads.TableFillingThreads;

import Entity.User;
import jakarta.persistence.EntityManager;
import org.example.insurancemanagementapplication.Controller.DashBoardController.TableFillingController.InsuranceManagerTableFilling;
import org.example.insurancemanagementapplication.Interfaces.EmployeeRead;

public class InsuranceManagerTableFillingThread extends Thread {

    private EntityManager entityManager;
    private User user;

    public InsuranceManagerTableFillingThread(EntityManager entityManager, User user) {
        this.entityManager = entityManager;
        this.user = user;
    }

    @Override
    public void run() {
        InsuranceManagerTableFilling insuranceManagerTableFilling = new InsuranceManagerTableFilling(entityManager, user);
        insuranceManagerTableFilling.fillingInsuranceManagerTable(entityManager, user, EmployeeRead.getAllInsuranceManager(entityManager));
    }
}
