package Threads.TableFillingThreads;

import Entity.User;
import jakarta.persistence.EntityManager;
import org.example.insurancemanagementapplication.Controller.DashBoardController.TableFillingController.PolicyOwnerTableFilling;
import org.example.insurancemanagementapplication.Interfaces.CustomerRead;

public class PolicyOwnerTableFillingThread extends Thread {
    private EntityManager entityManager;
    private User user;

    public PolicyOwnerTableFillingThread(EntityManager entityManager, User user) {
        this.entityManager = entityManager;
        this.user = user;
    }

    @Override
    public void run() {
        PolicyOwnerTableFilling policyOwnerTableFilling = new PolicyOwnerTableFilling(entityManager, user);

        policyOwnerTableFilling.fillingPolicyOwnerTable(entityManager, user, CustomerRead.getAllPolicyOwner(entityManager));
    }
}
