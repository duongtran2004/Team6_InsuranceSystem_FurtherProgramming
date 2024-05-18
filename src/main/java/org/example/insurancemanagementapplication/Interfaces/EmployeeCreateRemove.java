package org.example.insurancemanagementapplication.Interfaces;

import Entity.InsuranceManager;
import Entity.InsuranceSurveyor;
import Entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import javafx.scene.Node;
import javafx.stage.Stage;
import org.example.insurancemanagementapplication.Controller.Page404Controller;
import org.example.insurancemanagementapplication.Utility.StageBuilder;

import java.util.Random;

/**
 * @author Luong Thanh Trung
 * @version ${}
 * @created 27/04/2024 04:58
 * @project InsuranceManagementTeamProject
 */
public interface EmployeeCreateRemove {
    public static boolean createInsuranceManager(Node node, User user, EntityManager entityManager, String id, String fullName, String address, String phoneNumber, String email, String password){
        EntityTransaction transaction = entityManager.getTransaction();
        try{
            transaction.begin();
            InsuranceManager insuranceManager = new InsuranceManager();
            insuranceManager.setFullName(fullName);
            insuranceManager.setPhoneNumber(phoneNumber);
            insuranceManager.setAddress(address);
            insuranceManager.setEmail(email);
            insuranceManager.setPassword(password);
            insuranceManager.setId(id);
            entityManager.persist(insuranceManager);
            transaction.commit();

        }
        catch (Exception e){
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

    public static boolean createInsuranceSurveyor(Node node, User user, EntityManager entityManager, String id,  String fullName, String address, String phoneNumber, String email, String password, InsuranceManager insuranceManager){
        EntityTransaction transaction = entityManager.getTransaction();
        try{
            transaction.begin();
            InsuranceSurveyor insuranceSurveyor = new InsuranceSurveyor();
            insuranceSurveyor.setFullName(fullName);
            insuranceSurveyor.setPhoneNumber(phoneNumber);
            insuranceSurveyor.setAddress(address);
            insuranceSurveyor.setEmail(email);
            insuranceSurveyor.setPassword(password);
            Random random = new Random();
            for (int i = 0; i < 8; i++){
                id = id + random.nextInt(0, 10);
            }
            insuranceSurveyor.setId(id);
            insuranceSurveyor.setInsuranceManager(insuranceManager);
            entityManager.persist(insuranceSurveyor);
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

    public static boolean removeInsuranceManager(Node node, User user, EntityManager entityManager, InsuranceManager insuranceManager){
        EntityTransaction transaction = entityManager.getTransaction();
        try{
            transaction.begin();
            entityManager.remove(insuranceManager);
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

    public static boolean removeInsuranceSurveyor(Node node, User user,EntityManager entityManager, InsuranceSurveyor insuranceSurveyor){
        EntityTransaction transaction = entityManager.getTransaction();
        try{
            transaction.begin();
            entityManager.remove(insuranceSurveyor);
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
