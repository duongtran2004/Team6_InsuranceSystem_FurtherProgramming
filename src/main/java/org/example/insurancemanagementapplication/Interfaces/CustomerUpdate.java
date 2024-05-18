package org.example.insurancemanagementapplication.Interfaces;

import Entity.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import javafx.scene.Node;
import javafx.stage.Stage;
import org.example.insurancemanagementapplication.Controller.Page404Controller;
import org.example.insurancemanagementapplication.Utility.StageBuilder;

/**
 * @author Luong Thanh Trung
 * @version ${}
 * @created 27/04/2024 04:57
 * @project InsuranceManagementTeamProject
 */
public interface CustomerUpdate {
    static boolean updatePolicyOwner(Node node, User user, EntityManager entityManager, PolicyOwner policyOwner, String address, String phoneNumber, String email, String password){
        EntityTransaction transaction = entityManager.getTransaction();
        try{
            transaction.begin();
            entityManager.persist(policyOwner);
            policyOwner.setAddress(address);
            policyOwner.setPhoneNumber(phoneNumber);
            policyOwner.setEmail(email);
            policyOwner.setPassword(password);
            transaction.commit();


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

    static boolean updatePolicyHolder(Node node, User user, EntityManager entityManager, PolicyHolder policyHolder, String address, String phoneNumber, String email, String password){
        EntityTransaction transaction = entityManager.getTransaction();
        try{
            transaction.begin();
            entityManager.persist(policyHolder);
            policyHolder.setAddress(address);
            policyHolder.setPhoneNumber(phoneNumber);
            policyHolder.setEmail(email);
            policyHolder.setPassword(password);
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

    static boolean updateDependant(Node node, User user, EntityManager entityManager, Dependant dependant, String address, String phoneNumber, String email, String password){
        EntityTransaction transaction = entityManager.getTransaction();
        try{
            transaction.begin();
            entityManager.persist(dependant);
            dependant.setAddress(address);
            dependant.setPhoneNumber(phoneNumber);
            dependant.setEmail(email);
            dependant.setPassword(password);
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

    static boolean updatePolicyHolder(Node node, User user, EntityManager entityManager, PolicyHolder policyHolder, InsuranceCard newCard){
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(policyHolder);
            policyHolder.setInsuranceCard(newCard);
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

    static boolean updateDependant(Node node, User user, EntityManager entityManager, Dependant dependant, InsuranceCard newCard){
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(dependant);
            dependant.setInsuranceCard(newCard);
            transaction.commit();
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
}
