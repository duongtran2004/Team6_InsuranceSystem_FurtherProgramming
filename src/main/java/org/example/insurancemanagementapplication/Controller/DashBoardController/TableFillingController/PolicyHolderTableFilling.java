package org.example.insurancemanagementapplication.Controller.DashBoardController.TableFillingController;

import Entity.*;
import jakarta.persistence.EntityManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.example.insurancemanagementapplication.Controller.CreationAndUpdatePageController.CreationAndUpdatePageControllerClaim;
import org.example.insurancemanagementapplication.Controller.CreationAndUpdatePageController.CreationAndUpdatePageControllerDependant;
import org.example.insurancemanagementapplication.Controller.CreationAndUpdatePageController.CreationAndUpdatePageControllerPolicyHolder;
import org.example.insurancemanagementapplication.Interfaces.ClaimRead;
import org.example.insurancemanagementapplication.Interfaces.CustomerCreateRemove;
import org.example.insurancemanagementapplication.Utility.StageBuilder;

import java.util.List;
import java.util.ListIterator;

/**
 * @author Luong Thanh Trung
 * @version ${}
 * @created 01/05/2024 15:56
 * @project InsuranceManagementTeamProject
 */
public class PolicyHolderTableFilling extends InsuranceCardTableFilling {
    //TODO Create a thread that get all Policy Holders from the table  and check if new entries exist. If they do, append the new entries to the Observable List
    private ObservableList<PolicyHolder> policyHoldersObservableList = FXCollections.observableArrayList();
    @FXML
    protected TableView<PolicyHolder> policyHolderTable;
    @FXML
    protected TableColumn<PolicyHolder, String> policyHolderId;
    @FXML
    protected TableColumn<PolicyHolder, String> policyHolderFullName;
    @FXML
    protected TableColumn<PolicyHolder, String> policyHolderAddress;
    @FXML
    protected TableColumn<PolicyHolder, String> policyHolderPhoneNumber;
    @FXML
    protected TableColumn<PolicyHolder, String> policyHolderEmail;
    @FXML
    protected TableColumn<PolicyHolder, String> policyHolderPassword;
    @FXML
    protected TableColumn<PolicyHolder, String> policyOwnerHolderTable;
    @FXML
    protected TableColumn<PolicyHolder, String> cardNumberHolderTable;
    @FXML
    protected TableColumn<PolicyHolder, Button> policyHolderUpdateInfoButton;
    @FXML
    protected TableColumn<PolicyHolder, Button> policyHolderAddDependantButton;
    @FXML
    protected TableColumn<PolicyHolder, Button> policyHolderAddClaimButton;
    @FXML
    protected TableColumn<PolicyHolder, Button> policyHolderRemoveButton;
    @FXML
    protected TextField policyHolderSearchField;
    @FXML
    private TableColumn<PolicyHolder, Integer> totalSuccessfulClaimAmountPolicyHolderColumn;

    public PolicyHolderTableFilling(EntityManager entityManager, User user) {
        super(entityManager, user);
    }

    /**
     * Attach an event listener to the policy holder search field that filter the policy holder table according to changes in this field
     *
     * @param filteredPolicyHolderList
     */
    public void filteringPolicyHolderTable(FilteredList<PolicyHolder> filteredPolicyHolderList) {
        policyHolderSearchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredPolicyHolderList.setPredicate(policyHolder -> {
                if (newValue.isEmpty() || newValue == null || newValue.isBlank()) {
                    return true;
                }
                String searchValue = newValue.toLowerCase();
                if (policyHolder.getId().toLowerCase().contains(searchValue)) {
                    return true;
                } else if (policyHolder.getFullName().toLowerCase().contains(searchValue)) {
                    return true;
                } else if (policyHolder.getEmail().toLowerCase().contains(searchValue)) {
                    return true;
                } else if (policyHolder.getAddress().toLowerCase().contains(searchValue)) {
                    return true;
                } else if (policyHolder.getPhoneNumber().toLowerCase().contains(searchValue)) {
                    return true;
                } else if (policyHolder.getPolicyOwnerId().toLowerCase().contains(searchValue)) {
                    return true;
                } else if (policyHolder.getPolicyOwner().getFullName().toLowerCase().contains(searchValue)) {
                    return true;
                } else {
                    return false;
                }
            });
        });
    }

    /**
     * Mapping the columns of the policy holder tables with Policy Holder entity. Fill up the policy holder tables with data from the database
     *
     * @param entityManager
     * @param user
     * @param policyHolders
     */
    public void fillingPolicyHolderTable(EntityManager entityManager, User user, List<PolicyHolder> policyHolders) {
        ListIterator<PolicyHolder> policyHolderListIterator = policyHolders.listIterator();
        //Add policy holders to the observable list
        while (policyHolderListIterator.hasNext()) {
            PolicyHolder policyHolder = policyHolderListIterator.next();
            //setter
            policyHolder.setTotalSuccessfulClaimAmount(ClaimRead.getTotalSuccessfulClaimAmountMadeByABeneficiary((Beneficiaries) policyHolder));
            Button buttonUpdateInfo = new Button("Update Info");
            Button buttonAddDependant = new Button("Add Dependant");
            Button buttonRemove = new Button("Remove");
            Button buttonAddClaim = new Button("Add Claim");
            buttonList.add(buttonAddDependant);
            buttonList.add(buttonRemove);
            buttonList.add(buttonAddClaim);
            buttonList.add(buttonUpdateInfo);

            //Only a system admin and a policy owner has access to the update info and remove and add dependant buttons
            if (user instanceof SystemAdmin || user instanceof Customer) {
                //The Update Info Button will create a CreationPage Controller for the policy holder in update mode by passing in the policy holder object
                //It will then open the Policy Holder Creation Page
                buttonUpdateInfo.setOnAction(event -> {
                    CreationAndUpdatePageControllerPolicyHolder creationPageControllerPolicyHolder = new CreationAndUpdatePageControllerPolicyHolder(entityManager, user, policyHolder);
                    StageBuilder.showStage((Stage) buttonUpdateInfo.getScene().getWindow(), creationPageControllerPolicyHolder, "PolicyHolderCreationAndUpdatePage.fxml", "Policy Holder Update");
                });
                //The addDependant button will create a Dependant CreationPage Controller in creation mode by passing the policy holder object
                //It will then open the Dependant Creation Form
                buttonAddDependant.setOnAction(event -> {
                    CreationAndUpdatePageControllerDependant creationPageControllerDependant = new CreationAndUpdatePageControllerDependant(entityManager, user, policyHolder);
                    StageBuilder.showStage((Stage) buttonAddDependant.getScene().getWindow(), creationPageControllerDependant, "DependantCreationAndUpdatePage.fxml", "Dependant Creation");
                });
                //The remove button will remove its policy holder from the database
                buttonRemove.setOnAction(event -> {
                    CustomerCreateRemove.removePolicyHolder(entityManager, policyHolder);
                });
                policyHolder.setRemoveButton(buttonRemove);
                policyHolder.setAddDependantButton(buttonAddDependant);
                policyHolder.setUpdateInfoButton(buttonUpdateInfo);

                //Only a policy owner has access to the add claim button
                if (user instanceof PolicyOwner) {
                    //Create a ClaimCreationPage controller in creation mode by passing the policy holder object to the constructor
                    //Open a new scene in the existing stage by calling the showStage static method from the Repeated Code Class
                    buttonAddClaim.setOnAction(event -> {
                        CreationAndUpdatePageControllerClaim creationPageControllerClaim = new CreationAndUpdatePageControllerClaim(entityManager, user, policyHolder);
                        StageBuilder.showStage((Stage) buttonAddClaim.getScene().getWindow(), creationPageControllerClaim, "ClaimCreationAndUpdatePage.fxml", "Claim Creation");
                    });
                    policyHolder.setAddClaimButton(buttonAddClaim);
                }
            }

            policyHoldersObservableList.add(policyHolder);
        }


        policyHolderId.setCellValueFactory(new PropertyValueFactory<PolicyHolder, String>("id"));
        policyHolderFullName.setCellValueFactory(new PropertyValueFactory<PolicyHolder, String>("fullName"));
        policyHolderAddress.setCellValueFactory(new PropertyValueFactory<PolicyHolder, String>("address"));
        policyHolderPhoneNumber.setCellValueFactory(new PropertyValueFactory<PolicyHolder, String>("phoneNumber"));
        policyHolderEmail.setCellValueFactory(new PropertyValueFactory<PolicyHolder, String>("email"));
        policyOwnerHolderTable.setCellValueFactory(new PropertyValueFactory<PolicyHolder, String>("policyOwnerId"));
        cardNumberHolderTable.setCellValueFactory(new PropertyValueFactory<PolicyHolder, String>("cardNumber"));
        policyHolderPassword.setCellValueFactory(new PropertyValueFactory<PolicyHolder, String>("password"));
        if (user instanceof SystemAdmin) {
            totalSuccessfulClaimAmountPolicyHolderColumn.setCellValueFactory(new PropertyValueFactory<PolicyHolder, Integer>("totalSuccessfulClaimAmount"));
        }

        if (user instanceof SystemAdmin || user instanceof Customer) {
            policyHolderUpdateInfoButton.setCellValueFactory(new PropertyValueFactory<PolicyHolder, Button>("updateInfoButton"));
            policyHolderAddDependantButton.setCellValueFactory(new PropertyValueFactory<PolicyHolder, Button>("addDependantButton"));
            policyHolderRemoveButton.setCellValueFactory(new PropertyValueFactory<PolicyHolder, Button>("removeButton"));
            if (user instanceof Customer) {
                policyHolderAddClaimButton.setCellValueFactory(new PropertyValueFactory<PolicyHolder, Button>("addClaimButton"));
            }
        }
        FilteredList<PolicyHolder> filteredPolicyHolderList = new FilteredList<>(policyHoldersObservableList, b -> true);
        filteringPolicyHolderTable(filteredPolicyHolderList);
        policyHolderTable.setItems(filteredPolicyHolderList);
    }


    public TableView<PolicyHolder> getPolicyHolderTable() {
        return policyHolderTable;
    }

    public void setPolicyHolderTable(TableView<PolicyHolder> policyHolderTable) {
        this.policyHolderTable = policyHolderTable;
    }

    public TableColumn<PolicyHolder, String> getPolicyHolderId() {
        return policyHolderId;
    }

    public void setPolicyHolderId(TableColumn<PolicyHolder, String> policyHolderId) {
        this.policyHolderId = policyHolderId;
    }

    public TableColumn<PolicyHolder, String> getPolicyHolderFullName() {
        return policyHolderFullName;
    }

    public void setPolicyHolderFullName(TableColumn<PolicyHolder, String> policyHolderFullName) {
        this.policyHolderFullName = policyHolderFullName;
    }

    public TableColumn<PolicyHolder, String> getPolicyHolderAddress() {
        return policyHolderAddress;
    }

    public void setPolicyHolderAddress(TableColumn<PolicyHolder, String> policyHolderAddress) {
        this.policyHolderAddress = policyHolderAddress;
    }

    public TableColumn<PolicyHolder, String> getPolicyHolderPhoneNumber() {
        return policyHolderPhoneNumber;
    }

    public void setPolicyHolderPhoneNumber(TableColumn<PolicyHolder, String> policyHolderPhoneNumber) {
        this.policyHolderPhoneNumber = policyHolderPhoneNumber;
    }

    public TableColumn<PolicyHolder, String> getPolicyHolderEmail() {
        return policyHolderEmail;
    }

    public void setPolicyHolderEmail(TableColumn<PolicyHolder, String> policyHolderEmail) {
        this.policyHolderEmail = policyHolderEmail;
    }

    public TableColumn<PolicyHolder, String> getPolicyHolderPassword() {
        return policyHolderPassword;
    }

    public void setPolicyHolderPassword(TableColumn<PolicyHolder, String> policyHolderPassword) {
        this.policyHolderPassword = policyHolderPassword;
    }

    public TableColumn<PolicyHolder, String> getPolicyOwnerHolderTable() {
        return policyOwnerHolderTable;
    }

    public void setPolicyOwnerHolderTable(TableColumn<PolicyHolder, String> policyOwnerHolderTable) {
        this.policyOwnerHolderTable = policyOwnerHolderTable;
    }

    public TableColumn<PolicyHolder, String> getCardNumberHolderTable() {
        return cardNumberHolderTable;
    }

    public void setCardNumberHolderTable(TableColumn<PolicyHolder, String> cardNumberHolderTable) {
        this.cardNumberHolderTable = cardNumberHolderTable;
    }

    public TableColumn<PolicyHolder, Button> getPolicyHolderUpdateInfoButton() {
        return policyHolderUpdateInfoButton;
    }

    public void setPolicyHolderUpdateInfoButton(TableColumn<PolicyHolder, Button> policyHolderUpdateInfoButton) {
        this.policyHolderUpdateInfoButton = policyHolderUpdateInfoButton;
    }

    public TableColumn<PolicyHolder, Button> getPolicyHolderAddDependantButton() {
        return policyHolderAddDependantButton;
    }

    public void setPolicyHolderAddDependantButton(TableColumn<PolicyHolder, Button> policyHolderAddDependantButton) {
        this.policyHolderAddDependantButton = policyHolderAddDependantButton;
    }

    public TableColumn<PolicyHolder, Button> getPolicyHolderAddClaimButton() {
        return policyHolderAddClaimButton;
    }

    public void setPolicyHolderAddClaimButton(TableColumn<PolicyHolder, Button> policyHolderAddClaimButton) {
        this.policyHolderAddClaimButton = policyHolderAddClaimButton;
    }

    public TableColumn<PolicyHolder, Button> getPolicyHolderRemoveButton() {
        return policyHolderRemoveButton;
    }

    public void setPolicyHolderRemoveButton(TableColumn<PolicyHolder, Button> policyHolderRemoveButton) {
        this.policyHolderRemoveButton = policyHolderRemoveButton;
    }

    public TextField getPolicyHolderSearchField() {
        return policyHolderSearchField;
    }

    public void setPolicyHolderSearchField(TextField policyHolderSearchField) {
        this.policyHolderSearchField = policyHolderSearchField;
    }

}

//Inner Class For Thread

