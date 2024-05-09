package Threads.TableFillingThreads;

import Entity.User;
import jakarta.persistence.EntityManager;
import org.example.insurancemanagementapplication.Controller.DashBoardController.TableFillingController.DependantTableFilling;
import org.example.insurancemanagementapplication.Interfaces.CustomerRead;

public class DependantTableFillingThread extends Thread {
    private EntityManager entityManager;
    private User user;

    public DependantTableFillingThread(EntityManager entityManager, User user) {
        this.entityManager = entityManager;
        this.user = user;
    }

    @Override
    public void run() {
        // Fill the table based on the specific logic
        // For example:
        // DependantTableFilling filling = new DependantTableFilling(entityManager, user);
        // filling.fillingDependantTable(entityManager, user, dependants);
        // Adjust the method call based on your actual implementation

        DependantTableFilling dependantTableFilling = new DependantTableFilling(entityManager, user);
        dependantTableFilling.fillingDependantTable(entityManager, user, CustomerRead.getAllDependant(entityManager));
    }
}
