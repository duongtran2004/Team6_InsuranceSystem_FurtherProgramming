package org.example.insurancemanagementapplication.Interfaces;

import Entity.InsuranceCard;
import Entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import javafx.scene.Node;
import javafx.stage.Stage;
import org.example.insurancemanagementapplication.Controller.Page404Controller;
import org.example.insurancemanagementapplication.Utility.StageBuilder;

public interface InsuranceCreateAndRemove {

    public static boolean createInsuranceCard(Node node, User user, EntityManager entityManager, InsuranceCard insuranceCard){
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(insuranceCard);
            transaction.commit();
        }catch (Exception e){
            Page404Controller page404Controller = new Page404Controller(user, entityManager);
            StageBuilder.showStage((Stage) node.getScene().getWindow(), page404Controller, "Page404.fxml", "An Error has occurred");
            return false;
        }
        finally {
            if (transaction.isActive()){
                transaction.rollback();
            }
        }
        return true;
    }

    public static boolean removeInsuranceCard(Node node, User user, EntityManager entityManager, InsuranceCard insuranceCard){
        EntityTransaction transaction = entityManager.getTransaction();
        try{
            transaction.begin();
            entityManager.remove(insuranceCard);
            transaction.commit();
            // Update the insurance card field of all its beneficiaries
            //updateBeneficiariesInsuranceCardField(node, user, entityManager, insuranceCard);
        } catch (Exception e){
            Page404Controller page404Controller = new Page404Controller(user, entityManager);
            StageBuilder.showStage((Stage) node.getScene().getWindow(), page404Controller, "Page404.fxml", "An Error has occurred");
            return false;
        }
        finally {
            if (transaction.isActive()){
                transaction.rollback();
            }
        }
        return true;
    }
    /**
    private static void updateBeneficiariesInsuranceCardField(Node node, User user, EntityManager entityManager, InsuranceCard insuranceCard) {
        try{
            // Retrieve all beneficiaries associated with the insurance card
            List<Beneficiaries> beneficiaries = entityManager.createQuery("SELECT b FROM Beneficiaries b WHERE b.insuranceCard = :insuranceCard", Beneficiaries.class)
                    .setParameter("insuranceCard", insuranceCard)
                    .getResultList();

            // Update the insurance card field of each beneficiary
            entityManager.getTransaction().begin();
            for (Beneficiaries beneficiary : beneficiaries) {
                beneficiary.setInsuranceCard(null); // Set insurance card field to null
                entityManager.merge(beneficiary); // Update the beneficiary in the database
            }
            entityManager.getTransaction().commit();
        } catch (Exception e){
            Page404Controller page404Controller = new Page404Controller(user, entityManager);
            StageBuilder.showStage((Stage) node.getScene().getWindow(), page404Controller, "Page404.fxml", "An Error has occurred");
            return false;
        }

    }
     **/
}
