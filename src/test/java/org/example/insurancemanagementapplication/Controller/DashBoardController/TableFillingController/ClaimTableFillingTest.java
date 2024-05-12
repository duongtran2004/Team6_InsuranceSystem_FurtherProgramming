package org.example.insurancemanagementapplication.Controller.DashBoardController.TableFillingController;

import Entity.Claim;
import Entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import org.example.insurancemanagementapplication.Interfaces.ClaimRead;
import org.example.insurancemanagementapplication.Utility.CustomizedComparator;
import org.junit.jupiter.api.Test;

class ClaimTableFillingTest {
    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    User user = new User();

    @Test
    public void testSortingClaimTable() {
        // Create sample data for testing
        ObservableList<Claim> claimObservableList = FXCollections.observableArrayList();
        claimObservableList.addAll(ClaimRead.getAllClaims(entityManager)); // Add sample Claim objects
        //convert to SortedList
        SortedList<Claim> sortedClaimList = new SortedList<>(claimObservableList);
        String newVal = "Sort by Claim Amount In Ascending Order";
    if (!(newVal.equals("NONE"))) {
            if (newVal.equals("Sort By Creation Date In Ascending Order")) {
                CustomizedComparator.ClaimCreationDateComparator claimCreationDateComparator = new CustomizedComparator.ClaimCreationDateComparator();
                sortedClaimList.sort(claimCreationDateComparator);
            } else if (newVal.equals("Sort By Creation Date In Descending Order")) {
                CustomizedComparator.ClaimCreationDateComparator claimCreationDateComparator = new CustomizedComparator.ClaimCreationDateComparator();
                sortedClaimList.sort(claimCreationDateComparator);
                sortedClaimList.reversed();
            } else if (newVal.equals("Sort By Settlement Date In Ascending Order")) {
                CustomizedComparator.ClaimSettlementDateComparator claimSettlementDateComparator = new CustomizedComparator.ClaimSettlementDateComparator();
                sortedClaimList.sort(claimSettlementDateComparator);
            } else if (newVal.equals("Sort By Settlement Date In Descending Order")) {
                CustomizedComparator.ClaimSettlementDateComparator claimSettlementDateComparator = new CustomizedComparator.ClaimSettlementDateComparator();
                sortedClaimList.sort(claimSettlementDateComparator);
                sortedClaimList.reversed();
            } else if (newVal.equals("Sort by Claim Amount In Ascending Order")) {
                CustomizedComparator.ClaimAmountComparator claimAmountComparator = new CustomizedComparator.ClaimAmountComparator();
                sortedClaimList.sort(claimAmountComparator);
            } else if (newVal.equals("Sort by Claim Amount In Descending Order")) {
                CustomizedComparator.ClaimAmountComparator claimAmountComparator = new CustomizedComparator.ClaimAmountComparator();
                sortedClaimList.sort(claimAmountComparator);
                sortedClaimList.reversed();
            }
        }

    for (Claim claim : sortedClaimList) {
        System.out.println(String.valueOf(claim.getClaimAmount()));
    }

    }
}