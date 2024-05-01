package org.example.insurancemanagementapplication.Controller.DashBoardController.TableFillingController;

import Entity.Customer;
import Entity.Dependant;
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
import org.example.insurancemanagementapplication.Controller.CreationPageController.CreationPageController_Dependant;
import org.example.insurancemanagementapplication.Interfaces.CustomerCreateRemove;
import org.example.insurancemanagementapplication.MainEntryPoint;

import java.io.IOException;
import java.util.List;
import java.util.ListIterator;

/**
 * @author Luong Thanh Trung
 * @version ${}
 * @created 01/05/2024 15:33
 * @project InsuranceManagementTeamProject
 */
public abstract class DependantTableFilling extends ClaimTableFilling {
    @FXML
    protected static TableView<Dependant> dependantTable;
    @FXML
    protected static TableColumn<Dependant, String> dependantId;
    @FXML
    protected static TableColumn<Dependant, String> dependantFullName;
    @FXML
    protected static TableColumn<Dependant, String> dependantAddress;
    @FXML
    protected static TableColumn<Dependant, String> dependantPhoneNumber;
    @FXML
    protected static TableColumn<Dependant, String> dependantEmail;
    @FXML
    protected static TableColumn<Dependant, String> dependantPassword;
    @FXML
    protected static TableColumn<Dependant, String> policyOwnerDependantTable;
    @FXML
    protected static TableColumn<Dependant, String> cardNumberDependantTable;
    @FXML
    protected static TableColumn<Dependant, Button> dependantUpdateInfoButton;
    @FXML
    protected static TableColumn<Dependant, Button> policyHolderAddClaimButton;

    @FXML
    protected static TableColumn<Dependant, Button> policyHolderRemoveButton;
    @FXML
    protected static TableColumn<Dependant, String> policyHolderDependantTable;
    @FXML
    protected static TextField dependantSearchField;

    protected static void fillingDependantTable(EntityManager entityManager, User user, List<Dependant> dependants){
        ObservableList<Dependant> dependantObservableList = FXCollections.observableArrayList();
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
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(MainEntryPoint.class.getResource("DependantCreationPage.fxml"));
                    fxmlLoader.setController(creationPageControllerDependant);
                    try {
                        Scene scene = new Scene(fxmlLoader.load());
                        Stage stage = (Stage) buttonUpdateInfo.getScene().getWindow();
                        stage.setScene(scene);
                        stage.show();

                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
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
                    dependant.setAddClaimButton(buttonAddClaim);
                }

                dependantObservableList.add(dependant);

            }


        }
        FilteredList<Dependant> filteredDependantList = new FilteredList<>(dependantObservableList, b -> true);
        dependantSearchField.textProperty().addListener((observable, oldValue, newValue)->{
            filteredDependantList.setPredicate(dependant -> {
                if (newValue.isEmpty() || newValue == null || newValue.isBlank()){
                    return true;
                }
                String searchValue = newValue.toLowerCase();
                if (dependant.getId().equals(searchValue)){
                    return true;
                }
                else if (dependant.getFullName().equals(searchValue)){
                    return true;
                }
                else if (dependant.getAddress().equals(searchValue)){
                    return true;
                }
                else if (dependant.getEmail().equals(searchValue)){
                    return true;
                }
                else if (dependant.getPhoneNumber().equals(searchValue)){
                    return true;
                }
                else if (dependant.getPolicyOwnerId().equals(searchValue)){
                    return true;
                }
                else if(dependant.getPolicyOwner().getFullName().equals(searchValue)) {
                    return true;
                }
                else if (dependant.getPolicyHolderId().equals(searchValue)){
                    return true;
                }
                else if(dependant.getPolicyHolder().getFullName().equals(searchValue)) {
                    return true;
                }
                else {
                    return false;
                }
            });
        });
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
            policyHolderRemoveButton.setCellValueFactory(new PropertyValueFactory<Dependant, Button>("removeButton"));
            if (!(user instanceof SystemAdmin)){
                policyHolderAddClaimButton.setCellValueFactory(new PropertyValueFactory<Dependant, Button>("addClaim"));
            }
        }

        dependantTable.getItems().setAll(dependantObservableList);
    }
}
