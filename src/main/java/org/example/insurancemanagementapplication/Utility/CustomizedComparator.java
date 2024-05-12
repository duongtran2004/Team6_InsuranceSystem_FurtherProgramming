package org.example.insurancemanagementapplication.Utility;

import Entity.Claim;

import java.util.Comparator;

public class CustomizedComparator {
    //Comparator class. An instance of this class will be used as a parameter of the sort Method to define the sorting factor. In this class, the sorting factor is the claim's creation date
   public static class ClaimCreationDateComparator implements Comparator<Claim> {
        @Override
        public int compare(Claim firstClaim, Claim secondClaim) {
            long firstClaimTime = firstClaim.getCreationDate().getTime();
            long secondClaimTime = secondClaim.getCreationDate().getTime();
            return Long.compare(firstClaimTime, secondClaimTime);
        }
    }
    //Comparator class. An instance of this class will be used as a parameter of the sort Method to define the sorting factor. In this class, the sorting factor is the claim's settlement date
    public static class ClaimSettlementDateComparator implements Comparator<Claim> {
        @Override
        public int compare(Claim firstClaim, Claim secondClaim) {
            long firstClaimTime = firstClaim.getSettlementDate().getTime();
            long secondClaimTime = secondClaim.getSettlementDate().getTime();
            return Long.compare(firstClaimTime, secondClaimTime);
        }
    }

    //Comparator class. An instance of this class will be used as a parameter of the sort Method to define the sorting factor. In this class, the sorting factor is the claim's claim amount
   public static class ClaimAmountComparator implements Comparator<Claim> {
        @Override
        public int compare(Claim firstClaim, Claim secondClaim) {
            return Integer.compare(firstClaim.getClaimAmount(), secondClaim.getClaimAmount());
        }
    }
}
