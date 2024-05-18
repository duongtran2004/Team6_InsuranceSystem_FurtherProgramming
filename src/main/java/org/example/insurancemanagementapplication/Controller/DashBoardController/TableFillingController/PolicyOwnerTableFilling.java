package org.example.insurancemanagementapplication.Controller.DashBoardController.TableFillingController;

import Entity.*;
import jakarta.persistence.EntityManager;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.example.insurancemanagementapplication.Controller.CreationAndUpdatePageController.CreationAndUpdatePageControllerPolicyHolder;
import org.example.insurancemanagementapplication.Controller.CreationAndUpdatePageController.CreationAndUpdatePageControllerPolicyOwner;
import org.example.insurancemanagementapplication.Interfaces.ClaimRead;
import org.example.insurancemanagementapplication.Interfaces.CustomerCreateRemove;
import org.example.insurancemanagementapplication.Interfaces.CustomerRead;
import org.example.insurancemanagementapplication.Interfaces.YearlyRateCalculation;
import org.example.insurancemanagementapplication.Utility.StageBuilder;

import java.util.Comparator;
import java.util.List;
import java.util.ListIterator;

/**
 * @author Luong Thanh Trung
 * @version ${}
 * @created 01/05/2024 16:11
 * @project InsuranceManagementTeamProject
 */
public class PolicyOwnerTableFilling extends PolicyHolderTableFilling {
    //TODO Create a thread that get all Policy Owners from the table  and check if new entries exist. If they do, append the new entries to the Observable List
    private ObservableList<PolicyOwner> policyOwnersObservableList = FXCollections.observableArrayList();
    @FXML
    protected TableView<PolicyOwner> policyOwnerTable;
    @FXML
    protected TableColumn<PolicyOwner, String> policyOwnerId;
    @FXML
    protected TableColumn<PolicyOwner, String> policyOwnerFullName;
    @FXML
    protected TableColumn<PolicyOwner, String> policyOwnerAddress;
    @FXML
    protected TableColumn<PolicyOwner, String> policyOwnerPhoneNumber;
    @FXML
    protected TableColumn<PolicyOwner, String> policyOwnerEmail;
    @FXML
    protected TableColumn<PolicyOwner, String> policyOwnerPassword;
    @FXML
    protected TableColumn<PolicyOwner, Button> policyOwnerUpdateInfoButton;
    @FXML
    protected TableColumn<PolicyOwner, Button> policyOwnerAddPolicyButton;
    @FXML
    protected TableColumn<PolicyOwner, Button> policyOwnerRemoveButton;
    @FXML
    protected TextField policyOwnerSearchField;
    @FXML
    protected TableColumn<PolicyOwner, Integer> policyOwnerTotalYearlyRate;
    @FXML
    private TableColumn<PolicyOwner, Integer> totalSuccessfulClaimAmountPolicyOwnerColumn;

    @FXML
    private ChoiceBox<String> policyOwnerSortBox;


    @FXML
    private Label yearlyRateSumOfAllPolicyOwnersLabel;

    private int yearlyRateSumOfAllPolicyOwnersValue = 0;

    public PolicyOwnerTableFilling(EntityManager entityManager, User user) {
        super(entityManager, user);
    }


    public void sortingPolicyOwnerTable(SortedList<PolicyOwner> sortedPolicyOwnerList) {


        //Comparator class. An instance of this class will be used as a parameter of the sort Method to define the sorting factor. In this class, the sorting factor is the claim's claim amount
        class TotalSuccessfulClaimAmountComparatorForPolicyOwner implements Comparator<PolicyOwner> {
            @Override
            public int compare(PolicyOwner firstPolicyOwner, PolicyOwner secondPolicyOwner) {
                return Integer.compare(firstPolicyOwner.getTotalSuccessfulClaimAmount(), secondPolicyOwner.getTotalSuccessfulClaimAmount());
            }

        }

        class TotalYearlyRateComparator implements Comparator<PolicyOwner> {
            @Override
            public int compare(PolicyOwner firstPolicyOwner, PolicyOwner secondPolicyOwner) {
                return Integer.compare(firstPolicyOwner.getTotalYearlyRate(), secondPolicyOwner.getTotalYearlyRate());
            }

        }
        //Total Successful Claim Amount choiceBox
        //add a listener to the sort list choice box. The listener will monitor the choice box's value to apply the correct sorting
        //not allowed to reverse a sorted list
        policyOwnerSortBox.valueProperty().addListener((observable, oldVal, newVal) -> {

            //only change the observable list if other options except "NONE
            if (!(newVal.equals("NONE"))) {
                if (newVal.equals("Sort By Total Successful Claim Amount In Ascending Order")) {

                    TotalSuccessfulClaimAmountComparatorForPolicyOwner totalSuccessfulClaimAmountComparator = new TotalSuccessfulClaimAmountComparatorForPolicyOwner();
                    sortedPolicyOwnerList.setComparator(totalSuccessfulClaimAmountComparator);
                } else if (newVal.equals("Sort By Total Successful Claim Amount In Descending Order")) {

                    TotalSuccessfulClaimAmountComparatorForPolicyOwner totalSuccessfulClaimAmountComparator = new TotalSuccessfulClaimAmountComparatorForPolicyOwner();
                    sortedPolicyOwnerList.setComparator(totalSuccessfulClaimAmountComparator.reversed());
                } else if (newVal.equals("Sort By Total Yearly Rate In Ascending Order")) {

                    TotalYearlyRateComparator totalYearlyRateComparator = new TotalYearlyRateComparator();
                    sortedPolicyOwnerList.setComparator(totalYearlyRateComparator);
                } else if (newVal.equals("Sort By Total Yearly Rate In Descending Order")) {

                    TotalYearlyRateComparator totalYearlyRateComparator = new TotalYearlyRateComparator();
                    sortedPolicyOwnerList.setComparator(totalYearlyRateComparator.reversed());
                }
            } else { //if choice = "NONE"
                sortedPolicyOwnerList.setComparator(null);
            }
        });
    }

    /**
     * Attach an event listener to the policy owner search field that filter the policy owner table according to changes in this field
     *
     * @param filteredPolicyOwnerList
     */
    public void filteringPolicyOwnerTable(FilteredList<PolicyOwner> filteredPolicyOwnerList) {
        policyOwnerSearchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredPolicyOwnerList.setPredicate(policyOwner -> {
                if (newValue.isEmpty() || newValue == null || newValue.isBlank()) {
                    return true;
                }
                String searchValue = newValue.toLowerCase();
                if (policyOwner.getId().toLowerCase().contains(searchValue)) {
                    return true;
                } else if (policyOwner.getFullName().toLowerCase().contains(searchValue)) {
                    return true;
                } else if (policyOwner.getEmail().toLowerCase().contains(searchValue)) {
                    return true;
                } else if (policyOwner.getAddress().toLowerCase().contains(searchValue)) {
                    return true;
                } else if (policyOwner.getPhoneNumber().toLowerCase().contains(searchValue)) {
                    return true;
                } else {
                    return false;
                }
            });
        });
    }

    /**
     * Mapping the columns of the policy owner tables with Policy Owner entity. Fill up the policy owner tables with data from the database
     *
     * @param entityManager
     * @param user
     * @param policyOwners
     */
    public void fillingPolicyOwnerTable(EntityManager entityManager, User user, List<PolicyOwner> policyOwners) {
        if (user instanceof SystemAdmin) {
            String[] policyOwnerSortBoxArray = {"Sort By Total Yearly Rate In Ascending Order", "Sort By Total Yearly Rate In Descending Order", "Sort By Total Successful Claim Amount In Ascending Order", "Sort By Total Successful Claim Amount In Descending Order", "NONE"};
            policyOwnerSortBox.getItems().setAll(policyOwnerSortBoxArray);
            policyOwnerSortBox.setValue("NONE"); //set default value

        }
        ListIterator<PolicyOwner> policyOwnerListIterator = policyOwners.listIterator();
        //Add policy owners to the observable list
        while (policyOwnerListIterator.hasNext()) {

            PolicyOwner policyOwner = policyOwnerListIterator.next();

            //reassign object from database
            policyOwner = entityManager.find(PolicyOwner.class, policyOwner.getId());
            //call interface method to get all claims of a policy owner
            List<Claim> policyOwnerClaimList = ClaimRead.getAllClaimsFromBeneficiariesOfAPolicyOwner(entityManager, policyOwner.getId());

            PolicyOwner finalPolicyOwner = policyOwner;
            policyOwner.setTotalSuccessfulClaimAmount(ClaimRead.getTotalSuccessfulClaimAmountMadeByAPolicyOwner(policyOwnerClaimList));
            Button buttonUpdateInfo = new Button("Update Info");
            Button buttonAddPolicy = new Button("Add Policy");
            Button buttonRemove = new Button("Remove");
            buttonList.add(buttonAddPolicy);
            buttonList.add(buttonUpdateInfo);
            buttonList.add(buttonRemove);
            //Only a system admin has access to the update info, add policy, and remove button
            if (user instanceof SystemAdmin) {
                //The Update Info Button will create a CreationPage Controller for the policy owner in update mode by passing in the policy owner object
                //It will then open the Policy Owner Creation Page
                buttonUpdateInfo.setOnAction(event -> {
                    CreationAndUpdatePageControllerPolicyOwner creationPageControllerPolicyOwner = new CreationAndUpdatePageControllerPolicyOwner(entityManager, user, finalPolicyOwner);
                    StageBuilder.showStage((Stage) buttonUpdateInfo.getScene().getWindow(), creationPageControllerPolicyOwner, "PolicyOwnerCreationAndUpdatePage.fxml", "Policy Owner Update");

                });

                policyOwner.setUpdateInfoButton(buttonUpdateInfo);
                //The addPolicyHolder button will create a Policy Holder CreationPage Controller in creation mode by passing the policy owner object
                //It will then open the Policy Holder Creation Form

                buttonAddPolicy.setOnAction(event -> {
                    CreationAndUpdatePageControllerPolicyHolder creationPageControllerPolicyHolder = new CreationAndUpdatePageControllerPolicyHolder(entityManager, user, finalPolicyOwner);
                    StageBuilder.showStage((Stage) buttonAddPolicy.getScene().getWindow(), creationPageControllerPolicyHolder, "PolicyHolderCreationAndUpdatePage.fxml", "Policy Creation");

                });

                //The remove button will remove its policy owner button from the database
                policyOwner.setRemoveButton(buttonRemove);
                buttonRemove.setOnAction(event -> {
                    CustomerCreateRemove.removePolicyOwner(entityManager, finalPolicyOwner);
                });
                policyOwner.setAddPolicyButton(buttonAddPolicy);

                // Calculate the total yearly rate for the policy owner

                //get beneficiaries list of policy owner from the database
                List<Beneficiaries> beneficiariesList = CustomerRead.getAllBeneficiariesOfAPolicyOwner(entityManager, policyOwner.getId());
                int yearlyRate = YearlyRateCalculation.calculateYearlyRateOfAPolicyOwner(beneficiariesList);
                yearlyRateSumOfAllPolicyOwnersValue = yearlyRateSumOfAllPolicyOwnersValue + yearlyRate;
                policyOwner.setTotalYearlyRate(yearlyRate);
            }

            policyOwnersObservableList.add(policyOwner);
        }


        policyOwnerId.setCellValueFactory(new PropertyValueFactory<PolicyOwner, String>("id"));
        policyOwnerFullName.setCellValueFactory(new PropertyValueFactory<PolicyOwner, String>("fullName"));
        policyOwnerAddress.setCellValueFactory(new PropertyValueFactory<PolicyOwner, String>("address"));
        policyOwnerEmail.setCellValueFactory(new PropertyValueFactory<PolicyOwner, String>("email"));
        policyOwnerPassword.setCellValueFactory(new PropertyValueFactory<PolicyOwner, String>("password"));
        policyOwnerPhoneNumber.setCellValueFactory(new PropertyValueFactory<PolicyOwner, String>("phoneNumber"));

        if (user instanceof SystemAdmin) {
            policyOwnerTotalYearlyRate.setCellValueFactory(new PropertyValueFactory<PolicyOwner, Integer>("totalYearlyRate"));
            policyOwnerUpdateInfoButton.setCellValueFactory(new PropertyValueFactory<PolicyOwner, Button>("updateInfoButton"));
            policyOwnerAddPolicyButton.setCellValueFactory(new PropertyValueFactory<PolicyOwner, Button>("addPolicyButton"));
            policyOwnerRemoveButton.setCellValueFactory(new PropertyValueFactory<PolicyOwner, Button>("removeButton"));
            totalSuccessfulClaimAmountPolicyOwnerColumn.setCellValueFactory(new PropertyValueFactory<PolicyOwner, Integer>("totalSuccessfulClaimAmount"));

            //Putting values into the sorting  choice box


            //display sum of yearly rate from all policy owners


            Platform.runLater(() -> {
                // do your GUI stuff here
                yearlyRateSumOfAllPolicyOwnersLabel.setText(String.valueOf(yearlyRateSumOfAllPolicyOwnersValue));
            });

        }
        FilteredList<PolicyOwner> filteredPolicyOwnerList = new FilteredList<>(policyOwnersObservableList, b -> true);
        filteringPolicyOwnerTable(filteredPolicyOwnerList);
        SortedList<PolicyOwner> sortedPolicyOwners = new SortedList<>(filteredPolicyOwnerList);
        if (user instanceof SystemAdmin) {
            sortingPolicyOwnerTable(sortedPolicyOwners);
        }

        policyOwnerTable.setItems(sortedPolicyOwners);

    }

}
//Inner Class for thread

