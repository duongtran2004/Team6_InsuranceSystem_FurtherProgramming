package Threads.TableFillingThreads;

import Entity.User;
import jakarta.persistence.EntityManager;
import org.example.insurancemanagementapplication.Controller.DashBoardController.TableFillingController.ClaimTableFilling;
import org.example.insurancemanagementapplication.Interfaces.ClaimRead;

public class ClaimTableFillingThread extends  Thread{
    private EntityManager entityManager;
    private User user;

    public ClaimTableFillingThread(EntityManager entityManager, User user) {
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

        ClaimTableFilling claimTableFilling = new ClaimTableFilling(entityManager,user);
        claimTableFilling.  fillingClaimTable(entityManager, user, ClaimRead.getAllClaimsFromABeneficiary(entityManager,user.getId()));
    }
}

