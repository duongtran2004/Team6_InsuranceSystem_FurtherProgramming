package org.example.insurancemanagementapplication.Controller.DashBoardController.TableFillingController;

import Entity.PolicyOwner;
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
import org.example.insurancemanagementapplication.Controller.CreationPageController.CreationPageControllerPolicyHolder;
import org.example.insurancemanagementapplication.Controller.CreationPageController.CreationPageControllerPolicyOwner;
import org.example.insurancemanagementapplication.Interfaces.CustomerCreateRemove;
import org.example.insurancemanagementapplication.Interfaces.CustomerRead;
import org.example.insurancemanagementapplication.Utility.StageBuilder;

import java.util.List;
import java.util.ListIterator;

/**
 * @author Luong Thanh Trung
 * @version ${}
 * @created 01/05/2024 16:11
 * @project InsuranceManagementTeamProject
 */
public class PolicyOwnerTableFilling extends PolicyHolderTableFilling {
    //TODO Create a thread that get all Policy Owners from the table  and check if new entries exist. If they do, append the new entries to the Observable List
    private ObservableList<PolicyOwner> policyOwnersObservableList = FXCollections.observableArrayList();
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
    /**
     * Attach an event listener to the policy owner search field that filter the policy owner table according to changes in this field
     * @param filteredPolicyOwnerList
     */
    public void filteringPolicyOwnerTable(FilteredList<PolicyOwner> filteredPolicyOwnerList){
        policyOwnerSearchField.textProperty().addListener((observable, oldValue, newValue)->{
            filteredPolicyOwnerList.setPredicate(policyOwner -> {
                if (newValue.isEmpty() || newValue == null || newValue.isBlank()){
                    return true;
                }
                String searchValue = newValue.toLowerCase();
                if (policyOwner.getId().toLowerCase().contains(searchValue)){
                    return true;
                }
                else if (policyOwner.getFullName().toLowerCase().contains(searchValue)){
                    return true;
                }
                else if (policyOwner.getEmail().toLowerCase().contains(searchValue)){
                    return true;
                }
                else if (policyOwner.getAddress().toLowerCase().contains(searchValue)){
                    return true;
                }
                else if (policyOwner.getPhoneNumber().toLowerCase().contains(searchValue)){
                    return true;
                }

                else {
                    return false;
                }
            });
        });
    }

    /**
     * Mapping the columns of the policy owner tables with Policy Owner entity. Fill up the policy owner tables with data from the database
     * @param entityManager
     * @param user
     * @param policyOwners
     */
    public void fillingPolicyOwnerTable(EntityManager entityManager, User user, List<PolicyOwner> policyOwners){
        ListIterator<PolicyOwner> policyOwnerListIterator = policyOwners.listIterator();
        //Add policy owners to the observable list
        while (policyOwnerListIterator.hasNext()){
            PolicyOwner policyOwner = policyOwnerListIterator.next();
            Button buttonUpdateInfo = new Button("Update Info");
            Button buttonAddPolicy = new Button("Add Policy");
            Button buttonRemove = new Button("Remove");
            //Only a system admin has access to the update info, add policy, and remove button
            if (user instanceof SystemAdmin){
                //The Update Info Button will create a CreationPage Controller for the policy owner in update mode by passing in the policy owner object
                //It will then open the Policy Owner Creation Page
                buttonUpdateInfo.setOnAction(event -> {
                    CreationPageControllerPolicyOwner creationPageControllerPolicyOwner = new CreationPageControllerPolicyOwner(entityManager, user, policyOwner);
                    StageBuilder.showStage((Stage) buttonUpdateInfo.getScene().getWindow(), creationPageControllerPolicyOwner, "PolicyOwnerCreationPage.fxml", "Policy Owner Update");

                });

                policyOwner.setUpdateInfoButton(buttonUpdateInfo);
                //The addPolicyHolder button will create a Policy Holder CreationPage Controller in creation mode by passing the policy owner object
                //It will then open the Policy Holder Creation Form
                buttonAddPolicy.setOnAction(event -> {
                    CreationPageControllerPolicyHolder creationPageControllerPolicyHolder = new CreationPageControllerPolicyHolder(entityManager, user, policyOwner);
                    StageBuilder.showStage((Stage) buttonAddPolicy.getScene().getWindow(), creationPageControllerPolicyHolder, "PolicyHolderCreationPage.fxml", "Policy Creation");

                });

                //The remove button will remove its policy owner button from the database
                policyOwner.setRemoveButton(buttonRemove);
                buttonRemove.setOnAction(event -> {
                    CustomerCreateRemove.removePolicyOwner(entityManager, policyOwner );
                });
                policyOwner.setAddPolicyButton(buttonAddPolicy);
            }

            policyOwnersObservableList.add(policyOwner);
        }



        policyOwnerId.setCellValueFactory(new PropertyValueFactory<PolicyOwner, String>("id"));
        policyOwnerFullName.setCellValueFactory(new PropertyValueFactory<PolicyOwner, String>("fullName"));
        policyOwnerAddress.setCellValueFactory(new PropertyValueFactory<PolicyOwner, String>("address"));
        policyOwnerEmail.setCellValueFactory(new PropertyValueFactory<PolicyOwner, String>("email"));
        policyOwnerPassword.setCellValueFactory(new PropertyValueFactory<PolicyOwner, String>("password"));
        policyOwnerPhoneNumber.setCellValueFactory(new PropertyValueFactory<PolicyOwner, String>("phoneNumber"));
        if (user instanceof SystemAdmin){
            policyOwnerUpdateInfoButton.setCellValueFactory(new PropertyValueFactory<PolicyOwner, Button>("updateInfoButton"));
            policyOwnerAddPolicyButton.setCellValueFactory(new PropertyValueFactory<PolicyOwner, Button>("addPolicyButton"));
            policyOwnerRemoveButton.setCellValueFactory(new PropertyValueFactory<PolicyOwner, Button>("removeButton"));
        }
        FilteredList<PolicyOwner> filteredPolicyOwnerList = new FilteredList<>(policyOwnersObservableList, b -> true);
        filteringPolicyOwnerTable(filteredPolicyOwnerList);
        policyOwnerTable.setItems(filteredPolicyOwnerList);

    }

}
//Inner Class for thread

 class PolicyOwnerTableFillingThread extends Thread {
    private EntityManager entityManager;
    private User user;

    public PolicyOwnerTableFillingThread(EntityManager entityManager, User user) {
        this.entityManager = entityManager;
        this.user = user;
    }

    public static void policyOwnerTableFillingForInsuranceSurveyor(EntityManager entityManager, User user) {
        PolicyOwnerTableFilling policyOwnerTableFilling = new PolicyOwnerTableFilling(entityManager, user);

        policyOwnerTableFilling.fillingPolicyOwnerTable(entityManager, user, CustomerRead.getAllPolicyOwnersTakeChargeByAnEmployee(entityManager, user.getId(), "InsuranceSurveyor"));
    }

     public static void policyOwnerTableFillingForInsuranceManager(EntityManager entityManager, User user) {
         PolicyOwnerTableFilling policyOwnerTableFilling = new PolicyOwnerTableFilling(entityManager, user);

         policyOwnerTableFilling.fillingPolicyOwnerTable(entityManager, user, CustomerRead.getAllPolicyOwnersTakeChargeByAnEmployee(entityManager, user.getId(), "InsuranceManager"));
     }

    //For System Admin
    public static void policyOwnerTableFillingForSystemAdmin(EntityManager entityManager, User user) {
        PolicyOwnerTableFilling policyOwnerTableFilling = new PolicyOwnerTableFilling(entityManager, user);

        policyOwnerTableFilling.fillingPolicyOwnerTable(entityManager, user, CustomerRead.getAllPolicyOwner(entityManager));
    }

    @Override
    public void run() {
        PolicyOwnerTableFilling policyOwnerTableFilling = new PolicyOwnerTableFilling(entityManager, user);

        policyOwnerTableFilling.fillingPolicyOwnerTable(entityManager, user, CustomerRead.getAllPolicyOwner(entityManager));
    }
}
