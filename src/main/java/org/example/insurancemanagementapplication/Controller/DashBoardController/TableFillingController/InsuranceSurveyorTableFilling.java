package org.example.insurancemanagementapplication.Controller.DashBoardController.TableFillingController;

import Entity.InsuranceSurveyor;
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
import org.example.insurancemanagementapplication.Controller.CreationPageController.CreationPageController_InsuranceSurveyor;
import org.example.insurancemanagementapplication.Interfaces.EmployeeCreateRemove;
import org.example.insurancemanagementapplication.MainEntryPoint;

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
    @FXML
    private TableView<InsuranceSurveyor> surveyorTable;
    @FXML
    private TableColumn<InsuranceSurveyor, String> surveyorId;
    @FXML
    private TableColumn<InsuranceSurveyor, String> surveyorFullName;
    @FXML
    private TableColumn<InsuranceSurveyor, String> surveyorAddress;
    @FXML
    private TableColumn<InsuranceSurveyor, String> surveyorPhoneNumber;
    @FXML
    private TableColumn<InsuranceSurveyor, String> surveyorEmail;
    @FXML
    private TableColumn<InsuranceSurveyor, String> surveyorPassword;
    @FXML
    private TableColumn<InsuranceSurveyor, String> manager;
    @FXML
    private  TableColumn<InsuranceSurveyor, Button> surveyorUpdateInfoButton;
    @FXML
    private TableColumn<InsuranceSurveyor, Button> surveyorRemoveButton;
    @FXML
    private TextField insuranceSurveyorSearchField;

    public void fillingInsuranceSurveyorTable(EntityManager entityManager, User user, List<InsuranceSurveyor> insuranceSurveyors, ObservableList<InsuranceSurveyor> insuranceSurveyorsObservableList){
        ListIterator<InsuranceSurveyor> listIteratorInsuranceSurveyor = insuranceSurveyors.listIterator();
        while (listIteratorInsuranceSurveyor.hasNext()){
            InsuranceSurveyor insuranceSurveyor = listIteratorInsuranceSurveyor.next();
            Button buttonUpdateInfo = new Button("Update Info");
            if (user instanceof SystemAdmin){
                buttonUpdateInfo.setOnAction(event -> {
                    CreationPageController_InsuranceSurveyor creationPageControllerInsuranceSurveyor = new CreationPageController_InsuranceSurveyor(entityManager, user, insuranceSurveyor);
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
        FilteredList<InsuranceSurveyor> filteredSurveyorList = new FilteredList<>(insuranceSurveyorsObservableList, b -> true);
        insuranceSurveyorSearchField.textProperty().addListener((observable, oldValue, newValue)->{
            filteredSurveyorList.setPredicate(insuranceSurveyor -> {
                if (newValue.isEmpty() || newValue == null || newValue.isBlank()){
                    return true;
                }
                String searchValue = newValue.toLowerCase();
                if (insuranceSurveyor.getId().equals(searchValue)){
                    return true;
                }
                else if (insuranceSurveyor.getFullName().equals(searchValue)){
                    return true;
                }
                else if (insuranceSurveyor.getEmail().equals(searchValue)){
                    return true;
                }
                else if (insuranceSurveyor.getAddress().equals(searchValue)){
                    return true;
                }
                else if (insuranceSurveyor.getPhoneNumber().equals(searchValue)){
                    return true;
                }
                else if (insuranceSurveyor.getInsuranceManagerId().equals(searchValue)){
                    return true;
                }
                else if (insuranceSurveyor.getInsuranceManager().getFullName().equals(searchValue)){
                    return true;
                }
                else {
                    return false;
                }
            });

        });
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

        surveyorTable.getItems().setAll(filteredSurveyorList);
    }

    public InsuranceSurveyorTableFilling(){

    }

    public TableView<InsuranceSurveyor> getSurveyorTable() {
        return surveyorTable;
    }

    public void setSurveyorTable(TableView<InsuranceSurveyor> surveyorTable) {
        this.surveyorTable = surveyorTable;
    }

    public TableColumn<InsuranceSurveyor, String> getSurveyorId() {
        return surveyorId;
    }

    public void setSurveyorId(TableColumn<InsuranceSurveyor, String> surveyorId) {
        this.surveyorId = surveyorId;
    }

    public TableColumn<InsuranceSurveyor, String> getSurveyorFullName() {
        return surveyorFullName;
    }

    public void setSurveyorFullName(TableColumn<InsuranceSurveyor, String> surveyorFullName) {
        this.surveyorFullName = surveyorFullName;
    }

    public TableColumn<InsuranceSurveyor, String> getSurveyorAddress() {
        return surveyorAddress;
    }

    public void setSurveyorAddress(TableColumn<InsuranceSurveyor, String> surveyorAddress) {
        this.surveyorAddress = surveyorAddress;
    }

    public TableColumn<InsuranceSurveyor, String> getSurveyorPhoneNumber() {
        return surveyorPhoneNumber;
    }

    public void setSurveyorPhoneNumber(TableColumn<InsuranceSurveyor, String> surveyorPhoneNumber) {
        this.surveyorPhoneNumber = surveyorPhoneNumber;
    }

    public TableColumn<InsuranceSurveyor, String> getSurveyorEmail() {
        return surveyorEmail;
    }

    public void setSurveyorEmail(TableColumn<InsuranceSurveyor, String> surveyorEmail) {
        this.surveyorEmail = surveyorEmail;
    }

    public TableColumn<InsuranceSurveyor, String> getSurveyorPassword() {
        return surveyorPassword;
    }

    public void setSurveyorPassword(TableColumn<InsuranceSurveyor, String> surveyorPassword) {
        this.surveyorPassword = surveyorPassword;
    }

    public TableColumn<InsuranceSurveyor, String> getManager() {
        return manager;
    }

    public void setManager(TableColumn<InsuranceSurveyor, String> manager) {
        this.manager = manager;
    }

    public TableColumn<InsuranceSurveyor, Button> getSurveyorUpdateInfoButton() {
        return surveyorUpdateInfoButton;
    }

    public void setSurveyorUpdateInfoButton(TableColumn<InsuranceSurveyor, Button> surveyorUpdateInfoButton) {
        this.surveyorUpdateInfoButton = surveyorUpdateInfoButton;
    }

    public TableColumn<InsuranceSurveyor, Button> getSurveyorRemoveButton() {
        return surveyorRemoveButton;
    }

    public void setSurveyorRemoveButton(TableColumn<InsuranceSurveyor, Button> surveyorRemoveButton) {
        this.surveyorRemoveButton = surveyorRemoveButton;
    }

    public TextField getInsuranceSurveyorSearchField() {
        return insuranceSurveyorSearchField;
    }

    public void setInsuranceSurveyorSearchField(TextField insuranceSurveyorSearchField) {
        this.insuranceSurveyorSearchField = insuranceSurveyorSearchField;
    }
}
