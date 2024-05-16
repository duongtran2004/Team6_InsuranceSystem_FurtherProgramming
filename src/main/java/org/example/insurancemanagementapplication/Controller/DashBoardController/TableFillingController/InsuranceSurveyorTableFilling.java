package org.example.insurancemanagementapplication.Controller.DashBoardController.TableFillingController;

import Entity.InsuranceSurveyor;
import Entity.SystemAdmin;
import Entity.User;
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
import org.example.insurancemanagementapplication.Controller.CreationAndUpdatePageController.CreationAndUpdatePageControllerInsuranceSurveyor;
import org.example.insurancemanagementapplication.Interfaces.ClaimRead;
import org.example.insurancemanagementapplication.Interfaces.EmployeeCreateRemove;
import org.example.insurancemanagementapplication.Utility.StageBuilder;

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

    public InsuranceSurveyorTableFilling(EntityManager entityManager, User user) {
        super(entityManager, user);
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
                    CreationAndUpdatePageControllerInsuranceSurveyor creationPageControllerInsuranceSurveyor = new CreationAndUpdatePageControllerInsuranceSurveyor(entityManager, user, finalInsuranceSurveyor);
                    StageBuilder.showStage((Stage) buttonUpdateInfo.getScene().getWindow(), creationPageControllerInsuranceSurveyor, "InsuranceSurveyorCreationAndUpdatePage.fxml", "Insurance Surveyor Update");


                });

                insuranceSurveyor.setUpdateInfoButton(buttonUpdateInfo);
                //The remove button will remove its Insurance Surveyor from the database
                Button buttonRemove = new Button("Remove");
                buttonList.add(buttonRemove);
                insuranceSurveyor.setRemoveButton(buttonRemove);
                buttonRemove.setOnAction(event -> {
                    EmployeeCreateRemove.removeInsuranceSurveyor(entityManager, finalInsuranceSurveyor);
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
        surveyorTable.setItems(filteredSurveyorList);
    }


}
//Inner Class for thread