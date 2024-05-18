package org.example.insurancemanagementapplication.Interfaces;

import Entity.InsuranceManager;
import Entity.InsuranceSurveyor;
import Entity.SystemAdmin;
import Entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import javafx.scene.Node;
import javafx.stage.Stage;
import org.example.insurancemanagementapplication.Controller.LogInPageController;
import org.example.insurancemanagementapplication.Controller.Page404Controller;
import org.example.insurancemanagementapplication.Utility.StageBuilder;

import java.util.List;

/**
 * @author Luong Thanh Trung
 * @version ${}
 * @created 27/04/2024 04:57
 * @project InsuranceManagementTeamProject
 */
public interface EmployeeRead {
    public static List<InsuranceManager> getAllInsuranceManager(Node node, User user, EntityManager entityManager) {
        try{
            return entityManager.createQuery(
                    "SELECT e FROM InsuranceManager e").getResultList();
        }catch (Exception e){
            Page404Controller page404Controller = new Page404Controller(user, entityManager);
            StageBuilder.showStage((Stage) node.getScene().getWindow(), page404Controller, "Page404.fxml", "An Error has occurred");
            return null;
        }


    }
    public static InsuranceManager findRandomInsuranceManager(Node node, User user, EntityManager entityManager){
        try {
            String query = "SELECT c FROM InsuranceManager c ORDER BY random() LIMIT 1";
            return  (InsuranceManager) entityManager.createQuery(query).getSingleResult();
        } catch (Exception e){
            Page404Controller page404Controller = new Page404Controller(user, entityManager);
            StageBuilder.showStage((Stage) node.getScene().getWindow(), page404Controller, "Page404.fxml", "An Error has occurred");
            return null;
        }


    }

    public static List<InsuranceSurveyor> getAllInsuranceSurveyor(Node node, User user, EntityManager entityManager) {
        try{
            return entityManager.createQuery(
                    "SELECT e FROM InsuranceSurveyor e").getResultList();
        } catch (Exception e){
            Page404Controller page404Controller = new Page404Controller(user, entityManager);
            StageBuilder.showStage((Stage) node.getScene().getWindow(), page404Controller, "Page404.fxml", "An Error has occurred");
            return null;
        }
    }


    public static InsuranceManager findInsuranceManagerById(Node node, User user, EntityManager entityManager, String id) {
        try {
            InsuranceManager insuranceManager = entityManager.find(InsuranceManager.class, id);
            return insuranceManager;
        } catch (Exception e){
            Page404Controller page404Controller = new Page404Controller(user, entityManager);
            StageBuilder.showStage((Stage) node.getScene().getWindow(), page404Controller, "Page404.fxml", "An Error has occurred");
            return null;
        }

    }
    //FOR LOGIN

    public static SystemAdmin getSystemAdminWithCredential(Node node, EntityManager entityManager, String id, String email, String password) {
        try {
            SystemAdmin employee = (SystemAdmin) entityManager.createQuery("SELECT c FROM SystemAdmin c WHERE c.id LIKE ?1 AND c.password LIKE ?2 AND c.email LIKE ?3").setParameter(1, id).setParameter(2, password).setParameter(3, email).getSingleResult();
            return employee;
        } catch (Exception e){
            LogInPageController logInPageController = new LogInPageController(entityManager);
            StageBuilder.showStage((Stage) node.getScene().getWindow(), logInPageController, "LogInPage.fxml", "Log in Page");
            return null;
        }

    }

    public static InsuranceManager getInsuranceManagerWithCredential(Node node, EntityManager entityManager, String id, String email, String password) {
        try{
            InsuranceManager employee = (InsuranceManager) entityManager.createQuery("SELECT c FROM InsuranceManager c WHERE c.id LIKE ?1 AND c.password LIKE ?2 AND c.email LIKE ?3").setParameter(1, id).setParameter(2, password).setParameter(3, email).getSingleResult();
            return employee;
        } catch (Exception e){
            LogInPageController logInPageController = new LogInPageController(entityManager);
            StageBuilder.showStage((Stage) node.getScene().getWindow(), logInPageController, "LogInPage.fxml", "Log in Page");
            return null;
        }

    }

    public static InsuranceSurveyor getInsuranceSurveyorWithCredential(Node node, EntityManager entityManager, String id, String email, String password) {
        try {
            InsuranceSurveyor employee = (InsuranceSurveyor) entityManager.createQuery("SELECT c FROM InsuranceSurveyor c WHERE c.id LIKE ?1 AND c.password LIKE ?2 AND c.email LIKE ?3").setParameter(1, id).setParameter(2, password).setParameter(3, email).getSingleResult();
            return employee;
        } catch (Exception e){
            LogInPageController logInPageController = new LogInPageController(entityManager);
            StageBuilder.showStage((Stage) node.getScene().getWindow(), logInPageController, "LogInPage.fxml", "Log in Page");
            return null;
        }

    }

    //FOR RETRIEVING OBJECT
    public static InsuranceManager getInsuranceManagerByCredential(EntityManager entityManager, String fullName, String email, String password, String phoneNumber, String address) {
        try {
            InsuranceManager insuranceManager = (InsuranceManager) entityManager.createQuery(
                            "SELECT c FROM InsuranceManager c WHERE c.email = :email AND c.password = :password AND c.fullName = :fullName AND c.phoneNumber = :phoneNumber AND c.address = :address")
                    .setParameter("email", email)
                    .setParameter("password", password)
                    .setParameter("fullName", fullName)
                    .setParameter("phoneNumber", phoneNumber)
                    .setParameter("address", address)
                    .getSingleResult();
            return insuranceManager;
        } catch (NoResultException e) {
            return null; // Handle case where no matching policy owner is found
        }
    }

    public static InsuranceSurveyor getInsuranceSurveyorByCredential(EntityManager entityManager, String fullName, String email, String password, String phoneNumber, String address) {
        try {
            InsuranceSurveyor insuranceSurveyor = (InsuranceSurveyor) entityManager.createQuery(
                            "SELECT c FROM InsuranceSurveyor c WHERE c.email = :email AND c.password = :password AND c.fullName = :fullName AND c.phoneNumber = :phoneNumber AND c.address = :address")
                    .setParameter("email", email)
                    .setParameter("password", password)
                    .setParameter("fullName", fullName)
                    .setParameter("phoneNumber", phoneNumber)
                    .setParameter("address", address)
                    .getSingleResult();
            return insuranceSurveyor;
        } catch (NoResultException e) {
            return null; // Handle case where no matching policy owner is found
        }
    }

    public static SystemAdmin getSystemAdminByCredential(EntityManager entityManager, String fullName, String email, String password, String phoneNumber, String address) {
        try {
            SystemAdmin systemAdmin = (SystemAdmin) entityManager.createQuery(
                            "SELECT c FROM SystemAdmin c WHERE c.email = :email AND c.password = :password AND c.fullName = :fullName AND c.phoneNumber = :phoneNumber AND c.address = :address")
                    .setParameter("email", email)
                    .setParameter("password", password)
                    .setParameter("fullName", fullName)
                    .setParameter("phoneNumber", phoneNumber)
                    .setParameter("address", address)
                    .getSingleResult();
            return systemAdmin;
        } catch (NoResultException e) {
            return null; // Handle case where no matching policy owner is found
        }
    }

    public static List<InsuranceSurveyor> getAllInsuranceSurveyorOfAnInsuranceManager(Node node, User user, EntityManager entityManager, String insuranceManagerID) {
        try {
            return entityManager.createQuery("SELECT c FROM InsuranceSurveyor c WHERE c.insuranceManagerId = ?1").setParameter(1, insuranceManagerID).getResultList();
        } catch (Exception e){
            Page404Controller page404Controller = new Page404Controller(user, entityManager);
            StageBuilder.showStage((Stage) node.getScene().getWindow(), page404Controller, "Page404.fxml", "An Error has occurred");
            return null;
        }

    }


//should I use map instead for this ranking method, if I want to display both user and their sucessful claim amount in the front-end ?



}
