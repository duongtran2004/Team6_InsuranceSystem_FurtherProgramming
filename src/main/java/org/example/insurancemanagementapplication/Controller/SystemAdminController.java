package org.example.insurancemanagementapplication.Controller;

import Entity.*;
import jakarta.persistence.EntityManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.insurancemanagementapplication.Interfaces.CustomerCreateRemove;
import org.example.insurancemanagementapplication.Interfaces.EmployeeAnalytics;
import org.example.insurancemanagementapplication.Interfaces.EmployeeCreateRemove;

import java.net.URL;
import java.util.List;
import java.util.ListIterator;
import java.util.ResourceBundle;

/**
 * @author Luong Thanh Trung
 * @version ${}
 * @created 27/04/2024 04:55
 * @project InsuranceManagementTeamProject
 */
public class SystemAdminController implements EmployeeCreateRemove, CustomerCreateRemove, Initializable, EmployeeAnalytics {
    private final EntityManager entityManager;
    private final SystemAdmin systemAdmin;
    private InsuranceSurveyor insuranceSurveyor;
    private InsuranceManager insuranceManager;
    private Customer customer;
    private Claim claim;

    @FXML
    private TextField userIdField;
    @FXML
    private TextField fullNameField;
    @FXML
    private TextField addressField;
    @FXML
    private TextField phoneNumberField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField passwordField;
    @FXML
    private TextField passwordValidationField;
    @FXML
    private Button updateInfo;
    @FXML
    private Button addSurveyor;
    @FXML
    private Button addManager;



    @FXML
    private TableView<InsuranceManager> managerTable;
    @FXML
    private TableColumn<InsuranceManager, String> managerId;
    @FXML
    private TableColumn<InsuranceManager, String> managerFullName;
    @FXML
    private TableColumn<InsuranceManager, TextField> managerAddress;
    @FXML
    private TableColumn<InsuranceManager, TextField> managerPhoneNumber;
    @FXML
    private TableColumn<InsuranceManager, TextField> managerEmail;
    @FXML
    private TableColumn<InsuranceManager, TextField> managerPassword;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<InsuranceManager> insuranceManagers = EmployeeAnalytics.getAllInsuranceManager(entityManager);
        ListIterator<InsuranceManager> listIterator = insuranceManagers.listIterator();
        while (listIterator.hasNext()){
            InsuranceManager insuranceManager = listIterator.next();
            insuranceManager.setPhoneNumberTextField(new TextField(insuranceManager.getPhoneNumber()));
            insuranceManager.setAddressTextField(new TextField(insuranceManager.getAddress()));
            insuranceManager.setEmailTextField(new TextField(insuranceManager.getEmail()));
            insuranceManager.setPasswordTextField(new TextField(insuranceManager.getPassword()));
        }
        managerId.setCellValueFactory(new PropertyValueFactory<InsuranceManager, String>("id"));
        managerFullName.setCellValueFactory(new PropertyValueFactory<InsuranceManager, String>("fullName"));
        managerEmail.setCellValueFactory(new PropertyValueFactory<InsuranceManager, TextField>("emailTextField"));
        managerAddress.setCellValueFactory(new PropertyValueFactory<InsuranceManager, TextField>("addressTextField"));
        managerPhoneNumber.setCellValueFactory(new PropertyValueFactory<InsuranceManager, TextField>("phoneNumberTextField"));
        managerPassword.setCellValueFactory(new PropertyValueFactory<InsuranceManager, TextField>("passwordTextField"));

        managerTable.getItems().setAll(insuranceManagers);
    }



    public SystemAdminController(EntityManager entityManager, SystemAdmin systemAdmin) {
        this.entityManager = entityManager;
        this.systemAdmin = systemAdmin;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public SystemAdmin getSystemAdmin() {
        return systemAdmin;
    }

    public InsuranceSurveyor getInsuranceSurveyor() {
        return insuranceSurveyor;
    }

    public void setInsuranceSurveyor(InsuranceSurveyor insuranceSurveyor) {
        this.insuranceSurveyor = insuranceSurveyor;
    }

    public InsuranceManager getInsuranceManager() {
        return insuranceManager;
    }

    public void setInsuranceManager(InsuranceManager insuranceManager) {
        this.insuranceManager = insuranceManager;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Claim getClaim() {
        return claim;
    }

    public void setClaim(Claim claim) {
        this.claim = claim;
    }


}
