package org.example.insurancemanagementapplication.Controller.DashBoardController.TableFillingController;

import Entity.InsuranceManager;
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
import org.example.insurancemanagementapplication.Controller.CreationPageController.CreationPageController_InsuranceManager;
import org.example.insurancemanagementapplication.Controller.CreationPageController.CreationPageController_InsuranceSurveyor;
import org.example.insurancemanagementapplication.Interfaces.EmployeeCreateRemove;
import org.example.insurancemanagementapplication.MainEntryPoint;

import java.io.IOException;
import java.util.List;
import java.util.ListIterator;

/**
 * @author Luong Thanh Trung
 * @version ${}
 * @created 01/05/2024 16:33
 * @project InsuranceManagementTeamProject
 */
public class InsuranceManagerTableFilling extends InsuranceSurveyorTableFilling {
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
    protected TextField  insuranceManagerSearchField;

    public void filteringInsuranceManagerTable(FilteredList<InsuranceManager> filteredManagerList){
        insuranceManagerSearchField.textProperty().addListener((observable, oldValue, newValue)->{
            filteredManagerList.setPredicate(insuranceManager -> {
                if (newValue.isEmpty() || newValue == null || newValue.isBlank()){
                    return true;
                }
                String searchValue = newValue.toLowerCase();
                if (insuranceManager.getId().equals(searchValue)){
                    return true;
                }
                else if (insuranceManager.getFullName().equals(searchValue)){
                    return true;
                }
                else if (insuranceManager.getEmail().equals(searchValue)){
                    return true;
                }
                else if (insuranceManager.getAddress().equals(searchValue)){
                    return true;
                }
                else if (insuranceManager.getPhoneNumber().equals(searchValue)){
                    return true;
                }
                else {
                    return false;
                }
            });

        });
    }

    public void fillingInsuranceManagerTable(EntityManager entityManager, User user, List<InsuranceManager> insuranceManagers, ObservableList<InsuranceManager> insuranceManagersObservableList){
        ListIterator<InsuranceManager> listIteratorInsuranceManager = insuranceManagers.listIterator();
        while (listIteratorInsuranceManager.hasNext()){
            InsuranceManager insuranceManager = listIteratorInsuranceManager.next();
            Button buttonUpdateInfo = new Button("Update Info");
            insuranceManager.setUpdateInfoButton(buttonUpdateInfo);
            buttonUpdateInfo.setUserData(insuranceManager);
            buttonUpdateInfo.setOnAction(event -> {
                CreationPageController_InsuranceManager insuranceManagerCreationPageController = new CreationPageController_InsuranceManager(entityManager, user, (InsuranceManager) buttonUpdateInfo.getUserData());
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(MainEntryPoint.class.getResource("InsuranceManagerCreationPage.fxml"));
                fxmlLoader.setController(insuranceManagerCreationPageController);
                try {
                    Scene scene = new Scene(fxmlLoader.load());
                    Stage stage = (Stage) buttonUpdateInfo.getScene().getWindow();
                    stage.setScene(scene);
                    stage.show();

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            Button buttonAddSurveyor = new Button("Add Surveyor");
            insuranceManager.setAddSurveyorButton(buttonAddSurveyor);
            buttonAddSurveyor.setUserData(insuranceManager);
            buttonAddSurveyor.setOnAction(event ->{
                CreationPageController_InsuranceSurveyor creationPageControllerInsuranceSurveyor = new CreationPageController_InsuranceSurveyor(entityManager, user, (InsuranceManager) buttonAddSurveyor.getUserData());
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(MainEntryPoint.class.getResource("InsuranceSurveyorCreationPage.fxml"));
                fxmlLoader.setController(creationPageControllerInsuranceSurveyor);
                try {
                    Scene scene = new Scene(fxmlLoader.load());
                    Stage stage = (Stage) buttonAddSurveyor.getScene().getWindow();
                    stage.setScene(scene);
                    stage.show();

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            Button buttonRemove = new Button("Remove");
            insuranceManager.setRemoveButton(buttonRemove);
            buttonRemove.setOnAction(event -> {
                EmployeeCreateRemove.removeInsuranceManager(entityManager, insuranceManager);
            });

            insuranceManagersObservableList.add(insuranceManager);
        }
        FilteredList<InsuranceManager> filteredManagerList = new FilteredList<>(insuranceManagersObservableList, b -> true);
        filteringInsuranceManagerTable(filteredManagerList);

        managerId.setCellValueFactory(new PropertyValueFactory<InsuranceManager, String>("id"));
        managerFullName.setCellValueFactory(new PropertyValueFactory<InsuranceManager, String>("fullName"));
        managerAddress.setCellValueFactory(new PropertyValueFactory<InsuranceManager, String>("address"));
        managerEmail.setCellValueFactory(new PropertyValueFactory<InsuranceManager, String>("email"));
        managerPhoneNumber.setCellValueFactory(new PropertyValueFactory<InsuranceManager, String>("phoneNumber"));
        managerPassword.setCellValueFactory(new PropertyValueFactory<InsuranceManager, String>("password"));
        managerUpdateInfoButton.setCellValueFactory(new PropertyValueFactory<InsuranceManager, Button>("updateInfoButton"));
        managerAddSurveyorButton.setCellValueFactory(new PropertyValueFactory<InsuranceManager, Button>("addSurveyorButton"));
        managerRemoveButton.setCellValueFactory(new PropertyValueFactory<InsuranceManager, Button>("removeButton") );
        managerTable.getItems().setAll(filteredManagerList);
    }
    public InsuranceManagerTableFilling(EntityManager entityManager, User user){
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
