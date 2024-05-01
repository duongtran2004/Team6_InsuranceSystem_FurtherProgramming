package org.example.insurancemanagementapplication.Controller.DashBoardController.TableFillingController;

import Entity.Claim;
import Entity.Dependant;
import Entity.SystemAdmin;
import Entity.User;
import jakarta.persistence.EntityManager;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.insurancemanagementapplication.Controller.CreationPageController.CreationPageController_Claim;

import java.sql.Date;
import java.util.Comparator;
import java.util.List;
import java.util.ListIterator;

/**
 * @author Luong Thanh Trung
 * @version ${}
 * @created 01/05/2024 15:21
 * @project InsuranceManagementTeamProject
 */
public class ClaimTableFilling {
    @FXML
    protected static TableView<Claim> claimTable;
    @FXML
    protected static TableColumn<Claim, String> claimId;
    @FXML
    protected static TableColumn<Claim, Date> creationDate;
    @FXML
    protected static TableColumn<Claim, String> insuredPersonId;
    @FXML
    protected static TableColumn<Claim, String> cardNumberClaimTable;
    @FXML
    protected static TableColumn<Claim, String> policyOwnerClaimTable;
    @FXML
    protected static TableColumn<Claim, Float> claimAmount;
    @FXML
    protected static TableColumn<Claim, Date> settlementDate;
    @FXML
    protected static TableColumn<Claim, String> status;
    @FXML
    protected static TableColumn<Claim, Button> claimButton;
    @FXML
    protected static TextField claimListSearchField;
    @FXML
    protected static ChoiceBox<String> sortList;
    @FXML
    protected static ChoiceBox<String> statusList;
    @FXML
    protected static DatePicker creationDateFrom;
    @FXML
    protected static DatePicker creationDateTo;
    @FXML
    protected static DatePicker settlementDateFrom;
    @FXML
    protected static DatePicker settlementDateTo;
    @FXML
    protected static TextField claimAmountFrom;
    @FXML
    protected static TextField claimAmountTo;

    protected static void fillingClaimTable(EntityManager entityManager, User user, List<Claim> claims, ObservableList<Claim> claimObservableList){
        ListIterator<Claim> claimListIterator = claims.listIterator();
        while (claimListIterator.hasNext()){
            Claim claim = claimListIterator.next();

            if (!(user instanceof Dependant)){
                Button viewClaimButton = new Button();
                if (user instanceof SystemAdmin){
                    viewClaimButton.setText("View Claim");
                }
                else {
                    viewClaimButton.setText("Update Claim");
                }
                viewClaimButton.setOnAction(event -> {
                    CreationPageController_Claim creationPageControllerClaim = new CreationPageController_Claim(entityManager, user, claim);
                });
                claim.setClaimButton(viewClaimButton);
            }

            claimObservableList.add(claim);

        }
        FilteredList<Claim> filteredClaimList = new FilteredList<>(claimObservableList, b -> true);
        claimListSearchField.textProperty().addListener((observable, oldValue, newValue)->{
            filteredClaimList.setPredicate(claim -> {
                String searchValue = newValue.toLowerCase();
                if (newValue.isEmpty() || newValue.isBlank() || newValue == null){
                    return true;
                }
                else if (claim.getClaimId().equals(searchValue)){
                    return true;
                }
                else if (claim.getInsuredPersonId().equals(searchValue)){
                    return true;
                } else if (claim.getCardNumber().equals(searchValue)){
                    return true;
                } else if (claim.getPolicyOwnerId().equals(searchValue)){
                    return true;
                } else if (claim.getStatus().equals(searchValue)){
                    return true;
                } else{
                    return false;
                }
            });
        });

        creationDateFrom.valueProperty().addListener((observable, oldDate, newDate)->{
            filteredClaimList.setPredicate(claim -> {
                if (newDate == null){
                    return true;
                }
                else if (!claim.getCreationDate().toLocalDate().isBefore(newDate)){
                    return true;
                }
                else {
                    return false;
                }
            });
        });

        creationDateTo.valueProperty().addListener((observable, oldDate, newDate)->{
            filteredClaimList.setPredicate(claim -> {
                if (newDate == null){
                    return true;
                }
                else if (!claim.getCreationDate().toLocalDate().isAfter(newDate)){
                    return true;
                }
                else {
                    return false;
                }
            });
        });

        settlementDateFrom.valueProperty().addListener((observable, oldDate, newDate)->{
            filteredClaimList.setPredicate(claim -> {
                if (newDate == null){
                    return true;
                }
                else if (!claim.getSettlementDate().toLocalDate().isBefore(newDate)){
                    return true;
                }
                else {
                    return false;
                }
            });
        });


        settlementDateTo.valueProperty().addListener((observable, oldDate, newDate)->{
            filteredClaimList.setPredicate(claim -> {
                if (newDate == null){
                    return true;
                }
                else if (!claim.getSettlementDate().toLocalDate().isAfter(newDate)){
                    return true;
                }
                else {
                    return false;
                }
            });
        });

        claimAmountFrom.textProperty().addListener((observable, oldValue, newValue)->{
            filteredClaimList.setPredicate(claim -> {
                if (newValue == null || newValue.isBlank() || newValue.isEmpty()){
                    return true;
                }
                else {
                    try{
                        if (Float.parseFloat(newValue) <= claim.getClaimAmount()){
                            return true;
                        }
                        else {
                            return false;
                        }
                    } catch (NumberFormatException e){
                        return false;
                    }
                }

            });
        });

        claimAmountTo.textProperty().addListener((observable, oldValue, newValue)->{
            filteredClaimList.setPredicate(claim -> {
                if (newValue == null || newValue.isBlank() || newValue.isEmpty()){
                    return true;
                }
                else {
                    try{
                        if (Float.parseFloat(newValue) >= claim.getClaimAmount()){
                            return true;
                        }
                        else {
                            return false;
                        }
                    } catch (NumberFormatException e){
                        return false;
                    }
                }

            });
        });

        statusList.valueProperty().addListener((observable, oldVal, newVal)->{
            filteredClaimList.setPredicate(claim -> {
                if (newVal == null){
                    return true;
                }
                else if (claim.getStatus().equals(newVal)){
                    return true;
                }
                else {
                    return false;
                }

            });
        });
        class ClaimCreationDateComparator implements Comparator<Claim> {
            @Override
            public int compare(Claim firstClaim, Claim secondClaim) {
                long firstClaimTime = firstClaim.getCreationDate().getTime();
                long secondClaimTime = secondClaim.getCreationDate().getTime();
                return Long.compare(firstClaimTime, secondClaimTime);
            }

        }

        class ClaimSettlementDateComparator implements Comparator<Claim>{

            @Override
            public int compare(Claim firstClaim, Claim secondClaim) {
                long firstClaimTime = firstClaim.getSettlementDate().getTime();
                long secondClaimTime = secondClaim.getSettlementDate().getTime();
                return Long.compare(firstClaimTime, secondClaimTime);
            }

        }

        class ClaimAmountComparator implements Comparator<Claim>{

            @Override
            public int compare(Claim firstClaim, Claim secondClaim) {
                return Float.compare(firstClaim.getClaimAmount(), secondClaim.getClaimAmount());
            }
        }

        SortedList<Claim> sortedClaimList = new SortedList<>(filteredClaimList);
        sortList.valueProperty().addListener((observable, oldVal, newVal)->{
            if (newVal.equals("Sort By Creation Date In Ascending Order")){
                ClaimCreationDateComparator claimCreationDateComparator = new ClaimCreationDateComparator();
                sortedClaimList.sort(claimCreationDateComparator);
            }
            else if (newVal.equals("Sort By Creation Date In Descending Order")){
                ClaimCreationDateComparator claimCreationDateComparator = new ClaimCreationDateComparator();
                sortedClaimList.sort(claimCreationDateComparator);
                sortedClaimList.reversed();
            }
            else if (newVal.equals("Sort By Settlement Date In Ascending Order")){
                ClaimSettlementDateComparator claimSettlementDateComparator = new ClaimSettlementDateComparator();
                sortedClaimList.sort(claimSettlementDateComparator);
            }
            else if (newVal.equals("Sort By Settlement Date In Descending Order")){
                ClaimSettlementDateComparator claimSettlementDateComparator = new ClaimSettlementDateComparator();
                sortedClaimList.sort(claimSettlementDateComparator);
                sortedClaimList.reversed();
            }

            else if (newVal.equals("Sort by Claim Amount In Ascending Order")){
                ClaimAmountComparator claimAmountComparator = new ClaimAmountComparator();
                sortedClaimList.sort(claimAmountComparator);
            }
            else if (newVal.equals("Sort by Claim Amount In Descending Order")){
                ClaimAmountComparator claimAmountComparator = new ClaimAmountComparator();
                sortedClaimList.sort(claimAmountComparator);
                sortedClaimList.reversed();
            }

        });
        claimId.setCellValueFactory(new PropertyValueFactory<Claim, String>("claimId"));
        creationDate.setCellValueFactory(new PropertyValueFactory<Claim, Date>("creationDate"));
        insuredPersonId.setCellValueFactory(new PropertyValueFactory<Claim, String>("insuredPersonId"));
        cardNumberClaimTable.setCellValueFactory(new PropertyValueFactory<Claim, String>("cardNumber"));
        policyOwnerClaimTable.setCellValueFactory(new PropertyValueFactory<Claim, String>("policyOwnerId"));
        claimAmount.setCellValueFactory(new PropertyValueFactory<Claim, Float>("claimAmount"));
        settlementDate.setCellValueFactory(new PropertyValueFactory<Claim, Date>("settlementDate"));
        status.setCellValueFactory(new PropertyValueFactory<Claim, String>("status"));
        if (!(user instanceof Dependant)){
            claimButton.setCellValueFactory(new PropertyValueFactory<Claim, Button>("claimButton"));
        }
        claimTable.getItems().setAll(sortedClaimList);
    }
}
