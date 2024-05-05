package org.example.insurancemanagementapplication.Controller.DashBoardController.TableFillingController;

import Entity.InsuranceSurveyor;
import Entity.SystemAdmin;
import Entity.User;
import jakarta.persistence.EntityManager;
import javafx.collections.FXCollections;
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
import org.example.insurancemanagementapplication.Controller.CreationPageController.CreationPageController_InsuranceSurveyor;
import org.example.insurancemanagementapplication.Interfaces.EmployeeCreateRemove;
import org.example.insurancemanagementapplication.MainEntryPoint;
import org.example.insurancemanagementapplication.Utility.RepeatedCode;

import java.io.IOException;
import java.util.List;
import java.util.ListIterator;

/**
 * @author Luong Thanh Trung
 * @version ${}
 * @created 01/05/2024 16:27
 * @project InsuranceManagementTeamProject
 */
public class InsuranceSurveyorTableFilling extends PolicyOwnerTableFilling{
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
    protected  TableColumn<InsuranceSurveyor, Button> surveyorUpdateInfoButton;
    @FXML
    protected TableColumn<InsuranceSurveyor, Button> surveyorRemoveButton;
    @FXML
    protected TextField insuranceSurveyorSearchField;

    public InsuranceSurveyorTableFilling(EntityManager entityManager, User user) {
        super(entityManager, user);
    }

    public void filteringSurveyorTable(FilteredList<InsuranceSurveyor> filteredSurveyorList){
        insuranceSurveyorSearchField.textProperty().addListener((observable, oldValue, newValue)->{
            filteredSurveyorList.setPredicate(insuranceSurveyor -> {
                if (newValue.isEmpty() || newValue == null || newValue.isBlank()){
                    return true;
                }
                String searchValue = newValue.toLowerCase();
                if (insuranceSurveyor.getId().toLowerCase().contains(searchValue)){
                    return true;
                }
                else if (insuranceSurveyor.getFullName().toLowerCase().contains(searchValue)){
                    return true;
                }
                else if (insuranceSurveyor.getEmail().toLowerCase().contains(searchValue)){
                    return true;
                }
                else if (insuranceSurveyor.getAddress().toLowerCase().contains(searchValue)){
                    return true;
                }
                else if (insuranceSurveyor.getPhoneNumber().toLowerCase().contains(searchValue)){
                    return true;
                }
                else if (insuranceSurveyor.getInsuranceManagerId().toLowerCase().contains(searchValue)){
                    return true;
                }
                else if (insuranceSurveyor.getInsuranceManager().getFullName().toLowerCase().contains(searchValue)){
                    return true;
                }
                else {
                    return false;
                }
            });

        });
    }

    public void fillingInsuranceSurveyorTable(EntityManager entityManager, User user, List<InsuranceSurveyor> insuranceSurveyors){
        ListIterator<InsuranceSurveyor> listIteratorInsuranceSurveyor = insuranceSurveyors.listIterator();
        while (listIteratorInsuranceSurveyor.hasNext()){
            InsuranceSurveyor insuranceSurveyor = listIteratorInsuranceSurveyor.next();
            Button buttonUpdateInfo = new Button("Update Info");
            if (user instanceof SystemAdmin){
                buttonUpdateInfo.setOnAction(event -> {
                    CreationPageController_InsuranceSurveyor creationPageControllerInsuranceSurveyor = new CreationPageController_InsuranceSurveyor(entityManager, user, insuranceSurveyor);
                    RepeatedCode.showStage((Stage) buttonUpdateInfo.getScene().getWindow(), creationPageControllerInsuranceSurveyor, "InsuranceSurveyorCreationPage.fxml", "Insurance Surveyor Update");
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(MainEntryPoint.class.getResource("InsuranceSurveyorCreationPage.fxml"));
                    fxmlLoader.setController(creationPageControllerInsuranceSurveyor);
                    try {
                        Scene scene = new Scene(fxmlLoader.load());
                        Stage stage = (Stage) buttonUpdateInfo.getScene().getWindow();
                        stage.setScene(scene);
                        stage.show();

                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                });
                insuranceSurveyor.setUpdateInfoButton(buttonUpdateInfo);
                Button buttonRemove = new Button("Remove");
                insuranceSurveyor.setRemoveButton(buttonRemove);
                buttonRemove.setOnAction(event -> {
                    EmployeeCreateRemove.removeInsuranceSurveyor(entityManager, insuranceSurveyor);
                });
            }

            insuranceSurveyorsObservableList.add(insuranceSurveyor);
        }

        surveyorId.setCellValueFactory(new PropertyValueFactory<InsuranceSurveyor, String>("id"));
        surveyorFullName.setCellValueFactory(new PropertyValueFactory<InsuranceSurveyor, String>("fullName"));
        surveyorAddress.setCellValueFactory(new PropertyValueFactory<InsuranceSurveyor, String>("address"));
        surveyorEmail.setCellValueFactory(new PropertyValueFactory<InsuranceSurveyor, String>("email"));
        surveyorPassword.setCellValueFactory(new PropertyValueFactory<InsuranceSurveyor, String>("password"));
        manager.setCellValueFactory(new PropertyValueFactory<InsuranceSurveyor, String>("insuranceManagerId"));
        if (user instanceof SystemAdmin){
            surveyorUpdateInfoButton.setCellValueFactory(new PropertyValueFactory<InsuranceSurveyor, Button>("updateInfoButton"));
            surveyorRemoveButton.setCellValueFactory(new PropertyValueFactory<InsuranceSurveyor, Button>("removeButton"));
        }
        FilteredList<InsuranceSurveyor> filteredSurveyorList = new FilteredList<>(insuranceSurveyorsObservableList, b -> true);
        filteringSurveyorTable(filteredSurveyorList);
        surveyorTable.setItems(filteredSurveyorList);
    }


}
