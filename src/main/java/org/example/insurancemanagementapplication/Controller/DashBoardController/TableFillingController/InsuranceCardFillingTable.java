package org.example.insurancemanagementapplication.Controller.DashBoardController.TableFillingController;

import Entity.InsuranceCard;
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
    protected static TableView<InsuranceCard> insuranceCardTable;
    @FXML
    protected static TableColumn<InsuranceCard, String> cardNumber;
    @FXML
    protected static TableColumn<InsuranceCard, Date> expiryDate;
    @FXML
    protected static TableColumn<InsuranceCard, String> cardHolderId;
    @FXML
    protected static TableColumn<InsuranceCard, String> policyOwnerInsuranceCardTable;
    @FXML
    protected static TableColumn<InsuranceCard, Button> insuranceCardRemoveButton;
    @FXML
    protected static TextField insuranceCardSearchField;

    protected static void fillingInsuranceCardTable(EntityManager entityManager, User user, List<InsuranceCard> insuranceCards){

        ObservableList<InsuranceCard> insuranceCardObservableList = FXCollections.observableArrayList();
        ListIterator<InsuranceCard> insuranceCardListIterator = insuranceCards.listIterator();
        while (insuranceCardListIterator.hasNext()){
            InsuranceCard insuranceCard = new InsuranceCard();
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


        cardNumber.setCellValueFactory(new PropertyValueFactory<InsuranceCard, String>("cardNumber"));
        cardHolderId.setCellValueFactory(new PropertyValueFactory<InsuranceCard, String>("cardHolder"));
        policyOwnerInsuranceCardTable.setCellValueFactory(new PropertyValueFactory<InsuranceCard, String>("policyOwner"));
        expiryDate.setCellValueFactory(new PropertyValueFactory<InsuranceCard, Date>("expirationDate"));
        if (user instanceof SystemAdmin || user instanceof PolicyOwner){
            insuranceCardRemoveButton.setCellValueFactory(new PropertyValueFactory<InsuranceCard, Button>("removeButton"));
        }

        insuranceCardTable.getItems().addAll(filteredInsuranceCardList);

    }
}
