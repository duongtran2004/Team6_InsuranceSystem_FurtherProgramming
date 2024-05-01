package org.example.insurancemanagementapplication.Controller.DashBoardController.TableFillingController;

import Entity.Customer;
import Entity.PolicyHolder;
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
import org.example.insurancemanagementapplication.Controller.CreationPageController.CreationPageController_Dependant;
import org.example.insurancemanagementapplication.Controller.CreationPageController.CreationPageController_PolicyHolder;
import org.example.insurancemanagementapplication.Interfaces.CustomerCreateRemove;
import org.example.insurancemanagementapplication.MainEntryPoint;

import java.io.IOException;
import java.util.List;
import java.util.ListIterator;

/**
 * @author Luong Thanh Trung
 * @version ${}
 * @created 01/05/2024 15:56
 * @project InsuranceManagementTeamProject
 */
public class PolicyHolderTableFilling extends InsuranceCardFillingTable{
    @FXML
    protected static TableView<PolicyHolder> policyHolderTable;
    @FXML
    protected static TableColumn<PolicyHolder, String> policyHolderId;
    @FXML
    protected static TableColumn<PolicyHolder, String> policyHolderFullName;
    @FXML
    protected static TableColumn<PolicyHolder, String> policyHolderAddress;
    @FXML
    protected static TableColumn<PolicyHolder, String> policyHolderPhoneNumber;
    @FXML
    protected static TableColumn<PolicyHolder, String> policyHolderEmail;
    @FXML
    protected static TableColumn<PolicyHolder, String> policyHolderPassword;
    @FXML
    protected static TableColumn<PolicyHolder, String> policyOwnerHolderTable;
    @FXML
    protected static TableColumn<PolicyHolder, String> cardNumberHolderTable;
    @FXML
    protected static TableColumn<PolicyHolder, Button> policyHolderUpdateInfoButton;
    @FXML
    protected static TableColumn<PolicyHolder, Button> policyHolderAddDependantButton;
    @FXML
    protected static TableColumn<PolicyHolder, Button> policyHolderAddClaimButton;
    @FXML
    protected static TableColumn<PolicyHolder, Button> policyHolderRemoveButton;
    @FXML
    protected static TextField policyHolderSearchField;

    protected static void fillingPolicyHolderTable(EntityManager entityManager, User user, List<PolicyHolder> policyHolders, ObservableList<PolicyHolder> policyHoldersObservableList){
        ListIterator<PolicyHolder> policyHolderListIterator = policyHolders.listIterator();
        while (policyHolderListIterator.hasNext()){
            PolicyHolder policyHolder = policyHolderListIterator.next();
            Button buttonUpdateInfo = new Button("Update Info");
            Button buttonAddDependant = new Button("Add Dependant");
            Button buttonRemove = new Button("Remove");
            Button buttonAddClaim = new Button("Add Claim");
            if (user instanceof SystemAdmin || user instanceof Customer){
                buttonUpdateInfo.setOnAction(event -> {
                    CreationPageController_PolicyHolder creationPageControllerPolicyHolder = new CreationPageController_PolicyHolder(entityManager, user, policyHolder);
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(MainEntryPoint.class.getResource("PolicyHolderCreationPage.fxml"));
                    fxmlLoader.setController(creationPageControllerPolicyHolder);
                    try {
                        Scene scene = new Scene(fxmlLoader.load());
                        Stage stage = (Stage) buttonUpdateInfo.getScene().getWindow();
                        stage.setScene(scene);
                        stage.show();

                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
                buttonAddDependant.setOnAction(event -> {
                    CreationPageController_Dependant creationPageControllerDependant = new CreationPageController_Dependant(entityManager, user, policyHolder);
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(MainEntryPoint.class.getResource("DependantCreationPage.fxml"));
                    fxmlLoader.setController(creationPageControllerDependant);
                    try {
                        Scene scene = new Scene(fxmlLoader.load());
                        Stage stage = (Stage) buttonAddDependant.getScene().getWindow();
                        stage.setScene(scene);
                        stage.show();

                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                });
                buttonRemove.setOnAction(event -> {
                    CustomerCreateRemove.removePolicyHolder(entityManager, policyHolder );
                });
                policyHolder.setRemoveButton(buttonRemove);
                policyHolder.setAddDependantButton(buttonAddDependant);
                policyHolder.setUpdateInfoButton(buttonUpdateInfo);

                if (user instanceof Customer){
                    buttonAddClaim.setOnAction(event -> {
                        policyHolder.setAddClaimButton(buttonAddClaim);
                    });
                }
            }

            policyHoldersObservableList.add(policyHolder);
        }

        FilteredList<PolicyHolder> filteredPolicyHolderList = new FilteredList<>(policyHoldersObservableList, b -> true);
        policyHolderSearchField.textProperty().addListener((observable, oldValue, newValue)->{
            filteredPolicyHolderList.setPredicate(policyHolder -> {
                if (newValue.isEmpty() || newValue == null || newValue.isBlank()){
                    return true;
                }
                String searchValue = newValue.toLowerCase();
                if (policyHolder.getId().equals(searchValue)){
                    return true;
                }
                else if (policyHolder.getFullName().equals(searchValue)){
                    return true;
                }
                else if (policyHolder.getEmail().equals(searchValue)){
                    return true;
                }
                else if (policyHolder.getAddress().equals(searchValue)){
                    return true;
                }
                else if (policyHolder.getPhoneNumber().equals(searchValue)){
                    return true;
                }
                else if (policyHolder.getPolicyOwnerId().equals(searchValue)){
                    return true;
                }
                else if( policyHolder.getPolicyOwner().getFullName().equals(searchValue)) {
                    return true;
                }

                else {
                    return false;
                }
            });
        });

        policyHolderId.setCellValueFactory(new PropertyValueFactory<PolicyHolder, String>("id"));
        policyHolderFullName.setCellValueFactory(new PropertyValueFactory<PolicyHolder, String>("fullName"));
        policyHolderAddress.setCellValueFactory(new PropertyValueFactory<PolicyHolder, String>("address"));
        policyHolderEmail.setCellValueFactory(new PropertyValueFactory<PolicyHolder, String>("email"));
        policyOwnerHolderTable.setCellValueFactory(new PropertyValueFactory<PolicyHolder, String>("policyOwnerId"));
        cardNumberHolderTable.setCellValueFactory(new PropertyValueFactory<PolicyHolder, String>("cardNumber"));
        policyHolderPassword.setCellValueFactory(new PropertyValueFactory<PolicyHolder, String>("password"));
        if (user instanceof SystemAdmin || user instanceof Customer){
            policyHolderUpdateInfoButton.setCellValueFactory(new PropertyValueFactory<PolicyHolder, Button>("updateInfoButton"));
            policyHolderAddDependantButton.setCellValueFactory(new PropertyValueFactory<PolicyHolder, Button>("addDependantButton"));
            policyHolderRemoveButton.setCellValueFactory(new PropertyValueFactory<PolicyHolder, Button>("removeButton"));
            if (user instanceof Customer){
                policyHolderAddClaimButton.setCellValueFactory(new PropertyValueFactory<PolicyHolder, Button>("addClaimButton"));
            }
        }

        policyHolderTable.getItems().setAll(filteredPolicyHolderList);
    }
}
