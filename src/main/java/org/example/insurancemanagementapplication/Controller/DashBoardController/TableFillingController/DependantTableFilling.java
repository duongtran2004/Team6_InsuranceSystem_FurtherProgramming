package org.example.insurancemanagementapplication.Controller.DashBoardController.TableFillingController;

import Entity.Customer;
import Entity.Dependant;
import Entity.SystemAdmin;
import Entity.User;
import jakarta.persistence.EntityManager;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.example.insurancemanagementapplication.Controller.CreationPageController.CreationPageController_Claim;
import org.example.insurancemanagementapplication.Controller.CreationPageController.CreationPageController_Dependant;
import org.example.insurancemanagementapplication.Interfaces.CustomerCreateRemove;
import org.example.insurancemanagementapplication.Utility.RepeatedCode;

import java.util.List;
import java.util.ListIterator;

/**
 * @author Luong Thanh Trung
 * @version ${}
 * @created 01/05/2024 15:33
 * @project InsuranceManagementTeamProject
 */
public class DependantTableFilling extends DashBoardController {
    @FXML
    protected TableView<Dependant> dependantTable;
    @FXML
    protected TableColumn<Dependant, String> dependantId;
    @FXML
    protected TableColumn<Dependant, String> dependantFullName;
    @FXML
    protected TableColumn<Dependant, String> dependantAddress;
    @FXML
    protected TableColumn<Dependant, String> dependantPhoneNumber;
    @FXML
    protected TableColumn<Dependant, String> dependantEmail;
    @FXML
    protected TableColumn<Dependant, String> dependantPassword;
    @FXML
    protected TableColumn<Dependant, String> policyOwnerDependantTable;
    @FXML
    protected TableColumn<Dependant, String> cardNumberDependantTable;
    @FXML
    protected TableColumn<Dependant, Button> dependantUpdateInfoButton;
    @FXML
    protected TableColumn<Dependant, Button> dependantAddClaimButton;
    @FXML
    protected TableColumn<Dependant, Button> dependantRemoveButton;
    @FXML
    protected TableColumn<Dependant, String> policyHolderDependantTable;
    @FXML
    protected TextField dependantSearchField;

    public DependantTableFilling(EntityManager entityManager, User user) {
        super(entityManager, user);
    }

    public void filteringDependantTable(FilteredList<Dependant> filteredDependantList){
        dependantSearchField.textProperty().addListener((observable, oldValue, newValue)->{

            filteredDependantList.setPredicate(dependant -> {
                if (newValue.isEmpty() || newValue == null || newValue.isBlank()){
                    return true;
                }
                String searchValue = newValue.toLowerCase();
                if (dependant.getId().toLowerCase().contains(searchValue)){
                    return true;
                }
                else if (dependant.getFullName().toLowerCase().contains(searchValue)){
                    return true;
                }
                else if (dependant.getAddress().toLowerCase().contains(searchValue)){
                    return true;
                }
                else if (dependant.getEmail().toLowerCase().contains(searchValue)){
                    return true;
                }
                else if (dependant.getPhoneNumber().toLowerCase().contains(searchValue)){
                    return true;
                }
                else if (dependant.getPolicyOwnerId().toLowerCase().contains(searchValue)){
                    return true;
                }
                else if(dependant.getPolicyOwner().getFullName().toLowerCase().contains(searchValue)) {
                    return true;
                }
                else if (dependant.getPolicyHolderId().toLowerCase().contains(searchValue)){
                    return true;
                }
                else if(dependant.getPolicyHolder().getFullName().toLowerCase().contains(searchValue)) {
                    return true;
                }
                else {
                    return false;
                }
            });
        });
    }
    public void fillingDependantTable(EntityManager entityManager, User user, List<Dependant> dependants, ObservableList<Dependant> dependantObservableList){
        ListIterator<Dependant> dependantListIterator = dependants.listIterator();
        while (dependantListIterator.hasNext()){
            Dependant dependant = dependantListIterator.next();
            Button buttonUpdateInfo = new Button();
            Button buttonAddClaim = new Button();
            Button buttonRemove = new Button();
            if (user instanceof SystemAdmin || user instanceof Customer){
                buttonUpdateInfo.setText("Update Info");
                buttonUpdateInfo.setOnAction(event -> {
                    CreationPageController_Dependant creationPageControllerDependant = new CreationPageController_Dependant(entityManager, user, dependant);
                    RepeatedCode.showStage((Stage) buttonUpdateInfo.getScene().getWindow(), creationPageControllerDependant, "DependantCreationPage.fxml", "Dependant Update");

                });

                buttonRemove.setText("Remove");
                dependant.setRemoveButton(buttonRemove);
                buttonRemove.setOnAction(event -> {
                    CustomerCreateRemove.removeDependant(entityManager, dependant );
                });

                buttonUpdateInfo.setUserData(dependant);
                dependant.setUpdateInfoButton(buttonUpdateInfo);


                if (!(user instanceof SystemAdmin)){
                    buttonAddClaim.setText("Add Claim");
                    buttonAddClaim.setOnAction(event -> {
                        CreationPageController_Claim creationPageControllerClaim = new CreationPageController_Claim(entityManager, user, dependant);
                        RepeatedCode.showStage((Stage) buttonUpdateInfo.getScene().getWindow(), creationPageControllerClaim, "ClaimCreationPage.fxml", "Claim Creation");
                    });
                    dependant.setAddClaimButton(buttonAddClaim);
                }

                dependantObservableList.add(dependant);

            }


        }
        FilteredList<Dependant> filteredDependantList = new FilteredList<>(dependantObservableList, b -> true);
        filteringDependantTable(filteredDependantList);

        dependantId.setCellValueFactory(new PropertyValueFactory<Dependant, String>("id"));
        dependantFullName.setCellValueFactory(new PropertyValueFactory<Dependant, String>("fullName"));
        dependantAddress.setCellValueFactory(new PropertyValueFactory<Dependant, String>("address"));
        dependantEmail.setCellValueFactory(new PropertyValueFactory<Dependant, String>("email"));
        dependantPassword.setCellValueFactory(new PropertyValueFactory<Dependant, String>("password"));
        policyOwnerDependantTable.setCellValueFactory(new PropertyValueFactory<Dependant, String>("policyOwnerId"));
        cardNumberDependantTable.setCellValueFactory(new PropertyValueFactory<Dependant, String>("cardNumber"));
        policyHolderDependantTable.setCellValueFactory(new PropertyValueFactory<Dependant, String>("policyHolderId"));
        if (user instanceof SystemAdmin || user instanceof Customer){
            dependantUpdateInfoButton.setCellValueFactory(new PropertyValueFactory<Dependant, Button>("updateInfoButton"));
            dependantRemoveButton.setCellValueFactory(new PropertyValueFactory<Dependant, Button>("removeButton"));
            if (!(user instanceof SystemAdmin)){
                dependantAddClaimButton.setCellValueFactory(new PropertyValueFactory<Dependant, Button>("addClaim"));
            }
        }

        dependantTable.getItems().setAll(dependantObservableList);
    }













    public TableView<Dependant> getDependantTable() {
        return dependantTable;
    }

    public void setDependantTable(TableView<Dependant> dependantTable) {
        this.dependantTable = dependantTable;
    }

    public TableColumn<Dependant, String> getDependantId() {
        return dependantId;
    }

    public void setDependantId(TableColumn<Dependant, String> dependantId) {
        this.dependantId = dependantId;
    }

    public TableColumn<Dependant, String> getDependantFullName() {
        return dependantFullName;
    }

    public void setDependantFullName(TableColumn<Dependant, String> dependantFullName) {
        this.dependantFullName = dependantFullName;
    }

    public TableColumn<Dependant, String> getDependantAddress() {
        return dependantAddress;
    }

    public void setDependantAddress(TableColumn<Dependant, String> dependantAddress) {
        this.dependantAddress = dependantAddress;
    }

    public TableColumn<Dependant, String> getDependantPhoneNumber() {
        return dependantPhoneNumber;
    }

    public void setDependantPhoneNumber(TableColumn<Dependant, String> dependantPhoneNumber) {
        this.dependantPhoneNumber = dependantPhoneNumber;
    }

    public TableColumn<Dependant, String> getDependantEmail() {
        return dependantEmail;
    }

    public void setDependantEmail(TableColumn<Dependant, String> dependantEmail) {
        this.dependantEmail = dependantEmail;
    }

    public TableColumn<Dependant, String> getDependantPassword() {
        return dependantPassword;
    }

    public void setDependantPassword(TableColumn<Dependant, String> dependantPassword) {
        this.dependantPassword = dependantPassword;
    }

    public TableColumn<Dependant, String> getPolicyOwnerDependantTable() {
        return policyOwnerDependantTable;
    }

    public void setPolicyOwnerDependantTable(TableColumn<Dependant, String> policyOwnerDependantTable) {
        this.policyOwnerDependantTable = policyOwnerDependantTable;
    }

    public TableColumn<Dependant, String> getCardNumberDependantTable() {
        return cardNumberDependantTable;
    }

    public void setCardNumberDependantTable(TableColumn<Dependant, String> cardNumberDependantTable) {
        this.cardNumberDependantTable = cardNumberDependantTable;
    }

    public TableColumn<Dependant, Button> getDependantUpdateInfoButton() {
        return dependantUpdateInfoButton;
    }

    public void setDependantUpdateInfoButton(TableColumn<Dependant, Button> dependantUpdateInfoButton) {
        this.dependantUpdateInfoButton = dependantUpdateInfoButton;
    }

    public TableColumn<Dependant, Button> getDependantAddClaimButton() {
        return dependantAddClaimButton;
    }

    public void setDependantAddClaimButton(TableColumn<Dependant, Button> dependantAddClaimButton) {
        this.dependantAddClaimButton = dependantAddClaimButton;
    }

    public TableColumn<Dependant, Button> getDependantRemoveButton() {
        return dependantRemoveButton;
    }

    public void setDependantRemoveButton(TableColumn<Dependant, Button> dependantRemoveButton) {
        this.dependantRemoveButton = dependantRemoveButton;
    }

    public TableColumn<Dependant, String> getPolicyHolderDependantTable() {
        return policyHolderDependantTable;
    }

    public void setPolicyHolderDependantTable(TableColumn<Dependant, String> policyHolderDependantTable) {
        this.policyHolderDependantTable = policyHolderDependantTable;
    }

    public TextField getDependantSearchField() {
        return dependantSearchField;
    }

    public void setDependantSearchField(TextField dependantSearchField) {
        this.dependantSearchField = dependantSearchField;
    }
}
