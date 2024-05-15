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

import java.util.ArrayList;
import java.util.List;

class ClaimTableFillingTest {
    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    User user = new User();

//    @Test
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

    @Test
    void setTextToClaimLabels() {

        //list of sample claims
        Claim claim1 = new Claim();
        claim1.setStatus("APPROVED");
        claim1.setClaimAmount(100);

        Claim claim2 = new Claim();
        claim2.setStatus("APPROVED");
        claim2.setClaimAmount(200);


        List<Claim> claimList = new ArrayList<>();
        claimList.add(claim1);
        claimList.add(claim2);
        System.out.println("Original list of claims: " + claimList);


        //add them into a collection
        //sample attributes
        int originalTotalSuccessfulClaims = 1;

        int originalTotalSuccessfulClaimAmount = 100;
        int modifiedTotalSuccessfulClaimAmount = originalTotalSuccessfulClaimAmount;
        int modifiedTotalSuccessfulClaims = originalTotalSuccessfulClaims;

        for (Claim claim : claimList) {
            modifiedTotalSuccessfulClaimAmount = originalTotalSuccessfulClaimAmount - claim.getClaimAmount(); //filter out claims that is un-satisfied with sorting conditions

            if (!claim.getStatus().equals("APPROVED")) {
                modifiedTotalSuccessfulClaims = originalTotalSuccessfulClaims - 1; //filter out claims that is un-satisfied with sorting conditions
            }

        }


        //print result to check

        System.out.println( "modified total successful claim amount"+ modifiedTotalSuccessfulClaimAmount);

        System.out.println( "modified total successful claims"+ modifiedTotalSuccessfulClaims);



    }
}