package org.example.insurancemanagementapplication.Controller.DashBoardController.TableFillingController;

import Entity.PolicyOwner;
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
import org.example.insurancemanagementapplication.Controller.CreationPageController.CreationPageController_PolicyHolder;
import org.example.insurancemanagementapplication.Controller.CreationPageController.CreationPageController_PolicyOwner;
import org.example.insurancemanagementapplication.Interfaces.CustomerCreateRemove;
import org.example.insurancemanagementapplication.MainEntryPoint;

import java.io.IOException;
import java.util.List;
import java.util.ListIterator;

/**
 * @author Luong Thanh Trung
 * @version ${}
 * @created 01/05/2024 16:11
 * @project InsuranceManagementTeamProject
 */
public class PolicyOwnerTableFilling extends PolicyHolderTableFilling {
    @FXML
    protected TableView<PolicyOwner> policyOwnerTable;
    @FXML
    protected TableColumn<PolicyOwner, String> policyOwnerId;
    @FXML
    protected TableColumn<PolicyOwner, String> policyOwnerFullName;
    @FXML
    protected TableColumn<PolicyOwner, String> policyOwnerAddress;
    @FXML
    protected TableColumn<PolicyOwner, String> policyOwnerPhoneNumber;
    @FXML
    protected TableColumn<PolicyOwner, String> policyOwnerEmail;
    @FXML
    protected TableColumn<PolicyOwner, String> policyOwnerPassword;
    @FXML
    protected TableColumn<PolicyOwner, Button> policyOwnerUpdateInfoButton;
    @FXML
    protected TableColumn<PolicyOwner, Button> policyOwnerAddPolicyButton;
    @FXML
    protected TableColumn<PolicyOwner, Button> policyOwnerRemoveButton;
    @FXML
    protected TextField policyOwnerSearchField;

    public PolicyOwnerTableFilling(EntityManager entityManager, User user) {
        super(entityManager, user);
    }

    public void filteringPolicyOwnerTable(FilteredList<PolicyOwner> filteredPolicyOwnerList){
        policyOwnerSearchField.textProperty().addListener((observable, oldValue, newValue)->{
            filteredPolicyOwnerList.setPredicate(policyOwner -> {
                if (newValue.isEmpty() || newValue == null || newValue.isBlank()){
                    return true;
                }
                String searchValue = newValue.toLowerCase();
                if (policyOwner.getId().equals(searchValue)){
                    return true;
                }
                else if (policyOwner.getFullName().equals(searchValue)){
                    return true;
                }
                else if (policyOwner.getEmail().equals(searchValue)){
                    return true;
                }
                else if (policyOwner.getAddress().equals(searchValue)){
                    return true;
                }
                else if (policyOwner.getPhoneNumber().equals(searchValue)){
                    return true;
                }

                else {
                    return false;
                }
            });
        });
    }

    public void fillingPolicyOwnerTable(EntityManager entityManager, User user, List<PolicyOwner> policyOwners, ObservableList<PolicyOwner> policyOwnersObservableList){
        ListIterator<PolicyOwner> policyOwnerListIterator = policyOwners.listIterator();
        while (policyOwnerListIterator.hasNext()){
            PolicyOwner policyOwner = policyOwnerListIterator.next();
            Button buttonUpdateInfo = new Button("Update Info");
            Button buttonAddPolicy = new Button("Add Policy");
            Button buttonRemove = new Button("Remove");

            if (user instanceof SystemAdmin){
                buttonUpdateInfo.setOnAction(event -> {
                    CreationPageController_PolicyOwner creationPageControllerPolicyOwner = new CreationPageController_PolicyOwner(entityManager, user, policyOwner);
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(MainEntryPoint.class.getResource("PolicyOwnerCreationPage.fxml"));
                    fxmlLoader.setController(creationPageControllerPolicyOwner);
                    try {
                        Scene scene = new Scene(fxmlLoader.load());
                        Stage stage = (Stage) buttonUpdateInfo.getScene().getWindow();
                        stage.setScene(scene);
                        stage.show();

                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });

                policyOwner.setUpdateInfoButton(buttonUpdateInfo);

                buttonAddPolicy.setOnAction(event -> {
                    CreationPageController_PolicyHolder creationPageControllerPolicyHolder = new CreationPageController_PolicyHolder(entityManager, user, policyOwner);
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(MainEntryPoint.class.getResource("PolicyHolderCreationPage.fxml"));
                    fxmlLoader.setController(creationPageControllerPolicyHolder);
                    try {
                        Scene scene = new Scene(fxmlLoader.load());
                        Stage stage = (Stage) buttonAddPolicy.getScene().getWindow();
                        stage.setScene(scene);
                        stage.show();

                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });

                policyOwner.setRemoveButton(buttonRemove);
                buttonRemove.setOnAction(event -> {
                    CustomerCreateRemove.removePolicyOwner(entityManager, policyOwner );
                });
                policyOwner.setAddPolicyButton(buttonAddPolicy);
            }

            policyOwnersObservableList.add(policyOwner);
        }

        FilteredList<PolicyOwner> filteredPolicyOwnerList = new FilteredList<>(policyOwnersObservableList, b -> true);
        filteringPolicyOwnerTable(filteredPolicyOwnerList);

        policyOwnerId.setCellValueFactory(new PropertyValueFactory<PolicyOwner, String>("id"));
        policyOwnerFullName.setCellValueFactory(new PropertyValueFactory<PolicyOwner, String>("fullName"));
        policyOwnerAddress.setCellValueFactory(new PropertyValueFactory<PolicyOwner, String>("address"));
        policyOwnerEmail.setCellValueFactory(new PropertyValueFactory<PolicyOwner, String>("email"));
        policyOwnerPassword.setCellValueFactory(new PropertyValueFactory<PolicyOwner, String>("password"));
        if (user instanceof SystemAdmin){
            policyOwnerUpdateInfoButton.setCellValueFactory(new PropertyValueFactory<PolicyOwner, Button>("updateInfoButton"));
            policyOwnerAddPolicyButton.setCellValueFactory(new PropertyValueFactory<PolicyOwner, Button>("addPolicyButton"));
            policyOwnerRemoveButton.setCellValueFactory(new PropertyValueFactory<PolicyOwner, Button>("removeButton"));
        }

        policyOwnerTable.getItems().setAll(filteredPolicyOwnerList);

    }





}
