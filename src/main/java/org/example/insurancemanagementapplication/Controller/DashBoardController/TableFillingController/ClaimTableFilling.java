package org.example.insurancemanagementapplication.Controller.DashBoardController.TableFillingController;

import Entity.*;
import jakarta.persistence.EntityManager;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.insurancemanagementapplication.Controller.CreationPageController.CreationPageController_Claim;
import org.example.insurancemanagementapplication.Interfaces.ClaimCreateRemove;

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
public class ClaimTableFilling implements ClaimCreateRemove {
    @FXML
    private TableView<Claim> claimTable;
    @FXML
    private  TableColumn<Claim, String> claimId;
    @FXML
    private  TableColumn<Claim, Date> creationDate;
    @FXML
    private  TableColumn<Claim, String> insuredPersonId;
    @FXML
    private  TableColumn<Claim, String> cardNumberClaimTable;
    @FXML
    private  TableColumn<Claim, String> policyOwnerClaimTable;
    @FXML
    private  TableColumn<Claim, Integer> claimAmount;
    @FXML
    private  TableColumn<Claim, Date> settlementDate;
    @FXML
    private  TableColumn<Claim, String> status;
    @FXML
    private  TableColumn<Claim, Button> claimButton;
    @FXML
    private TableColumn<Claim, Button> removeClaimButton;
    @FXML
    private  TextField claimListSearchField;
    @FXML
    private  ChoiceBox<String> sortList;
    @FXML
    private  ChoiceBox<String> statusList;
    @FXML
    private  DatePicker creationDateFrom;
    @FXML
    private  DatePicker creationDateTo;
    @FXML
    private  DatePicker settlementDateFrom;
    @FXML
    private  DatePicker settlementDateTo;
    @FXML
    private  TextField claimAmountFrom;
    @FXML
    private  TextField claimAmountTo;

    public void fillingClaimTable(EntityManager entityManager, User user, List<Claim> claims, ObservableList<Claim> claimObservableList){
        ListIterator<Claim> claimListIterator = claims.listIterator();
        while (claimListIterator.hasNext()){
            Claim claim = claimListIterator.next();

            if (!(user instanceof Dependant)){
                Button viewClaimButton = new Button();
                Button buttonRemoveClaim = new Button("Remove");
                buttonRemoveClaim.setOnAction(event -> {
                    ClaimCreateRemove.removeClaim(entityManager, claim);
                });
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
        filteringClaimTable(filteredClaimList);
        SortedList<Claim> sortedClaimList = new SortedList<>(filteredClaimList);
        sortingClaimTable(sortedClaimList);

        claimId.setCellValueFactory(new PropertyValueFactory<Claim, String>("claimId"));
        creationDate.setCellValueFactory(new PropertyValueFactory<Claim, Date>("creationDate"));
        insuredPersonId.setCellValueFactory(new PropertyValueFactory<Claim, String>("insuredPersonId"));
        cardNumberClaimTable.setCellValueFactory(new PropertyValueFactory<Claim, String>("cardNumber"));
        policyOwnerClaimTable.setCellValueFactory(new PropertyValueFactory<Claim, String>("policyOwnerId"));
        claimAmount.setCellValueFactory(new PropertyValueFactory<Claim, Integer>("claimAmount"));
        settlementDate.setCellValueFactory(new PropertyValueFactory<Claim, Date>("settlementDate"));
        status.setCellValueFactory(new PropertyValueFactory<Claim, String>("status"));
        if (!(user instanceof Dependant)){
            claimButton.setCellValueFactory(new PropertyValueFactory<Claim, Button>("claimButton"));
        }
        if (user instanceof PolicyHolder || user instanceof PolicyOwner){
            removeClaimButton.setCellValueFactory(new PropertyValueFactory<Claim, Button>("claimRemoveButton"));
        }
        claimTable.getItems().setAll(sortedClaimList);
    }

    public void sortingClaimTable(SortedList<Claim> sortedClaimList ){
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
    }

    public void filteringClaimTable(FilteredList<Claim> filteredClaimList){
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
    }
















    public ClaimTableFilling() {
    }

    public TableView<Claim> getClaimTable() {
        return claimTable;
    }

    public void setClaimTable(TableView<Claim> claimTable) {
        this.claimTable = claimTable;
    }

    public TableColumn<Claim, String> getClaimId() {
        return claimId;
    }

    public void setClaimId(TableColumn<Claim, String> claimId) {
        this.claimId = claimId;
    }

    public TableColumn<Claim, Date> getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(TableColumn<Claim, Date> creationDate) {
        this.creationDate = creationDate;
    }

    public TableColumn<Claim, String> getInsuredPersonId() {
        return insuredPersonId;
    }

    public void setInsuredPersonId(TableColumn<Claim, String> insuredPersonId) {
        this.insuredPersonId = insuredPersonId;
    }

    public TableColumn<Claim, String> getCardNumberClaimTable() {
        return cardNumberClaimTable;
    }

    public void setCardNumberClaimTable(TableColumn<Claim, String> cardNumberClaimTable) {
        this.cardNumberClaimTable = cardNumberClaimTable;
    }

    public TableColumn<Claim, String> getPolicyOwnerClaimTable() {
        return policyOwnerClaimTable;
    }

    public void setPolicyOwnerClaimTable(TableColumn<Claim, String> policyOwnerClaimTable) {
        this.policyOwnerClaimTable = policyOwnerClaimTable;
    }

    public TableColumn<Claim, Integer> getClaimAmount() {
        return claimAmount;
    }

    public void setClaimAmount(TableColumn<Claim, Integer> claimAmount) {
        this.claimAmount = claimAmount;
    }

    public TableColumn<Claim, Date> getSettlementDate() {
        return settlementDate;
    }

    public void setSettlementDate(TableColumn<Claim, Date> settlementDate) {
        this.settlementDate = settlementDate;
    }

    public TableColumn<Claim, String> getStatus() {
        return status;
    }

    public void setStatus(TableColumn<Claim, String> status) {
        this.status = status;
    }

    public TableColumn<Claim, Button> getClaimButton() {
        return claimButton;
    }

    public void setClaimButton(TableColumn<Claim, Button> claimButton) {
        this.claimButton = claimButton;
    }

    public TableColumn<Claim, Button> getRemoveClaimButton() {
        return removeClaimButton;
    }

    public void setRemoveClaimButton(TableColumn<Claim, Button> removeClaimButton) {
        this.removeClaimButton = removeClaimButton;
    }

    public TextField getClaimListSearchField() {
        return claimListSearchField;
    }

    public void setClaimListSearchField(TextField claimListSearchField) {
        this.claimListSearchField = claimListSearchField;
    }

    public ChoiceBox<String> getSortList() {
        return sortList;
    }

    public void setSortList(ChoiceBox<String> sortList) {
        this.sortList = sortList;
    }

    public ChoiceBox<String> getStatusList() {
        return statusList;
    }

    public void setStatusList(ChoiceBox<String> statusList) {
        this.statusList = statusList;
    }

    public DatePicker getCreationDateFrom() {
        return creationDateFrom;
    }

    public void setCreationDateFrom(DatePicker creationDateFrom) {
        this.creationDateFrom = creationDateFrom;
    }

    public DatePicker getCreationDateTo() {
        return creationDateTo;
    }

    public void setCreationDateTo(DatePicker creationDateTo) {
        this.creationDateTo = creationDateTo;
    }

    public DatePicker getSettlementDateFrom() {
        return settlementDateFrom;
    }

    public void setSettlementDateFrom(DatePicker settlementDateFrom) {
        this.settlementDateFrom = settlementDateFrom;
    }

    public DatePicker getSettlementDateTo() {
        return settlementDateTo;
    }

    public void setSettlementDateTo(DatePicker settlementDateTo) {
        this.settlementDateTo = settlementDateTo;
    }

    public TextField getClaimAmountFrom() {
        return claimAmountFrom;
    }

    public void setClaimAmountFrom(TextField claimAmountFrom) {
        this.claimAmountFrom = claimAmountFrom;
    }

    public TextField getClaimAmountTo() {
        return claimAmountTo;
    }

    public void setClaimAmountTo(TextField claimAmountTo) {
        this.claimAmountTo = claimAmountTo;
    }
}
