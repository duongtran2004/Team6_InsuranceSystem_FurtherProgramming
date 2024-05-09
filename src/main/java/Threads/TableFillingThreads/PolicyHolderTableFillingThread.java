package Threads.TableFillingThreads;

import Entity.User;
import jakarta.persistence.EntityManager;
import org.example.insurancemanagementapplication.Controller.DashBoardController.TableFillingController.PolicyHolderTableFilling;
import org.example.insurancemanagementapplication.Interfaces.CustomerRead;

public class PolicyHolderTableFillingThread extends Thread {
    private EntityManager entityManager;
    private User user;

    public PolicyHolderTableFillingThread(EntityManager entityManager, User user) {
        this.entityManager = entityManager;
        this.user = user;
    }

    @Override
    public void run() {
        PolicyHolderTableFilling policyHolderTableFilling = new PolicyHolderTableFilling(entityManager, user);
        policyHolderTableFilling.fillingPolicyHolderTable(entityManager, user, CustomerRead.getAllPolicyHolder(entityManager));
    }


}
