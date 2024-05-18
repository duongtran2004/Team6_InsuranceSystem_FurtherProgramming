package org.example.insurancemanagementapplication.Interfaces;

import Entity.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.example.insurancemanagementapplication.Controller.Page404Controller;
import org.example.insurancemanagementapplication.Utility.StageBuilder;

import java.sql.Date;

/**
 * @author Luong Thanh Trung
 * @version ${}
 * @created 27/04/2024 04:59
 * @project InsuranceManagementTeamProject
 */
public interface ClaimCreateRemove {

    public static boolean createClaim(Button button, EntityManager entityManager, User user, String claimId, Date creationDate, Beneficiaries beneficiaries, PolicyOwner policyOwner, InsuranceCard insuranceCard, InsuranceManager insuranceManager, String bankName, String accountName, String accountNumber, byte[] documentFile){
        EntityTransaction transaction = entityManager.getTransaction();
        try{
            transaction.begin();
            Claim claim = new Claim();
            claim.setClaimId(claimId);
            claim.setCreationDate(creationDate);
            claim.setInsuranceManager(insuranceManager);
            claim.setStatus("NEW");
            claim.setInsuredPerson(beneficiaries);
            claim.setPolicyOwner(policyOwner);
            claim.setInsuranceCard(insuranceCard);
            claim.setBankName(bankName);
            claim.setBankAccountName(accountName);
            claim.setBankAccountNumber(accountNumber);
            claim.setDocumentFile(documentFile);
            entityManager.persist(claim);
            transaction.commit();
        } catch (Exception e){
            Page404Controller page404Controller = new Page404Controller(user, entityManager);
            StageBuilder.showStage((Stage) button.getScene().getWindow(), (Controller) page404Controller, "Page404.fxml", "An Error has occurred");
        }
        finally {
            if (transaction.isActive()){
                transaction.rollback();
            }
        }
        return true;
    }


    public static boolean removeClaim(Button button, User user, EntityManager entityManager, Claim claim){
        EntityTransaction transaction = entityManager.getTransaction();
        try{
            transaction.begin();
            entityManager.remove(claim);
            transaction.commit();
        } catch (Exception e){
            Page404Controller page404Controller = new Page404Controller(user, entityManager);
            StageBuilder.showStage((Stage) button.getScene().getWindow(), (Controller) page404Controller, "Page404.fxml", "An Error has occurred");
        }
        finally {
            if (transaction.isActive()){
                transaction.rollback();
            }
        }
        return true;
    }
}
