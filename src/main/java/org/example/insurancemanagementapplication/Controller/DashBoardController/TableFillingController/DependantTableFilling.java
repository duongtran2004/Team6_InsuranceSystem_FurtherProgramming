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
import org.example.insurancemanagementapplication.Controller.CreationPageController.CreationPageController_Claim;
import org.example.insurancemanagementapplication.Controller.CreationPageController.CreationPageController_Dependant;
import org.example.insurancemanagementapplication.Interfaces.CustomerCreateRemove;
import org.example.insurancemanagementapplication.Utility.StageBuilder;

import java.util.List;
import java.util.ListIterator;

/**
 * @author Luong Thanh Trung
 * @version ${}
 * @created 01/05/2024 15:33
 * @project InsuranceManagementTeamProject
 */

/**
 * This class extends the ClaimTableFilling class. As such, it has access to all instance fields and methods of that class
 * This class contains methods to fill up Dependant table. It is extended by all DashBoard Controller classes except for the Dependant
 * DashBoard Controller, which does not have access to this table
 */
public class DependantTableFilling extends ClaimTableFilling {
    //TODO Create a thread that runs in a selected interval that get all Dependants from the database  and check if new entries exist. If they do, append the new entries to the Observable List

    //this List is to get notice of changes in dependents Collection
    private ObservableList<Dependant> dependantsObservableList = FXCollections.observableArrayList();

    //import necessary FXML object
    //tables and its column
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
    //controller like button and search field
    @FXML
    protected TableColumn<Dependant, Button> dependantUpdateInfoButton;
    @FXML
    protected TableColumn<Dependant, Button> dependantAddClaimButton;
    @FXML
    protected TableColumn<Dependant, Button> dependantRemoveButton;
    //child table inside the dashboard
    @FXML
    protected TableColumn<Dependant, String> policyHolderDependantTable;
    @FXML
    protected TextField dependantSearchField;

    public DependantTableFilling(EntityManager entityManager, User user) {
        super(entityManager, user);
    }

    /**
     * This method attaches an event listener to the dependant search field. It will listen for change in value of this field and filter the dependant
     * table accordingly.
     * @param filteredDependantList
     */
    //AKA: FILTERING THE DEPENDENT TABLE BASED ON WHAT WE TYPE ON THE SEARCH FIELD
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

    /**
     * This method maps columns of the dependant tables with the dependant entity. It will then fill up the table with data from the beneficiary database.
     * @param entityManager
     * @param user
     * @param dependants
     */
    //AKA: MAP: 1 COLUMNS IN DEPENDENT TABLE = 1 ATTRIBUTES OF DEPENDENT CLASS
    public void fillingDependantTable(EntityManager entityManager, User user, List<Dependant> dependants){
        ListIterator<Dependant> dependantListIterator = dependants.listIterator();
        //Adding dependants to the dependant observable list
        while (dependantListIterator.hasNext()){
            Dependant dependant = dependantListIterator.next();
            //ADD NECESSARY BUTTONS BESIDE 1 ROW
            Button buttonUpdateInfo = new Button();
            Button buttonAddClaim = new Button();
            Button buttonRemove = new Button();
            //Only system admin and policy holder and policy owner have access to the update info button, remove button
            if (user instanceof PolicyOwner || user instanceof PolicyHolder){
                //MAKE BUTTON TO UPDATE INFO OF DEPENDENT OBJECT BECOME VISIBLE
                buttonUpdateInfo.setText("Update Info");
                //Create a CreationPageController for the Dependant in Update mode by passing in the dependant object to the constructor
                //Open a new scene on the existing stage by calling the showStage static method from the Repeated Code Class
                buttonUpdateInfo.setOnAction(event -> {
                    CreationPageController_Dependant creationPageControllerDependant = new CreationPageController_Dependant(entityManager, user, dependant);
                    StageBuilder.showStage((Stage) buttonUpdateInfo.getScene().getWindow(), creationPageControllerDependant, "DependantCreationPage.fxml", "Dependant Update");

                });
                //MAKE BUTTON TO REMOVE DEPENDENT OBJECTS BECOME VISIBLE
                buttonRemove.setText("Remove");
                dependant.setRemoveButton(buttonRemove);
                //Set action for the remove button. Clicking the button will remove its dependant
                buttonRemove.setOnAction(event -> {
                    CustomerCreateRemove.removeDependant(entityManager, dependant );
                });

                buttonUpdateInfo.setUserData(dependant); //built in method of javafx button class
                dependant.setUpdateInfoButton(buttonUpdateInfo);

                //MAKE BUTTON TO ADD CLAIMS FOR DEPENDENT OBJECT BECOME VISIBLE
                //Only policy holder and policy owner could create a new claim for a dependant
                if (user instanceof PolicyHolder || user instanceof PolicyOwner){
                    buttonAddClaim.setText("Add Claim");
                    buttonAddClaim.setOnAction(event -> {
                        //Create a ClaimCreationPage controller in creation mode by passing the dependant object to the constructor
                        //Open a new scene in the existing stage by calling the showStage static method from the Repeated Code Class
                        CreationPageController_Claim creationPageControllerClaim = new CreationPageController_Claim(entityManager, user, dependant);
                        StageBuilder.showStage((Stage) buttonUpdateInfo.getScene().getWindow(), creationPageControllerClaim, "ClaimCreationPage.fxml", "Claim Creation");
                    });
                    dependant.setAddClaimButton(buttonAddClaim);
                }

                dependantsObservableList.add(dependant);

            }


        }
        //set label for table's column
        dependantId.setCellValueFactory(new PropertyValueFactory<Dependant, String>("id"));
        dependantFullName.setCellValueFactory(new PropertyValueFactory<Dependant, String>("fullName"));
        dependantAddress.setCellValueFactory(new PropertyValueFactory<Dependant, String>("address"));
        dependantEmail.setCellValueFactory(new PropertyValueFactory<Dependant, String>("email"));
        dependantPassword.setCellValueFactory(new PropertyValueFactory<Dependant, String>("password"));
        policyOwnerDependantTable.setCellValueFactory(new PropertyValueFactory<Dependant, String>("policyOwnerId"));
        cardNumberDependantTable.setCellValueFactory(new PropertyValueFactory<Dependant, String>("cardNumber"));
        policyHolderDependantTable.setCellValueFactory(new PropertyValueFactory<Dependant, String>("policyHolderId"));


        //CRUD for Dependent objects

        if ((user instanceof PolicyOwner || user instanceof PolicyHolder)){
            //only PolicyOwner and PolicyHolder and update information of Dependent object
            dependantUpdateInfoButton.setCellValueFactory(new PropertyValueFactory<Dependant, Button>("updateInfoButton"));
        }


        if (user instanceof SystemAdmin || user instanceof PolicyOwner){
            //only system admin and PolicyOwner  can create or remove Dependent objects
            dependantRemoveButton.setCellValueFactory(new PropertyValueFactory<Dependant, Button>("removeButton"));
            if (user instanceof PolicyOwner || user instanceof PolicyHolder){
                //only PolicyOwner and PolicyHolder can add claim for Dependent
                dependantAddClaimButton.setCellValueFactory(new PropertyValueFactory<Dependant, Button>("addClaim"));
            }
        }

        //FilteredList: Wraps an ObservableList and filters it's content using the provided Predicate. All changes in the ObservableList are propagated immediately to the FilteredList.


        //AKA: ADD A NEW, FILTERED LIST OF DEPENDENT INTO THE TABLE SCOPE AFTER THE USER FILTER BY SEARCH BAR
        FilteredList<Dependant> filteredDependantList = new FilteredList<>(dependantsObservableList, b -> true);
        filteringDependantTable(filteredDependantList);
        dependantTable.setItems(filteredDependantList);
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
