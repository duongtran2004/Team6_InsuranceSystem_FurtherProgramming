package org.example.insurancemanagementapplication.Interfaces;

import Entity.InsuranceManager;
import Entity.InsuranceSurveyor;
import Entity.SystemAdmin;
import Entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import javafx.scene.Node;
import javafx.stage.Stage;
import org.example.insurancemanagementapplication.Controller.Page404Controller;
import org.example.insurancemanagementapplication.Utility.StageBuilder;

/**
 * @author Luong Thanh Trung
 * @version ${}
 * @created 27/04/2024 04:58
 * @project InsuranceManagementTeamProject
 */
public interface EmployeeUpdate {

    static boolean updateSystemAdmin(Node node, User user, EntityManager entityManager, SystemAdmin systemAdmin, String address, String phoneNumber, String email, String password){
        EntityTransaction transaction = entityManager.getTransaction();
        try{
            transaction.begin();
            entityManager.persist(systemAdmin);
            systemAdmin.setAddress(address);
            systemAdmin.setPhoneNumber(phoneNumber);
            systemAdmin.setEmail(email);
            systemAdmin.setPassword(password);
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
    static boolean updateInsuranceManager(Node node, User user, EntityManager entityManager, InsuranceManager insuranceManager, String address, String phoneNumber, String email, String password){
        EntityTransaction transaction = entityManager.getTransaction();
        try{
            transaction.begin();
            entityManager.persist(insuranceManager);
            insuranceManager.setAddress(address);
            insuranceManager.setPhoneNumber(phoneNumber);
            insuranceManager.setEmail(email);
            insuranceManager.setPassword(password);
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

    static boolean updateInsuranceSurveyor(Node node, User user, EntityManager entityManager, InsuranceSurveyor insuranceSurveyor, String address, String phoneNumber, String email, String password, InsuranceManager insuranceManager){
        EntityTransaction transaction = entityManager.getTransaction();
        try{
            transaction.begin();
            entityManager.persist(insuranceSurveyor);
            insuranceSurveyor.setAddress(address);
            insuranceSurveyor.setPhoneNumber(phoneNumber);
            insuranceSurveyor.setEmail(email);
            insuranceSurveyor.setPassword(password);
            insuranceSurveyor.setInsuranceManager(insuranceManager);
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

    static boolean updateInsuranceSurveyor(Node node, User user, EntityManager entityManager, InsuranceSurveyor insuranceSurveyor, String address, String phoneNumber, String email, String password){
        EntityTransaction transaction = entityManager.getTransaction();
        try{
            transaction.begin();
            entityManager.persist(insuranceSurveyor);
            insuranceSurveyor.setAddress(address);
            insuranceSurveyor.setPhoneNumber(phoneNumber);
            insuranceSurveyor.setEmail(email);
            insuranceSurveyor.setPassword(password);
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
