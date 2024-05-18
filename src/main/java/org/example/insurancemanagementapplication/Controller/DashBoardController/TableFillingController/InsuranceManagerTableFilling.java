package org.example.insurancemanagementapplication.Controller.DashBoardController.TableFillingController;

import Entity.InsuranceManager;
import Entity.SystemAdmin;
import Entity.User;
import jakarta.persistence.EntityManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.example.insurancemanagementapplication.Controller.CreationAndUpdatePageController.CreationAndUpdatePageControllerInsuranceManager;
import org.example.insurancemanagementapplication.Controller.CreationAndUpdatePageController.CreationAndUpdatePageControllerInsuranceSurveyor;
import org.example.insurancemanagementapplication.Interfaces.ClaimRead;
import org.example.insurancemanagementapplication.Interfaces.EmployeeCreateRemove;
import org.example.insurancemanagementapplication.Utility.StageBuilder;

import java.util.Comparator;
import java.util.List;
import java.util.ListIterator;

/**
 * @author Luong Thanh Trung
 * @version ${}
 * @created 01/05/2024 16:33
 * @project InsuranceManagementTeamProject
 */
public class InsuranceManagerTableFilling extends InsuranceSurveyorTableFilling {
    //TODO Create a thread that runs in a selected interval that get all Managers from the database and check if new entries exist. If they do, append the new entries to the Observable List
    private ObservableList<InsuranceManager> insuranceManagersObservableList = FXCollections.observableArrayList();
    @FXML
    protected TableView<InsuranceManager> managerTable;
    @FXML
    protected TableColumn<InsuranceManager, String> managerId;
    @FXML
    protected TableColumn<InsuranceManager, String> managerFullName;
    @FXML
    protected TableColumn<InsuranceManager, String> managerAddress;
    @FXML
    protected TableColumn<InsuranceManager, String> managerPhoneNumber;
    @FXML
    protected TableColumn<InsuranceManager, String> managerEmail;
    @FXML
    protected TableColumn<InsuranceManager, String> managerPassword;
    @FXML
    protected TableColumn<InsuranceManager, Button> managerUpdateInfoButton;
    @FXML
    protected TableColumn<InsuranceManager, Button> managerAddSurveyorButton;
    @FXML
    protected TableColumn<InsuranceManager, Button> managerRemoveButton;
    @FXML
    protected TextField insuranceManagerSearchField;
    @FXML
    protected TableColumn<InsuranceManager, Integer> totalResolvedClaimsIMColumn;
    @FXML
    private ChoiceBox<String> insuranceManagerSortBox;

    public void sortingInsuranceManagerTable(SortedList<InsuranceManager> sortedInsuranceManagerList) {

        //Comparator class. An instance of this class will be used as a parameter of the sort Method to define the sorting factor. In this class, the sorting factor is the claim's claim amount
        class TotalResolvedClaimsComparator implements Comparator<InsuranceManager> {
            @Override
            public int compare(InsuranceManager firstInsuranceManager, InsuranceManager secondInsuranceManager) {
                return Integer.compare(firstInsuranceManager.getTotalResolvedClaims(), secondInsuranceManager.getTotalResolvedClaims());
            }

        }
        //Total Resolved Claims choiceBox
        //add a listener to the sort list choice box. The listener will monitor the choice box's value to apply the correct sorting
        //not allowed to reverse a sorted list
        insuranceManagerSortBox.valueProperty().addListener((observable, oldVal, newVal) -> {

            //only change the observable list if other options except "NONE
            if (!(newVal.equals("NONE"))) {
                if (newVal.equals("Sort By Total Resolved Claims In Ascending Order")) {
                    TotalResolvedClaimsComparator totalResolvedClaimsComparator = new TotalResolvedClaimsComparator();
                    sortedInsuranceManagerList.setComparator(totalResolvedClaimsComparator);
                } else if (newVal.equals("Sort By Total Resolved Claims In Descending Order")) {
                    TotalResolvedClaimsComparator totalResolvedClaimsComparator = new TotalResolvedClaimsComparator();
                    sortedInsuranceManagerList.setComparator(totalResolvedClaimsComparator.reversed());
                }
            } else { //if choice = "NONE"
                sortedInsuranceManagerList.setComparator(null);
            }
        });
    }

    /**
     * Attach an event listener to the manager search field that filter the insurance manager according to changes in this field
     *
     * @param filteredManagerList
     */
    public void filteringInsuranceManagerTable(FilteredList<InsuranceManager> filteredManagerList) {
        insuranceManagerSearchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredManagerList.setPredicate(insuranceManager -> {
                if (newValue.isEmpty() || newValue == null || newValue.isBlank()) {
                    return true;
                }
                String searchValue = newValue.toLowerCase();
                if (insuranceManager.getId().toLowerCase().contains(searchValue)) {
                    return true;
                } else if (insuranceManager.getFullName().toLowerCase().contains(searchValue)) {
                    return true;
                } else if (insuranceManager.getEmail().toLowerCase().contains(searchValue)) {
                    return true;
                } else if (insuranceManager.getAddress().toLowerCase().contains(searchValue)) {
                    return true;
                } else if (insuranceManager.getPhoneNumber().toLowerCase().contains(searchValue)) {
                    return true;
                } else {
                    return false;
                }
            });

        });
    }

    /**
     * Mapping the columns of the insurance manager tables with Insurance Manager entity. Fill up the Insurance Manager tables with data from the database
     *
     * @param entityManager
     * @param user
     * @param insuranceManagers
     */
    public void fillingInsuranceManagerTable(EntityManager entityManager, User user, List<InsuranceManager> insuranceManagers) {
        if (user instanceof SystemAdmin) {
            //Set choices option for choice box
            String[] totalResolvedClaimsSortBoxArray = {"Sort By Total Resolved Claims In Ascending Order", "Sort By Total Resolved Claims In Descending Order", "NONE"};
            insuranceManagerSortBox.getItems().setAll(totalResolvedClaimsSortBoxArray);
            insuranceManagerSortBox.setValue("NONE");
        }
        ListIterator<InsuranceManager> listIteratorInsuranceManager = insuranceManagers.listIterator();
        //adding insurance managers into the observable list
        while (listIteratorInsuranceManager.hasNext()) {
            InsuranceManager insuranceManager = listIteratorInsuranceManager.next();
            //reassign from database object
            insuranceManager = entityManager.find(InsuranceManager.class, insuranceManager.getId());
            //set total resolved claims
            insuranceManager.setTotalResolvedClaims(ClaimRead.countTotalResolvedClaimOfAnInsuranceManager(insuranceManager));
            Button buttonUpdateInfo = new Button("Update Info");
            buttonList.add(buttonUpdateInfo);
            insuranceManager.setUpdateInfoButton(buttonUpdateInfo);
            //The update info button on each row will create a CreationPage Controller in update mode for the corresponding insurance manager by passing in the insurance manager object
            //It will then open the Insurance Manager Creation Form
            InsuranceManager finalInsuranceManager = insuranceManager;
            buttonUpdateInfo.setOnAction(event -> {
                CreationAndUpdatePageControllerInsuranceManager insuranceManagerCreationPageController = new CreationAndUpdatePageControllerInsuranceManager(entityManager, user, finalInsuranceManager);
                StageBuilder.showStage((Stage) buttonUpdateInfo.getScene().getWindow(), insuranceManagerCreationPageController, "InsuranceManagerCreationAndUpdatePage.fxml", "Insurance Manager Update");
            });

            Button buttonAddSurveyor = new Button("Add Surveyor");
            buttonList.add(buttonAddSurveyor);
            insuranceManager.setAddSurveyorButton(buttonAddSurveyor);
            //The addSurveyor button will create an Insurance Surveyor CreationPage Controller in creation mode by passing the insurance manage object
            //It will then open the Insurance Surveyor Creation Form
            buttonAddSurveyor.setOnAction(event -> {
                CreationAndUpdatePageControllerInsuranceSurveyor creationPageControllerInsuranceSurveyor = new CreationAndUpdatePageControllerInsuranceSurveyor(entityManager, user, finalInsuranceManager);
                StageBuilder.showStage((Stage) buttonAddSurveyor.getScene().getWindow(), creationPageControllerInsuranceSurveyor, "InsuranceSurveyorCreationAndUpdatePage.fxml", "Insurance Surveyor Creation");
            });

            //The remove button will remove its insurance manager from the database
            Button buttonRemove = new Button("Remove");
            buttonList.add(buttonRemove);
            insuranceManager.setRemoveButton(buttonRemove);
            buttonRemove.setOnAction(event -> {
                EmployeeCreateRemove.removeInsuranceManager(entityManager, finalInsuranceManager);
                returnToDashBoard(user, entityManager, buttonRemove);
            });

            insuranceManagersObservableList.add(insuranceManager);
        }
        managerId.setCellValueFactory(new PropertyValueFactory<InsuranceManager, String>("id"));
        managerFullName.setCellValueFactory(new PropertyValueFactory<InsuranceManager, String>("fullName"));
        managerAddress.setCellValueFactory(new PropertyValueFactory<InsuranceManager, String>("address"));
        managerEmail.setCellValueFactory(new PropertyValueFactory<InsuranceManager, String>("email"));
        managerPhoneNumber.setCellValueFactory(new PropertyValueFactory<InsuranceManager, String>("phoneNumber"));
        managerPassword.setCellValueFactory(new PropertyValueFactory<InsuranceManager, String>("password"));
        managerUpdateInfoButton.setCellValueFactory(new PropertyValueFactory<InsuranceManager, Button>("updateInfoButton"));
        managerAddSurveyorButton.setCellValueFactory(new PropertyValueFactory<InsuranceManager, Button>("addSurveyorButton"));
        managerRemoveButton.setCellValueFactory(new PropertyValueFactory<InsuranceManager, Button>("removeButton"));
        totalResolvedClaimsIMColumn.setCellValueFactory(new PropertyValueFactory<InsuranceManager, Integer>("totalResolvedClaims"));

        FilteredList<InsuranceManager> filteredManagerList = new FilteredList<>(insuranceManagersObservableList, b -> true);
        filteringInsuranceManagerTable(filteredManagerList);
        SortedList<InsuranceManager> sortedManagers = new SortedList<>(filteredManagerList);
        sortingInsuranceManagerTable(sortedManagers);
        managerTable.setItems(sortedManagers);


    }

    public InsuranceManagerTableFilling(EntityManager entityManager, User user) {
        super(entityManager, user);
    }


    public TableView<InsuranceManager> getManagerTable() {
        return managerTable;
    }

    public void setManagerTable(TableView<InsuranceManager> managerTable) {
        this.managerTable = managerTable;
    }

    public TableColumn<InsuranceManager, String> getManagerId() {
        return managerId;
    }

    public void setManagerId(TableColumn<InsuranceManager, String> managerId) {
        this.managerId = managerId;
    }

    public TableColumn<InsuranceManager, String> getManagerFullName() {
        return managerFullName;
    }

    public void setManagerFullName(TableColumn<InsuranceManager, String> managerFullName) {
        this.managerFullName = managerFullName;
    }

    public TableColumn<InsuranceManager, String> getManagerAddress() {
        return managerAddress;
    }

    public void setManagerAddress(TableColumn<InsuranceManager, String> managerAddress) {
        this.managerAddress = managerAddress;
    }

    public TableColumn<InsuranceManager, String> getManagerPhoneNumber() {
        return managerPhoneNumber;
    }

    public void setManagerPhoneNumber(TableColumn<InsuranceManager, String> managerPhoneNumber) {
        this.managerPhoneNumber = managerPhoneNumber;
    }

    public TableColumn<InsuranceManager, String> getManagerEmail() {
        return managerEmail;
    }

    public void setManagerEmail(TableColumn<InsuranceManager, String> managerEmail) {
        this.managerEmail = managerEmail;
    }

    public TableColumn<InsuranceManager, String> getManagerPassword() {
        return managerPassword;
    }

    public void setManagerPassword(TableColumn<InsuranceManager, String> managerPassword) {
        this.managerPassword = managerPassword;
    }

    public TableColumn<InsuranceManager, Button> getManagerUpdateInfoButton() {
        return managerUpdateInfoButton;
    }

    public void setManagerUpdateInfoButton(TableColumn<InsuranceManager, Button> managerUpdateInfoButton) {
        this.managerUpdateInfoButton = managerUpdateInfoButton;
    }

    public TableColumn<InsuranceManager, Button> getManagerAddSurveyorButton() {
        return managerAddSurveyorButton;
    }

    public void setManagerAddSurveyorButton(TableColumn<InsuranceManager, Button> managerAddSurveyorButton) {
        this.managerAddSurveyorButton = managerAddSurveyorButton;
    }

    public TableColumn<InsuranceManager, Button> getManagerRemoveButton() {
        return managerRemoveButton;
    }

    public void setManagerRemoveButton(TableColumn<InsuranceManager, Button> managerRemoveButton) {
        this.managerRemoveButton = managerRemoveButton;
    }

    public TextField getInsuranceManagerSearchField() {
        return insuranceManagerSearchField;
    }

    public void setInsuranceManagerSearchField(TextField insuranceManagerSearchField) {
        this.insuranceManagerSearchField = insuranceManagerSearchField;
    }
}

