package org.example.insurancemanagementapplication.Controller.DashBoardController.TableFillingController;

import Entity.PolicyOwner;
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
import org.example.insurancemanagementapplication.Controller.CreationPageController.CreationPageController_PolicyHolder;
import org.example.insurancemanagementapplication.Controller.CreationPageController.CreationPageController_PolicyOwner;
import org.example.insurancemanagementapplication.Interfaces.CustomerCreateRemove;
import org.example.insurancemanagementapplication.MainEntryPoint;

import java.io.IOException;
import java.util.List;
import java.util.ListIterator;

/**
 * @author Luong Thanh Trung
 * @version ${}
 * @created 01/05/2024 16:11
 * @project InsuranceManagementTeamProject
 */
public class PolicyOwnerTableFilling extends PolicyHolderTableFilling {
    @FXML
    private TableView<PolicyOwner> policyOwnerTable;
    @FXML
    private TableColumn<PolicyOwner, String> policyOwnerId;
    @FXML
    private TableColumn<PolicyOwner, String> policyOwnerFullName;
    @FXML
    private TableColumn<PolicyOwner, String> policyOwnerAddress;
    @FXML
    private TableColumn<PolicyOwner, String> policyOwnerPhoneNumber;
    @FXML
    private TableColumn<PolicyOwner, String> policyOwnerEmail;
    @FXML
    private TableColumn<PolicyOwner, String> policyOwnerPassword;
    @FXML
    private TableColumn<PolicyOwner, Button> policyOwnerUpdateInfoButton;
    @FXML
    private TableColumn<PolicyOwner, Button> policyOwnerAddPolicyButton;
    @FXML
    private TableColumn<PolicyOwner, Button> policyOwnerRemoveButton;
    @FXML
    private TextField policyOwnerSearchField;

    public void filteringPolicyOwnerTable(FilteredList<PolicyOwner> filteredPolicyOwnerList){
        policyOwnerSearchField.textProperty().addListener((observable, oldValue, newValue)->{
            filteredPolicyOwnerList.setPredicate(policyOwner -> {
                if (newValue.isEmpty() || newValue == null || newValue.isBlank()){
                    return true;
                }
                String searchValue = newValue.toLowerCase();
                if (policyOwner.getId().equals(searchValue)){
                    return true;
                }
                else if (policyOwner.getFullName().equals(searchValue)){
                    return true;
                }
                else if (policyOwner.getEmail().equals(searchValue)){
                    return true;
                }
                else if (policyOwner.getAddress().equals(searchValue)){
                    return true;
                }
                else if (policyOwner.getPhoneNumber().equals(searchValue)){
                    return true;
                }

                else {
                    return false;
                }
            });
        });
    }

    public void fillingPolicyOwnerTable(EntityManager entityManager, User user, List<PolicyOwner> policyOwners, ObservableList<PolicyOwner> policyOwnersObservableList){
        ListIterator<PolicyOwner> policyOwnerListIterator = policyOwners.listIterator();
        while (policyOwnerListIterator.hasNext()){
            PolicyOwner policyOwner = policyOwnerListIterator.next();
            Button buttonUpdateInfo = new Button("Update Info");
            Button buttonAddPolicy = new Button("Add Policy");
            Button buttonRemove = new Button("Remove");

            if (user instanceof SystemAdmin){
                buttonUpdateInfo.setOnAction(event -> {
                    CreationPageController_PolicyOwner creationPageControllerPolicyOwner = new CreationPageController_PolicyOwner(entityManager, user, policyOwner);
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(MainEntryPoint.class.getResource("PolicyOwnerCreationPage.fxml"));
                    fxmlLoader.setController(creationPageControllerPolicyOwner);
                    try {
                        Scene scene = new Scene(fxmlLoader.load());
                        Stage stage = (Stage) buttonUpdateInfo.getScene().getWindow();
                        stage.setScene(scene);
                        stage.show();

                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });

                policyOwner.setUpdateInfoButton(buttonUpdateInfo);

                buttonAddPolicy.setOnAction(event -> {
                    CreationPageController_PolicyHolder creationPageControllerPolicyHolder = new CreationPageController_PolicyHolder(entityManager, user, policyOwner);
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(MainEntryPoint.class.getResource("PolicyHolderCreationPage.fxml"));
                    fxmlLoader.setController(creationPageControllerPolicyHolder);
                    try {
                        Scene scene = new Scene(fxmlLoader.load());
                        Stage stage = (Stage) buttonAddPolicy.getScene().getWindow();
                        stage.setScene(scene);
                        stage.show();

                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });

                policyOwner.setRemoveButton(buttonRemove);
                buttonRemove.setOnAction(event -> {
                    CustomerCreateRemove.removePolicyOwner(entityManager, policyOwner );
                });
                policyOwner.setAddPolicyButton(buttonAddPolicy);
            }

            policyOwnersObservableList.add(policyOwner);
        }

        FilteredList<PolicyOwner> filteredPolicyOwnerList = new FilteredList<>(policyOwnersObservableList, b -> true);
        filteringPolicyOwnerTable(filteredPolicyOwnerList);

        policyOwnerId.setCellValueFactory(new PropertyValueFactory<PolicyOwner, String>("id"));
        policyOwnerFullName.setCellValueFactory(new PropertyValueFactory<PolicyOwner, String>("fullName"));
        policyOwnerAddress.setCellValueFactory(new PropertyValueFactory<PolicyOwner, String>("address"));
        policyOwnerEmail.setCellValueFactory(new PropertyValueFactory<PolicyOwner, String>("email"));
        policyOwnerPassword.setCellValueFactory(new PropertyValueFactory<PolicyOwner, String>("password"));
        if (user instanceof SystemAdmin){
            policyOwnerUpdateInfoButton.setCellValueFactory(new PropertyValueFactory<PolicyOwner, Button>("updateInfoButton"));
            policyOwnerAddPolicyButton.setCellValueFactory(new PropertyValueFactory<PolicyOwner, Button>("addPolicyButton"));
            policyOwnerRemoveButton.setCellValueFactory(new PropertyValueFactory<PolicyOwner, Button>("removeButton"));
        }

        policyOwnerTable.getItems().setAll(filteredPolicyOwnerList);

    }

    public PolicyOwnerTableFilling(){

    }

    public TableView<PolicyOwner> getPolicyOwnerTable() {
        return policyOwnerTable;
    }

    public void setPolicyOwnerTable(TableView<PolicyOwner> policyOwnerTable) {
        this.policyOwnerTable = policyOwnerTable;
    }

    public TableColumn<PolicyOwner, String> getPolicyOwnerId() {
        return policyOwnerId;
    }

    public void setPolicyOwnerId(TableColumn<PolicyOwner, String> policyOwnerId) {
        this.policyOwnerId = policyOwnerId;
    }

    public TableColumn<PolicyOwner, String> getPolicyOwnerFullName() {
        return policyOwnerFullName;
    }

    public void setPolicyOwnerFullName(TableColumn<PolicyOwner, String> policyOwnerFullName) {
        this.policyOwnerFullName = policyOwnerFullName;
    }

    public TableColumn<PolicyOwner, String> getPolicyOwnerAddress() {
        return policyOwnerAddress;
    }

    public void setPolicyOwnerAddress(TableColumn<PolicyOwner, String> policyOwnerAddress) {
        this.policyOwnerAddress = policyOwnerAddress;
    }

    public TableColumn<PolicyOwner, String> getPolicyOwnerPhoneNumber() {
        return policyOwnerPhoneNumber;
    }

    public void setPolicyOwnerPhoneNumber(TableColumn<PolicyOwner, String> policyOwnerPhoneNumber) {
        this.policyOwnerPhoneNumber = policyOwnerPhoneNumber;
    }

    public TableColumn<PolicyOwner, String> getPolicyOwnerEmail() {
        return policyOwnerEmail;
    }

    public void setPolicyOwnerEmail(TableColumn<PolicyOwner, String> policyOwnerEmail) {
        this.policyOwnerEmail = policyOwnerEmail;
    }

    public TableColumn<PolicyOwner, String> getPolicyOwnerPassword() {
        return policyOwnerPassword;
    }

    public void setPolicyOwnerPassword(TableColumn<PolicyOwner, String> policyOwnerPassword) {
        this.policyOwnerPassword = policyOwnerPassword;
    }

    public TableColumn<PolicyOwner, Button> getPolicyOwnerUpdateInfoButton() {
        return policyOwnerUpdateInfoButton;
    }

    public void setPolicyOwnerUpdateInfoButton(TableColumn<PolicyOwner, Button> policyOwnerUpdateInfoButton) {
        this.policyOwnerUpdateInfoButton = policyOwnerUpdateInfoButton;
    }

    public TableColumn<PolicyOwner, Button> getPolicyOwnerAddPolicyButton() {
        return policyOwnerAddPolicyButton;
    }

    public void setPolicyOwnerAddPolicyButton(TableColumn<PolicyOwner, Button> policyOwnerAddPolicyButton) {
        this.policyOwnerAddPolicyButton = policyOwnerAddPolicyButton;
    }

    public TableColumn<PolicyOwner, Button> getPolicyOwnerRemoveButton() {
        return policyOwnerRemoveButton;
    }

    public void setPolicyOwnerRemoveButton(TableColumn<PolicyOwner, Button> policyOwnerRemoveButton) {
        this.policyOwnerRemoveButton = policyOwnerRemoveButton;
    }

    public TextField getPolicyOwnerSearchField() {
        return policyOwnerSearchField;
    }

    public void setPolicyOwnerSearchField(TextField policyOwnerSearchField) {
        this.policyOwnerSearchField = policyOwnerSearchField;
    }
}
