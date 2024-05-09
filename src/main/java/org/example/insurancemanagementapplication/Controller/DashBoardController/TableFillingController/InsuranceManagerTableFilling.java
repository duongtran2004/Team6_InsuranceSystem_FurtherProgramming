package org.example.insurancemanagementapplication.Controller.DashBoardController.TableFillingController;

import Entity.InsuranceManager;
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
import org.example.insurancemanagementapplication.Controller.CreationPageController.CreationPageControllerInsuranceManager;
import org.example.insurancemanagementapplication.Controller.CreationPageController.CreationPageControllerInsuranceSurveyor;
import org.example.insurancemanagementapplication.Interfaces.EmployeeCreateRemove;
import org.example.insurancemanagementapplication.Utility.StageBuilder;

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
    protected TextField  insuranceManagerSearchField;


    /**
     * Attach an event listener to the manager search field that filter the insurance manager according to changes in this field
     * @param filteredManagerList
     */
    public void filteringInsuranceManagerTable(FilteredList<InsuranceManager> filteredManagerList){
        insuranceManagerSearchField.textProperty().addListener((observable, oldValue, newValue)->{
            filteredManagerList.setPredicate(insuranceManager -> {
                if (newValue.isEmpty() || newValue == null || newValue.isBlank()){
                    return true;
                }
                String searchValue = newValue.toLowerCase();
                if (insuranceManager.getId().toLowerCase().contains(searchValue)){
                    return true;
                }
                else if (insuranceManager.getFullName().toLowerCase().contains(searchValue)){
                    return true;
                }
                else if (insuranceManager.getEmail().toLowerCase().contains(searchValue)){
                    return true;
                }
                else if (insuranceManager.getAddress().toLowerCase().contains(searchValue)){
                    return true;
                }
                else if (insuranceManager.getPhoneNumber().toLowerCase().contains(searchValue)){
                    return true;
                }
                else {
                    return false;
                }
            });

        });
    }
    /**
     * Mapping the columns of the insurance manager tables with Insurance Manager entity. Fill up the Insurance Manager tables with data from the database
     * @param entityManager
     * @param user
     * @param insuranceManagers
     */
    public void fillingInsuranceManagerTable(EntityManager entityManager, User user, List<InsuranceManager> insuranceManagers){
        ListIterator<InsuranceManager> listIteratorInsuranceManager = insuranceManagers.listIterator();
        //adding insurance managers into the observable list
        while (listIteratorInsuranceManager.hasNext()){
            InsuranceManager insuranceManager = listIteratorInsuranceManager.next();
            Button buttonUpdateInfo = new Button("Update Info");
            insuranceManager.setUpdateInfoButton(buttonUpdateInfo);
            //The update info button on each row will create a CreationPage Controller in update mode for the corresponding insurance manager by passing in the insurance manager object
            //It will then open the Insurance Manager Creation Form
            buttonUpdateInfo.setOnAction(event -> {
                CreationPageControllerInsuranceManager insuranceManagerCreationPageController = new CreationPageControllerInsuranceManager(entityManager, user, insuranceManager);
                StageBuilder.showStage((Stage) buttonUpdateInfo.getScene().getWindow(), insuranceManagerCreationPageController, "InsuranceManagerCreationPage.fxml", "Insurance Manager Update");
            });

            Button buttonAddSurveyor = new Button("Add Surveyor");
            insuranceManager.setAddSurveyorButton(buttonAddSurveyor);
            //The addSurveyor button will create an Insurance Surveyor CreationPage Controller in creation mode by passing the insurance manage object
            //It will then open the Insurance Surveyor Creation Form
            buttonAddSurveyor.setOnAction(event ->{
                CreationPageControllerInsuranceSurveyor creationPageControllerInsuranceSurveyor = new CreationPageControllerInsuranceSurveyor(entityManager, user, insuranceManager);
               StageBuilder.showStage((Stage) buttonAddSurveyor.getScene().getWindow(), creationPageControllerInsuranceSurveyor, "InsuranceSurveyorCreationPage.fxml", "Insurance Surveyor Creation");
            });

            //The remove button will remove its insurance manager from the database
            Button buttonRemove = new Button("Remove");
            insuranceManager.setRemoveButton(buttonRemove);
            buttonRemove.setOnAction(event -> {
                EmployeeCreateRemove.removeInsuranceManager(entityManager, insuranceManager);
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
        managerRemoveButton.setCellValueFactory(new PropertyValueFactory<InsuranceManager, Button>("removeButton") );
        FilteredList<InsuranceManager> filteredManagerList = new FilteredList<>(insuranceManagersObservableList, b -> true);
        filteringInsuranceManagerTable(filteredManagerList);
        managerTable.setItems(filteredManagerList);
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
