package org.example.insurancemanagementapplication.Controller.DashBoardController.TableFillingController;

import Entity.*;
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
import org.example.insurancemanagementapplication.Interfaces.InsuranceCreateAndRemove;

import java.sql.Date;
import java.util.List;
import java.util.ListIterator;

/**
 * @author Luong Thanh Trung
 * @version ${}
 * @created 01/05/2024 16:20
 * @project InsuranceManagementTeamProject
 */
public class InsuranceCardTableFilling extends DependantTableFilling {

    //TODO Create a thread that runs in a selected interval that get all Insurance Cards from the databse  and check if new entries exist. If they do, append the new entries to the Observable List
    private ObservableList<InsuranceCard> insuranceCardsObservableList = FXCollections.observableArrayList();
    @FXML
    protected TableView<InsuranceCard> insuranceCardTable;
    @FXML
    protected TableColumn<InsuranceCard, String> cardNumber;
    @FXML
    protected TableColumn<InsuranceCard, Date> expiryDate;
    @FXML
    protected TableColumn<InsuranceCard, String> cardHolderId;
    @FXML
    protected TableColumn<InsuranceCard, String> policyOwnerIDOfInsuranceCard;


    @FXML
    protected TableColumn<InsuranceCard, Button> insuranceCardRemoveButton;
    @FXML
    protected TextField insuranceCardSearchField;

    public InsuranceCardTableFilling(EntityManager entityManager, User user) {
        super(entityManager, user);
    }

    /**
     * Attach an event listener to the insurance card search field that filter the insurance card table according to changes in this field
     *
     * @param filteredInsuranceCardList
     */
    public void filteringInsuranceCardTable(FilteredList<InsuranceCard> filteredInsuranceCardList) {
        insuranceCardSearchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredInsuranceCardList.setPredicate(insuranceCard -> {
                if (newValue.isBlank() || newValue.isEmpty() || newValue == null) {
                    return true;
                }
                String searchValue = newValue.toLowerCase();
                if (insuranceCard.getCardNumber().toLowerCase().contains(searchValue)) {
                    return true;
                } else if (insuranceCard.getCardHolderId().toLowerCase().contains(searchValue)) {
                    return true;
                } else if (insuranceCard.getPolicyOwnerId().toLowerCase().contains(searchValue)) {
                    return true;
                } else if (insuranceCard.getCardHolder().getFullName().toLowerCase().contains(searchValue)) {
                    return true;
                } else if (insuranceCard.getPolicyOwner().getFullName().toLowerCase().contains(searchValue)) {
                    return true;
                } else {
                    return false;
                }
            });
        });
    }

    /**
     * Mapping the columns of the insurance card tables with Insurance Card entity. Fill up the Insurance Card tables with data from the database
     *
     * @param entityManager
     * @param user
     * @param insuranceCards
     */
    public void fillingInsuranceCardTable(EntityManager entityManager, User user, List<InsuranceCard> insuranceCards) {

        ListIterator<InsuranceCard> insuranceCardListIterator = insuranceCards.listIterator();
        //Adding Insurance Cards to the insurance card observable list
        while (insuranceCardListIterator.hasNext()) {

            InsuranceCard insuranceCard = insuranceCardListIterator.next();
            //reassign from database object
//            insuranceCard = entityManager.find(InsuranceCard.class, insuranceCard.getCardNumber()); //unit test for this


            //Only system admin and policy owner has access to the remove button
            if (user instanceof SystemAdmin || user instanceof PolicyOwner) {
                Button buttonRemove = new Button("Remove");
                buttonList.add(buttonRemove);
                InsuranceCard finalInsuranceCard = insuranceCard;
                buttonRemove.setOnAction(event -> {
                   InsuranceCreateAndRemove.removeInsuranceCard(entityManager, finalInsuranceCard);

                });
                insuranceCard.setRemoveButton(buttonRemove);
            }

            insuranceCardsObservableList.add(insuranceCard);
        }
        cardNumber.setCellValueFactory(new PropertyValueFactory<InsuranceCard, String>("cardNumber"));
        cardHolderId.setCellValueFactory(new PropertyValueFactory<InsuranceCard, String>("cardHolderId"));
        policyOwnerIDOfInsuranceCard.setCellValueFactory(new PropertyValueFactory<InsuranceCard, String>("policyOwnerId"));
        expiryDate.setCellValueFactory(new PropertyValueFactory<InsuranceCard, Date>("expirationDate"));
        if (user instanceof SystemAdmin || user instanceof PolicyOwner) {
            insuranceCardRemoveButton.setCellValueFactory(new PropertyValueFactory<InsuranceCard, Button>("removeButton"));
        }
        FilteredList<InsuranceCard> filteredInsuranceCardList = new FilteredList<>(insuranceCardsObservableList, b -> true);
        filteringInsuranceCardTable(filteredInsuranceCardList);
        insuranceCardTable.setItems(filteredInsuranceCardList);

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

    public TableColumn<InsuranceCard, String> getPolicyOwnerIDOfInsuranceCard() {
        return policyOwnerIDOfInsuranceCard;
    }

    public void setPolicyOwnerIDOfInsuranceCard(TableColumn<InsuranceCard, String> policyOwnerIDOfInsuranceCard) {
        this.policyOwnerIDOfInsuranceCard = policyOwnerIDOfInsuranceCard;
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

//Inner Class for thread

