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
    protected static TableView<InsuranceManager> managerTable;
    @FXML
    protected static TableColumn<InsuranceManager, String> managerId;
    @FXML
    protected static TableColumn<InsuranceManager, String> managerFullName;
    @FXML
    protected static TableColumn<InsuranceManager, String> managerAddress;
    @FXML
    protected static TableColumn<InsuranceManager, String> managerPhoneNumber;
    @FXML
    protected static TableColumn<InsuranceManager, String> managerEmail;
    @FXML
    protected static TableColumn<InsuranceManager, String> managerPassword;
    @FXML
    protected static TableColumn<InsuranceManager, Button> managerUpdateInfoButton;
    @FXML
    protected static TableColumn<InsuranceManager, Button> managerAddSurveyorButton;
    @FXML
    protected static TableColumn<InsuranceManager, Button> managerRemoveButton;
    @FXML
    protected static TextField insuranceManagerSearchField;

    protected static void fillingInsuranceManagerTable(EntityManager entityManager, User user, List<InsuranceManager> insuranceManagers, ObservableList<InsuranceManager> insuranceManagersObservableList){
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
        managerId.setCellValueFactory(new PropertyValueFactory<InsuranceManager, String>("id"));
        managerFullName.setCellValueFactory(new PropertyValueFactory<InsuranceManager, String>("fullName"));
        managerAddress.setCellValueFactory(new PropertyValueFactory<InsuranceManager, String>("address"));
        managerEmail.setCellValueFactory(new PropertyValueFactory<InsuranceManager, String>("email"));
        managerPassword.setCellValueFactory(new PropertyValueFactory<InsuranceManager, String>("password"));
        managerUpdateInfoButton.setCellValueFactory(new PropertyValueFactory<InsuranceManager, Button>("updateInfoButton"));
        managerAddSurveyorButton.setCellValueFactory(new PropertyValueFactory<InsuranceManager, Button>("addSurveyorButton"));
        managerRemoveButton.setCellValueFactory(new PropertyValueFactory<InsuranceManager, Button>("removeButton") );
        managerTable.getItems().setAll(filteredManagerList);
    }

}
