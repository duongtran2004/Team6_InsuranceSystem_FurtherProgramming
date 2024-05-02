package org.example.insurancemanagementapplication.Controller.DashBoardController.TableFillingController;

import Entity.Customer;
import Entity.PolicyHolder;
import Entity.SystemAdmin;
import Entity.User;
import jakarta.persistence.EntityManager;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.example.insurancemanagementapplication.Controller.CreationPageController.CreationPageController_Dependant;
import org.example.insurancemanagementapplication.Controller.CreationPageController.CreationPageController_PolicyHolder;
import org.example.insurancemanagementapplication.Interfaces.CustomerCreateRemove;
import org.example.insurancemanagementapplication.MainEntryPoint;

import java.io.IOException;
import java.util.List;
import java.util.ListIterator;

/**
 * @author Luong Thanh Trung
 * @version ${}
 * @created 01/05/2024 15:56
 * @project InsuranceManagementTeamProject
 */
public class PolicyHolderTableFilling extends InsuranceCardFillingTable{
    @FXML
    private TableView<PolicyHolder> policyHolderTable;
    @FXML
    private TableColumn<PolicyHolder, String> policyHolderId;
    @FXML
    private TableColumn<PolicyHolder, String> policyHolderFullName;
    @FXML
    private TableColumn<PolicyHolder, String> policyHolderAddress;
    @FXML
    private TableColumn<PolicyHolder, String> policyHolderPhoneNumber;
    @FXML
    private TableColumn<PolicyHolder, String> policyHolderEmail;
    @FXML
    private TableColumn<PolicyHolder, String> policyHolderPassword;
    @FXML
    private TableColumn<PolicyHolder, String> policyOwnerHolderTable;
    @FXML
    private TableColumn<PolicyHolder, String> cardNumberHolderTable;
    @FXML
    private TableColumn<PolicyHolder, Button> policyHolderUpdateInfoButton;
    @FXML
    private TableColumn<PolicyHolder, Button> policyHolderAddDependantButton;
    @FXML
    private TableColumn<PolicyHolder, Button> policyHolderAddClaimButton;
    @FXML
    private TableColumn<PolicyHolder, Button> policyHolderRemoveButton;
    @FXML
    private TextField policyHolderSearchField;

    public void filteringPolicyHolderTable(FilteredList<PolicyHolder> filteredPolicyHolderList){
        policyHolderSearchField.textProperty().addListener((observable, oldValue, newValue)->{
            filteredPolicyHolderList.setPredicate(policyHolder -> {
                if (newValue.isEmpty() || newValue == null || newValue.isBlank()){
                    return true;
                }
                String searchValue = newValue.toLowerCase();
                if (policyHolder.getId().equals(searchValue)){
                    return true;
                }
                else if (policyHolder.getFullName().equals(searchValue)){
                    return true;
                }
                else if (policyHolder.getEmail().equals(searchValue)){
                    return true;
                }
                else if (policyHolder.getAddress().equals(searchValue)){
                    return true;
                }
                else if (policyHolder.getPhoneNumber().equals(searchValue)){
                    return true;
                }
                else if (policyHolder.getPolicyOwnerId().equals(searchValue)){
                    return true;
                }
                else if( policyHolder.getPolicyOwner().getFullName().equals(searchValue)) {
                    return true;
                }

                else {
                    return false;
                }
            });
        });
    }

    public void fillingPolicyHolderTable(EntityManager entityManager, User user, List<PolicyHolder> policyHolders, ObservableList<PolicyHolder> policyHoldersObservableList){
        ListIterator<PolicyHolder> policyHolderListIterator = policyHolders.listIterator();
        while (policyHolderListIterator.hasNext()){
            PolicyHolder policyHolder = policyHolderListIterator.next();
            Button buttonUpdateInfo = new Button("Update Info");
            Button buttonAddDependant = new Button("Add Dependant");
            Button buttonRemove = new Button("Remove");
            Button buttonAddClaim = new Button("Add Claim");

            if (user instanceof SystemAdmin || user instanceof Customer){
                buttonUpdateInfo.setOnAction(event -> {
                    CreationPageController_PolicyHolder creationPageControllerPolicyHolder = new CreationPageController_PolicyHolder(entityManager, user, policyHolder);
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(MainEntryPoint.class.getResource("PolicyHolderCreationPage.fxml"));
                    fxmlLoader.setController(creationPageControllerPolicyHolder);
                    try {
                        Scene scene = new Scene(fxmlLoader.load());
                        Stage stage = (Stage) buttonUpdateInfo.getScene().getWindow();
                        stage.setScene(scene);
                        stage.show();

                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
                buttonAddDependant.setOnAction(event -> {
                    CreationPageController_Dependant creationPageControllerDependant = new CreationPageController_Dependant(entityManager, user, policyHolder);
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(MainEntryPoint.class.getResource("DependantCreationPage.fxml"));
                    fxmlLoader.setController(creationPageControllerDependant);
                    try {
                        Scene scene = new Scene(fxmlLoader.load());
                        Stage stage = (Stage) buttonAddDependant.getScene().getWindow();
                        stage.setScene(scene);
                        stage.show();

                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                });
                buttonRemove.setOnAction(event -> {
                    CustomerCreateRemove.removePolicyHolder(entityManager, policyHolder );
                });
                policyHolder.setRemoveButton(buttonRemove);
                policyHolder.setAddDependantButton(buttonAddDependant);
                policyHolder.setUpdateInfoButton(buttonUpdateInfo);

                if (user instanceof Customer){
                    buttonAddClaim.setOnAction(event -> {
                        policyHolder.setAddClaimButton(buttonAddClaim);
                    });
                }
            }

            policyHoldersObservableList.add(policyHolder);
        }

        FilteredList<PolicyHolder> filteredPolicyHolderList = new FilteredList<>(policyHoldersObservableList, b -> true);
        filteringPolicyHolderTable(filteredPolicyHolderList);

        policyHolderId.setCellValueFactory(new PropertyValueFactory<PolicyHolder, String>("id"));
        policyHolderFullName.setCellValueFactory(new PropertyValueFactory<PolicyHolder, String>("fullName"));
        policyHolderAddress.setCellValueFactory(new PropertyValueFactory<PolicyHolder, String>("address"));
        policyHolderEmail.setCellValueFactory(new PropertyValueFactory<PolicyHolder, String>("email"));
        policyOwnerHolderTable.setCellValueFactory(new PropertyValueFactory<PolicyHolder, String>("policyOwnerId"));
        cardNumberHolderTable.setCellValueFactory(new PropertyValueFactory<PolicyHolder, String>("cardNumber"));
        policyHolderPassword.setCellValueFactory(new PropertyValueFactory<PolicyHolder, String>("password"));
        if (user instanceof SystemAdmin || user instanceof Customer){
            policyHolderUpdateInfoButton.setCellValueFactory(new PropertyValueFactory<PolicyHolder, Button>("updateInfoButton"));
            policyHolderAddDependantButton.setCellValueFactory(new PropertyValueFactory<PolicyHolder, Button>("addDependantButton"));
            policyHolderRemoveButton.setCellValueFactory(new PropertyValueFactory<PolicyHolder, Button>("removeButton"));
            if (user instanceof Customer){
                policyHolderAddClaimButton.setCellValueFactory(new PropertyValueFactory<PolicyHolder, Button>("addClaimButton"));
            }
        }

        policyHolderTable.getItems().setAll(filteredPolicyHolderList);
    }

    public PolicyHolderTableFilling(){

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
