package org.example.insurancemanagementapplication.Controller.DashBoardController.TableFillingController;

import Entity.InsuranceCard;
import Entity.PolicyOwner;
import Entity.SystemAdmin;
import Entity.User;
import jakarta.persistence.EntityManager;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.insurancemanagementapplication.Interfaces.CustomerCreateRemove;

import java.sql.Date;
import java.util.List;
import java.util.ListIterator;

/**
 * @author Luong Thanh Trung
 * @version ${}
 * @created 01/05/2024 16:20
 * @project InsuranceManagementTeamProject
 */
public class InsuranceCardFillingTable extends DependantTableFilling {
    @FXML
    private TableView<InsuranceCard> insuranceCardTable;
    @FXML
    private TableColumn<InsuranceCard, String> cardNumber;
    @FXML
    private TableColumn<InsuranceCard, Date> expiryDate;
    @FXML
    private TableColumn<InsuranceCard, String> cardHolderId;
    @FXML
    private TableColumn<InsuranceCard, String> policyOwnerInsuranceCardTable;
    @FXML
    private TableColumn<InsuranceCard, Button> insuranceCardRemoveButton;
    @FXML
    private TextField insuranceCardSearchField;

    public void filteringInsuranceCardTable(FilteredList<InsuranceCard> filteredInsuranceCardList){
        insuranceCardSearchField.textProperty().addListener((observable, oldValue, newValue) ->{
            filteredInsuranceCardList.setPredicate(insuranceCard -> {
                if (newValue.isBlank() || newValue.isEmpty() || newValue == null){
                    return true;
                }
                String searchValue = newValue.toLowerCase();
                if (insuranceCard.getCardNumber().equals(searchValue)){
                    return true;
                }
                else if (insuranceCard.getCardHolderId().equals(searchValue)){
                    return true;
                }
                else if (insuranceCard.getPolicyOwnerId().equals(searchValue)){
                    return true;
                }
                else if (insuranceCard.getCardHolder().getFullName().equals(searchValue)){
                    return true;
                }
                else if (insuranceCard.getPolicyOwner().getFullName().equals(searchValue)){
                    return true;
                }
                else {
                    return false;
                }
            });
        });
    }
    public void fillingInsuranceCardTable(EntityManager entityManager, User user, List<InsuranceCard> insuranceCards, ObservableList<InsuranceCard> insuranceCardObservableList){

        ListIterator<InsuranceCard> insuranceCardListIterator = insuranceCards.listIterator();
        while (insuranceCardListIterator.hasNext()){

            InsuranceCard insuranceCard = insuranceCardListIterator.next();
            Button buttonRemove = new Button("Remove");
            if (user instanceof SystemAdmin || user instanceof PolicyOwner){
                buttonRemove.setOnAction(event -> {
                    CustomerCreateRemove.removeInsuranceCard(entityManager, insuranceCard);
                });
                insuranceCard.setRemoveButton(buttonRemove);
            }
            insuranceCardObservableList.add(insuranceCard);
        }

        FilteredList<InsuranceCard> filteredInsuranceCardList = new FilteredList<>(insuranceCardObservableList, b -> true);
        filteringInsuranceCardTable(filteredInsuranceCardList);



        cardNumber.setCellValueFactory(new PropertyValueFactory<InsuranceCard, String>("cardNumber"));
        cardHolderId.setCellValueFactory(new PropertyValueFactory<InsuranceCard, String>("cardHolder"));
        policyOwnerInsuranceCardTable.setCellValueFactory(new PropertyValueFactory<InsuranceCard, String>("policyOwner"));
        expiryDate.setCellValueFactory(new PropertyValueFactory<InsuranceCard, Date>("expirationDate"));
        if (user instanceof SystemAdmin || user instanceof PolicyOwner){
            insuranceCardRemoveButton.setCellValueFactory(new PropertyValueFactory<InsuranceCard, Button>("removeButton"));
        }
        insuranceCardTable.getItems().addAll(filteredInsuranceCardList);

    }

    public InsuranceCardFillingTable() {
    }

    public TableView<InsuranceCard> getInsuranceCardTable() {
        return insuranceCardTable;
    }

    public void setInsuranceCardTable(TableView<InsuranceCard> insuranceCardTable) {
        this.insuranceCardTable = insuranceCardTable;
    }

    public TableColumn<InsuranceCard, String> getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(TableColumn<InsuranceCard, String> cardNumber) {
        this.cardNumber = cardNumber;
    }

    public TableColumn<InsuranceCard, Date> getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(TableColumn<InsuranceCard, Date> expiryDate) {
        this.expiryDate = expiryDate;
    }

    public TableColumn<InsuranceCard, String> getCardHolderId() {
        return cardHolderId;
    }

    public void setCardHolderId(TableColumn<InsuranceCard, String> cardHolderId) {
        this.cardHolderId = cardHolderId;
    }

    public TableColumn<InsuranceCard, String> getPolicyOwnerInsuranceCardTable() {
        return policyOwnerInsuranceCardTable;
    }

    public void setPolicyOwnerInsuranceCardTable(TableColumn<InsuranceCard, String> policyOwnerInsuranceCardTable) {
        this.policyOwnerInsuranceCardTable = policyOwnerInsuranceCardTable;
    }

    public TableColumn<InsuranceCard, Button> getInsuranceCardRemoveButton() {
        return insuranceCardRemoveButton;
    }

    public void setInsuranceCardRemoveButton(TableColumn<InsuranceCard, Button> insuranceCardRemoveButton) {
        this.insuranceCardRemoveButton = insuranceCardRemoveButton;
    }

    public TextField getInsuranceCardSearchField() {
        return insuranceCardSearchField;
    }

    public void setInsuranceCardSearchField(TextField insuranceCardSearchField) {
        this.insuranceCardSearchField = insuranceCardSearchField;
    }
}
