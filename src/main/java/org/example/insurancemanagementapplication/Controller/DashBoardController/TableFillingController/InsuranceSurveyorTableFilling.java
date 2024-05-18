package org.example.insurancemanagementapplication.Controller.DashBoardController.TableFillingController;

import Entity.InsuranceSurveyor;
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
 * @created 01/05/2024 16:27
 * @project InsuranceManagementTeamProject
 */
public class InsuranceSurveyorTableFilling extends PolicyOwnerTableFilling {
    //TODO Create a thread that runs in a selected interval that get all Managers from the database and check if new entries exist. If they do, append the new entries to the Observable List
    private ObservableList<InsuranceSurveyor> insuranceSurveyorsObservableList = FXCollections.observableArrayList();
    @FXML
    protected TableView<InsuranceSurveyor> surveyorTable;
    @FXML
    protected TableColumn<InsuranceSurveyor, String> surveyorId;
    @FXML
    protected TableColumn<InsuranceSurveyor, String> surveyorFullName;
    @FXML
    protected TableColumn<InsuranceSurveyor, String> surveyorAddress;
    @FXML
    protected TableColumn<InsuranceSurveyor, String> surveyorPhoneNumber;
    @FXML
    protected TableColumn<InsuranceSurveyor, String> surveyorEmail;
    @FXML
    protected TableColumn<InsuranceSurveyor, String> surveyorPassword;
    @FXML
    protected TableColumn<InsuranceSurveyor, String> manager;
    @FXML
    protected TableColumn<InsuranceSurveyor, Button> surveyorUpdateInfoButton;
    @FXML
    protected TableColumn<InsuranceSurveyor, Button> surveyorRemoveButton;
    @FXML
    protected TextField insuranceSurveyorSearchField;
    @FXML
    protected TableColumn<InsuranceSurveyor, Integer> totalResolvedClaimsISColumn;
    @FXML
    private ChoiceBox<String> insuranceSurveyorSortBox;

    public InsuranceSurveyorTableFilling(EntityManager entityManager, User user) {
        super(entityManager, user);
    }

    public void sortingInsuranceSurveyorTable(SortedList<InsuranceSurveyor> sortedInsuranceSurveyorList) {

        //Comparator class. An instance of this class will be used as a parameter of the sort Method to define the sorting factor. In this class, the sorting factor is the claim's claim amount
        class TotalResolvedClaimsComparatorForInsuranceSurveyor implements Comparator<InsuranceSurveyor> {
            @Override
            public int compare(InsuranceSurveyor firstInsuranceSurveyor, InsuranceSurveyor secondInsuranceSurveyor) {
                return Integer.compare(firstInsuranceSurveyor.getTotalResolvedClaims(), secondInsuranceSurveyor.getTotalResolvedClaims());
            }
        }
        //Total Resolved Claims choiceBox
        //add a listener to the sort list choice box. The listener will monitor the choice box's value to apply the correct sorting
        //not allowed to reverse a sorted list
        insuranceSurveyorSortBox.valueProperty().addListener((observable, oldVal, newVal) -> {

            //only change the observable list if other options except "NONE
            if (!(newVal.equals("NONE"))) {
                if (newVal.equals("Sort By Total Resolved Claims In Ascending Order")) {
                    TotalResolvedClaimsComparatorForInsuranceSurveyor totalResolvedClaimsComparator = new TotalResolvedClaimsComparatorForInsuranceSurveyor();
                    sortedInsuranceSurveyorList.setComparator(totalResolvedClaimsComparator);
                } else if (newVal.equals("Sort By Total Resolved Claims In Descending Order")) {
                    TotalResolvedClaimsComparatorForInsuranceSurveyor totalResolvedClaimsComparator = new TotalResolvedClaimsComparatorForInsuranceSurveyor();
                    sortedInsuranceSurveyorList.setComparator(totalResolvedClaimsComparator.reversed());
                }
            } else { //if choice = "NONE"
                sortedInsuranceSurveyorList.setComparator(null);
            }
        });
    }

    /**
     * Attach an event listener to the surveyor search field that filter the insurance surveyor table according to changes in this field
     *
     * @param filteredSurveyorList
     */
    public void filteringSurveyorTable(FilteredList<InsuranceSurveyor> filteredSurveyorList) {
        insuranceSurveyorSearchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredSurveyorList.setPredicate(insuranceSurveyor -> {
                if (newValue.isEmpty() || newValue == null || newValue.isBlank()) {
                    return true;
                }
                String searchValue = newValue.toLowerCase();
                if (insuranceSurveyor.getId().toLowerCase().contains(searchValue)) {
                    return true;
                } else if (insuranceSurveyor.getFullName().toLowerCase().contains(searchValue)) {
                    return true;
                } else if (insuranceSurveyor.getEmail().toLowerCase().contains(searchValue)) {
                    return true;
                } else if (insuranceSurveyor.getAddress().toLowerCase().contains(searchValue)) {
                    return true;
                } else if (insuranceSurveyor.getPhoneNumber().toLowerCase().contains(searchValue)) {
                    return true;
                } else if (insuranceSurveyor.getInsuranceManagerId().toLowerCase().contains(searchValue)) {
                    return true;
                } else if (insuranceSurveyor.getInsuranceManager().getFullName().toLowerCase().contains(searchValue)) {
                    return true;
                } else {
                    return false;
                }
            });

        });
    }

    /**
     * Mapping the columns of the insurance surveyor tables with Insurance Surveyor entity. Fill up the Insurance Surveyor tables with data from the database
     *
     * @param entityManager
     * @param user
     * @param insuranceSurveyors
     */
    public void fillingInsuranceSurveyorTable(EntityManager entityManager, User user, List<InsuranceSurveyor> insuranceSurveyors) {
        if (user instanceof SystemAdmin) {
            String[] totalResolvedClaimsSortBoxArray = {"Sort By Total Resolved Claims In Ascending Order", "Sort By Total Resolved Claims In Descending Order", "NONE"};
            insuranceSurveyorSortBox.getItems().setAll(totalResolvedClaimsSortBoxArray);
            insuranceSurveyorSortBox.setValue("NONE");
        }
        ListIterator<InsuranceSurveyor> listIteratorInsuranceSurveyor = insuranceSurveyors.listIterator();
        //Adding insurance surveyors to the observable list.
        while (listIteratorInsuranceSurveyor.hasNext()) {
            InsuranceSurveyor insuranceSurveyor = listIteratorInsuranceSurveyor.next();
            //reassign object from database
            insuranceSurveyor = entityManager.find(InsuranceSurveyor.class, insuranceSurveyor.getId());
            //setter
            insuranceSurveyor.setTotalResolvedClaims(ClaimRead.countTotalResolvedClaimOfAnInsuranceSurveyor(insuranceSurveyor));
            Button buttonUpdateInfo = new Button("Update Info");
            buttonList.add(buttonUpdateInfo);
            //Only System admin has access to the update info button and the remove button
            if (user instanceof SystemAdmin) {
                //The Update Info Button will create a CreationPage Controller for the Insurance Surveyor in update mode by passing in the insurance surveyor object
                //It will then open the Insurance Surveyor Creation Page
                InsuranceSurveyor finalInsuranceSurveyor = insuranceSurveyor;
                buttonUpdateInfo.setOnAction(event -> {
                    CreationAndUpdatePageControllerInsuranceSurveyor creationPageControllerInsuranceSurveyor = new CreationAndUpdatePageControllerInsuranceSurveyor(entityManager, user);
                    StageBuilder.showStage((Stage) buttonUpdateInfo.getScene().getWindow(), creationPageControllerInsuranceSurveyor, "InsuranceSurveyorCreationAndUpdatePage.fxml", "Insurance Surveyor Update");


                });

                insuranceSurveyor.setUpdateInfoButton(buttonUpdateInfo);
                //The remove button will remove its Insurance Surveyor from the database
                Button buttonRemove = new Button("Remove");
                buttonList.add(buttonRemove);
                insuranceSurveyor.setRemoveButton(buttonRemove);
                buttonRemove.setOnAction(event -> {
                    EmployeeCreateRemove.removeInsuranceSurveyor(entityManager, finalInsuranceSurveyor);
                    returnToDashBoard(user, entityManager, buttonRemove);
                });
            }

            insuranceSurveyorsObservableList.add(insuranceSurveyor);
        }

        surveyorId.setCellValueFactory(new PropertyValueFactory<InsuranceSurveyor, String>("id"));
        surveyorFullName.setCellValueFactory(new PropertyValueFactory<InsuranceSurveyor, String>("fullName"));
        surveyorAddress.setCellValueFactory(new PropertyValueFactory<InsuranceSurveyor, String>("address"));
        surveyorPhoneNumber.setCellValueFactory(new PropertyValueFactory<InsuranceSurveyor, String>("phoneNumber"));
        surveyorEmail.setCellValueFactory(new PropertyValueFactory<InsuranceSurveyor, String>("email"));
        surveyorPassword.setCellValueFactory(new PropertyValueFactory<InsuranceSurveyor, String>("password"));
        manager.setCellValueFactory(new PropertyValueFactory<InsuranceSurveyor, String>("insuranceManagerId"));
        if (user instanceof SystemAdmin) {
            surveyorUpdateInfoButton.setCellValueFactory(new PropertyValueFactory<InsuranceSurveyor, Button>("updateInfoButton"));
            surveyorRemoveButton.setCellValueFactory(new PropertyValueFactory<InsuranceSurveyor, Button>("removeButton"));
            totalResolvedClaimsISColumn.setCellValueFactory(new PropertyValueFactory<InsuranceSurveyor, Integer>("totalResolvedClaims"));

        }
        FilteredList<InsuranceSurveyor> filteredSurveyorList = new FilteredList<>(insuranceSurveyorsObservableList, b -> true);
        filteringSurveyorTable(filteredSurveyorList);
        SortedList<InsuranceSurveyor> sortedSurveyors = new SortedList<>(filteredSurveyorList);
        if (user instanceof SystemAdmin) {
            sortingInsuranceSurveyorTable(sortedSurveyors);
        }

        surveyorTable.setItems(sortedSurveyors);
    }


}
//Inner Class for thread