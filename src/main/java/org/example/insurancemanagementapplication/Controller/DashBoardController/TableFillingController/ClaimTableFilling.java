package org.example.insurancemanagementapplication.Controller.DashBoardController.TableFillingController;

import Entity.*;
import jakarta.persistence.EntityManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.insurancemanagementapplication.Controller.CreationPageController.CreationPageController_Claim;
import org.example.insurancemanagementapplication.Interfaces.ClaimCreateRemove;
import org.example.insurancemanagementapplication.Interfaces.ClaimRead;

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

/**
 * The most encompassing class that all dashboard controller inherits from.This class contains a function that inhabits
 * the claim tables of all users' dashboards with data.
 * It also contains a method that automatically fill up the form on top of each dashboard with user's data.
 */

public class ClaimTableFilling implements ClaimCreateRemove {
    protected final EntityManager entityManager;
    protected final User user;
    //TODO Create a thread that runs in a selected interval that get all claims from the database and check if new entries exist. If they do, append the new entries to the Observable List
    private ObservableList<Claim> claimObservableList = FXCollections.observableArrayList();

    @FXML
    protected TextField userIdField;
    @FXML
    protected TextField fullNameField;
    @FXML
    protected TextField addressField;
    @FXML
    protected TextField phoneNumberField;
    @FXML
    protected TextField emailField;
    @FXML
    protected TextField passwordField;
    @FXML
    protected TextField passwordValidationField;
    @FXML
    protected Label errorContainer;


    //Table View and Columns
    @FXML
    protected TableView<Claim> claimTable;
    //The table column containing claims' ids
    @FXML
    protected TableColumn<Claim, String> claimId;
    @FXML
    protected TableColumn<Claim, Date> creationDate;
    @FXML
    protected TableColumn<Claim, String> insuredPersonId;
    @FXML
    protected TableColumn<Claim, String> cardNumberClaimTable;
    @FXML
    protected TableColumn<Claim, String> policyOwnerClaimTable;
    @FXML
    protected TableColumn<Claim, Integer> claimAmount;
    @FXML
    protected TableColumn<Claim, Date> settlementDate;
    @FXML
    protected TableColumn<Claim, String> status;
    @FXML
    protected TableColumn<Claim, Button> claimButton;
    @FXML
    protected TableColumn<Claim, Button> claimRemoveButton;
    @FXML
    protected TextField claimListSearchField;
    //User will select what sorting they would like to use
    @FXML
    protected ChoiceBox<String> sortList;
    //User will select the status they want to see
    @FXML
    protected ChoiceBox<String> statusList;
    //User will input the earliest settlement date they want to see
    @FXML
    protected DatePicker creationDateFrom;

    //User will input the latest settlement creation they want to see
    @FXML
    protected DatePicker creationDateTo;
    //User will input the earliest settlement date they want to see
    @FXML
    protected DatePicker settlementDateFrom;
    //User will input the latest settlement date they want to see
    @FXML
    protected DatePicker settlementDateTo;
    //User will input the min claim amount that they want to see
    @FXML
    protected TextField claimAmountFrom;
    //User will input the max claim amount they want to see
    @FXML
    protected TextField claimAmountTo;


    /**
     * Filling the form on top of the dashboard with user's information. The fields are disabled unless the user is a system admin
     */
    //AKA: personal info bar at the top
    public void userFillingData() {
        userIdField.setText(user.getId());
        userIdField.setDisable(true);
        fullNameField.setText(user.getFullName());
        fullNameField.setDisable(true);
        addressField.setText(user.getAddress());
        phoneNumberField.setText(user.getPhoneNumber());
        emailField.setText(user.getEmail());
        passwordField.setText(user.getPassword());
        passwordValidationField.setText(user.getPassword());
        if (!(user instanceof SystemAdmin)) {
            addressField.setDisable(true);
            phoneNumberField.setDisable(true);
            emailField.setDisable(true);
            passwordField.setDisable(true);
            passwordValidationField.setDisable(true);
        }
    }

    //constructor
    public ClaimTableFilling(EntityManager entityManager, User user) {
        this.entityManager = entityManager;
        this.user = user;
    }

    //This method adds event listener to sorting choice boxes and fields that sort the claim tables when their values change.
    public void sortingClaimTable(SortedList<Claim> sortedClaimList) {

        //Comparator class. An instance of this class will be used as a parameter of the sort Method to define the sorting factor. In this class, the sorting factor is the claim's creation date
        class ClaimCreationDateComparator implements Comparator<Claim> {
            @Override
            public int compare(Claim firstClaim, Claim secondClaim) {
                long firstClaimTime = firstClaim.getCreationDate().getTime();
                long secondClaimTime = secondClaim.getCreationDate().getTime();
                return Long.compare(firstClaimTime, secondClaimTime);
            }
        }
        //Comparator class. An instance of this class will be used as a parameter of the sort Method to define the sorting factor. In this class, the sorting factor is the claim's settlement date
        class ClaimSettlementDateComparator implements Comparator<Claim> {
            @Override
            public int compare(Claim firstClaim, Claim secondClaim) {
                long firstClaimTime = firstClaim.getSettlementDate().getTime();
                long secondClaimTime = secondClaim.getSettlementDate().getTime();
                return Long.compare(firstClaimTime, secondClaimTime);
            }
        }

        //Comparator class. An instance of this class will be used as a parameter of the sort Method to define the sorting factor. In this class, the sorting factor is the claim's claim amount
        class ClaimAmountComparator implements Comparator<Claim> {
            @Override
            public int compare(Claim firstClaim, Claim secondClaim) {
                return Float.compare(firstClaim.getClaimAmount(), secondClaim.getClaimAmount());
            }
        }
        //claimSorting choiceBox
        //add a listener to the sort list choice box. The listener will monitor the choice box's value to apply the correct sorting
        sortList.valueProperty().addListener((observable, oldVal, newVal) -> {
            if (newVal.equals("Sort By Creation Date In Ascending Order")) {
                ClaimCreationDateComparator claimCreationDateComparator = new ClaimCreationDateComparator();
                sortedClaimList.sort(claimCreationDateComparator);
            } else if (newVal.equals("Sort By Creation Date In Descending Order")) {
                ClaimCreationDateComparator claimCreationDateComparator = new ClaimCreationDateComparator();
                sortedClaimList.sort(claimCreationDateComparator);
                sortedClaimList.reversed();
            } else if (newVal.equals("Sort By Settlement Date In Ascending Order")) {
                ClaimSettlementDateComparator claimSettlementDateComparator = new ClaimSettlementDateComparator();
                sortedClaimList.sort(claimSettlementDateComparator);
            } else if (newVal.equals("Sort By Settlement Date In Descending Order")) {
                ClaimSettlementDateComparator claimSettlementDateComparator = new ClaimSettlementDateComparator();
                sortedClaimList.sort(claimSettlementDateComparator);
                sortedClaimList.reversed();
            } else if (newVal.equals("Sort by Claim Amount In Ascending Order")) {
                ClaimAmountComparator claimAmountComparator = new ClaimAmountComparator();
                sortedClaimList.sort(claimAmountComparator);
            } else if (newVal.equals("Sort by Claim Amount In Descending Order")) {
                ClaimAmountComparator claimAmountComparator = new ClaimAmountComparator();
                sortedClaimList.sort(claimAmountComparator);
                sortedClaimList.reversed();
            }

        });
    }

    //This method adds handlers to changes in filtering fields that filter the claim tables when their value changes.
    public void filteringClaimTable(FilteredList<Claim> filteredClaimList) {
        //Add a handler to the search field
        claimListSearchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredClaimList.setPredicate(claim -> {
                String searchValue = newValue.toLowerCase();
                if (newValue.isEmpty() || newValue.isBlank() || newValue == null) {
                    return true;
                } else if (claim.getClaimId().toLowerCase().contains(searchValue)) {
                    return true;
                } else if (claim.getInsuredPersonId().toLowerCase().contains(searchValue)) {
                    return true;
                } else if (claim.getCardNumber().toLowerCase().contains(searchValue)) {
                    return true;
                } else if (claim.getPolicyOwnerId().toLowerCase().contains(searchValue)) {
                    return true;
                } else if (claim.getStatus().toLowerCase().contains(searchValue)) {
                    return true;
                } else {
                    return false;
                }
            });
        });
        //Add a handler to the creationDateFrom field
        creationDateFrom.valueProperty().addListener((observable, oldDate, newDate) -> {
            filteredClaimList.setPredicate(claim -> {
                if (newDate == null) {
                    return true;
                } else if (!claim.getCreationDate().toLocalDate().isBefore(newDate)) {
                    return true;
                } else {
                    return false;
                }
            });
        });
        //Add a handler to the creationDateTo field
        creationDateTo.valueProperty().addListener((observable, oldDate, newDate) -> {
            filteredClaimList.setPredicate(claim -> {
                if (newDate == null) {
                    return true;
                } else if (!claim.getCreationDate().toLocalDate().isAfter(newDate)) {
                    return true;
                } else {
                    return false;
                }
            });
        });
        //Add a handler to the settlementDateFrom field
        settlementDateFrom.valueProperty().addListener((observable, oldDate, newDate) -> {
            filteredClaimList.setPredicate(claim -> {
                if (newDate == null) {
                    return true;
                } else if (!claim.getSettlementDate().toLocalDate().isBefore(newDate)) {
                    return true;
                } else {
                    return false;
                }
            });
        });
        //Add a handler to the settlementDateTo field.
        settlementDateTo.valueProperty().addListener((observable, oldDate, newDate) -> {
            filteredClaimList.setPredicate(claim -> {
                if (newDate == null) {
                    return true;
                } else if (!claim.getSettlementDate().toLocalDate().isAfter(newDate)) {
                    return true;
                } else {
                    return false;
                }
            });
        });
        //Add a handler to the claimAmountFrom field.
        claimAmountFrom.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredClaimList.setPredicate(claim -> {
                if (newValue == null || newValue.isBlank() || newValue.isEmpty()) {
                    return true;
                } else {
                    try {
                        if (Float.parseFloat(newValue) <= claim.getClaimAmount()) {
                            return true;
                        } else {
                            return false;
                        }
                    } catch (NumberFormatException e) {
                        return false;
                    }
                }

            });
        });
        //Add a handler to the claimAmountTo field
        claimAmountTo.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredClaimList.setPredicate(claim -> {
                if (newValue == null || newValue.isBlank() || newValue.isEmpty()) {
                    return true;
                } else {
                    try {
                        if (Float.parseFloat(newValue) >= claim.getClaimAmount()) {
                            return true;
                        } else {
                            return false;
                        }
                    } catch (NumberFormatException e) {
                        return false;
                    }
                }

            });
        });
        //Add a handler to the statusList choice box
        statusList.valueProperty().addListener((observable, oldVal, newVal) -> {
            filteredClaimList.setPredicate(claim -> {
                if (newVal == null) {
                    return true;
                } else if (claim.getStatus().equals(newVal)) {
                    return true;
                } else {
                    return false;
                }

            });
        });
    }

    //This method maps table's columns with entity's fields and fill the table up with data.
    public void fillingClaimTable(EntityManager entityManager, User user, List<Claim> claims) {
        //Putting values into the statusList choice box
        String[] statusArray = {"NEW", "PROCESSING", "NEED INFO", "APPROVED", "REJECTED"};
        statusList.getItems().setAll(statusArray);
        //Putting values into the sortList choice box
        String[] sortArray = {"Sort By Creation Date In Ascending Order", "Sort By Creation Date In Descending Order", "Sort By Settlement Date In Ascending Order", "Sort By Settlement Date In Descending Order", "Sort by Claim Amount In Ascending Order", "Sort by Claim Amount In Descending Order"};
        sortList.getItems().setAll(sortArray);
        ListIterator<Claim> claimListIterator = claims.listIterator();
        //Adding Claims to the claim observable list
        while (claimListIterator.hasNext()) {
            Claim claim = claimListIterator.next();

            //Only non-dependant users get to perform actions on the claim table
            if (!(user instanceof Dependant)) {
                //Creating a button whose functionaility depends on the type of user.
                Button claimButton = new Button();
                //Creating a Button to remove a claim
                Button buttonRemoveClaim = new Button("Remove");
                buttonRemoveClaim.setOnAction(event -> {
                    ClaimCreateRemove.removeClaim(entityManager, claim);
                });
                //If the user is a system admin the button will have "View Claim" text.
                if (user instanceof SystemAdmin) {
                    claimButton.setText("View Claim");
                }
                //If the user is other types of users, the button will have the "Update Claim" text
                else {
                    claimButton.setText("Update Claim");
                }
                //Binding a handler to the onClick event.
                claimButton.setOnAction(event -> {
                    //Creating the Controller for Claim Creation Page in update mode by passing the claim into the constructor
                    CreationPageController_Claim creationPageControllerClaim = new CreationPageController_Claim(entityManager, user, claim);
                });
                claim.setClaimButton(claimButton);
            }

            claimObservableList.add(claim);

        }
        FilteredList<Claim> filteredClaimList = new FilteredList<>(claimObservableList, b -> true);
        //Adding to handlers to changes in filtering fields and Search Box.
        filteringClaimTable(filteredClaimList);
        SortedList<Claim> sortedClaimList = new SortedList<>(filteredClaimList);
        //Adding handlers to changes in sorting field
        sortingClaimTable(sortedClaimList);

        //Mapping table columns to Entity Attributes
        claimId.setCellValueFactory(new PropertyValueFactory<Claim, String>("claimId"));
        creationDate.setCellValueFactory(new PropertyValueFactory<Claim, Date>("creationDate"));
        insuredPersonId.setCellValueFactory(new PropertyValueFactory<Claim, String>("insuredPersonId"));
        cardNumberClaimTable.setCellValueFactory(new PropertyValueFactory<Claim, String>("cardNumber"));
        policyOwnerClaimTable.setCellValueFactory(new PropertyValueFactory<Claim, String>("policyOwnerId"));
        claimAmount.setCellValueFactory(new PropertyValueFactory<Claim, Integer>("claimAmount"));
        settlementDate.setCellValueFactory(new PropertyValueFactory<Claim, Date>("settlementDate"));
        status.setCellValueFactory(new PropertyValueFactory<Claim, String>("status"));
        if (!(user instanceof Dependant)) {
            //Dependants dont have access to the claim buttons. The claimButton attribute of the Claim entity is left out in the mapping if user is a dependant
            claimButton.setCellValueFactory(new PropertyValueFactory<Claim, Button>("claimButton"));
        }
        if (user instanceof PolicyHolder || user instanceof PolicyOwner) {
            //System Admins, Insurance Managers, and Insurance Surveyors are not allowed to remove Claims. So they do not have access to the removeClaim button
            claimRemoveButton.setCellValueFactory(new PropertyValueFactory<Claim, Button>("claimRemoveButton"));
        }
        claimTable.setItems(sortedClaimList);
    }


    public ClaimTableFilling(EntityManager entityManager, SystemAdmin user) {
        this.entityManager = entityManager;
        this.user = user;
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


    public TableColumn<Claim, Button> getRemoveClaimButton() {
        return claimRemoveButton;
    }

    public void setRemoveClaimButton(TableColumn<Claim, Button> removeClaimButton) {
        this.claimRemoveButton = removeClaimButton;
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

//Inner class for thread
class ClaimTableFillingThread extends Thread {
    private EntityManager entityManager;
    private User user;

    public ClaimTableFillingThread(EntityManager entityManager, User user) {
        this.entityManager = entityManager;
        this.user = user;
    }

    public static void claimTableFillingThreadForDependant(EntityManager entityManager, User user) {
        ClaimTableFilling claimTableFilling = new ClaimTableFilling(entityManager, user);
        claimTableFilling.fillingClaimTable(entityManager, user, ClaimRead.getAllClaimsFromABeneficiary(entityManager, user.getId()));
    }

    public static void claimTableFillingThreadForPolicyHolder(EntityManager entityManager, User user) {
        ClaimTableFilling claimTableFilling = new ClaimTableFilling(entityManager, user);
        claimTableFilling.fillingClaimTable(entityManager, user, ClaimRead.getAllClaimsFromABeneficiary(entityManager, user.getId()));
    }

    public static void claimTableFillingThreadForPolicyOwner(EntityManager entityManager, User user) {
        ClaimTableFilling claimTableFilling = new ClaimTableFilling(entityManager, user);
        claimTableFilling.fillingClaimTable(entityManager, user, ClaimRead.getAllClaimsFromBeneficiariesOfAPolicyOwner(entityManager, user.getId()));
    }

    public static void claimTableFillingThreadForInsuranceSurveyor(EntityManager entityManager, User user) {
        ClaimTableFilling claimTableFilling = new ClaimTableFilling(entityManager, user);
        claimTableFilling.fillingClaimTable(entityManager, user, ClaimRead.getAllClaimsProcessByAnInsuranceSurveyor(entityManager, user.getId()));
    }

    public static void claimTableFillingThreadForInsuranceManager(EntityManager entityManager, User user) {
        ClaimTableFilling claimTableFilling = new ClaimTableFilling(entityManager, user);
        claimTableFilling.fillingClaimTable(entityManager, user, ClaimRead.getAllClaimsProcessByAnInsuranceManager(entityManager, user.getId()));
    }

    public static void claimTableFillingThreadForSystemAdmin(EntityManager entityManager, User user) {
        ClaimTableFilling claimTableFilling = new ClaimTableFilling(entityManager, user);
        claimTableFilling.fillingClaimTable(entityManager, user, ClaimRead.getAllClaims(entityManager));
    }

    @Override
    public void run() {
        // Fill the table based on the specific logic
        // For example:
        // DependantTableFilling filling = new DependantTableFilling(entityManager, user);
        // filling.fillingDependantTable(entityManager, user, dependants);
        // Adjust the method call based on your actual implementation

    }
}

