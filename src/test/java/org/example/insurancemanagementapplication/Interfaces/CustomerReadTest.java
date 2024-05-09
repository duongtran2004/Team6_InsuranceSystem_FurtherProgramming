package org.example.insurancemanagementapplication.Interfaces;

import Entity.Dependant;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.Test;

import java.util.List;

class CustomerReadTest {
    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
    EntityManager entityManager = entityManagerFactory.createEntityManager();
//    @Test
//    void getDependentWithLoginCredentials() {
//        CustomerRead.getDependentWithLoginCredentials(entityManager,"szanettini9@dion.ne.jp","55903705", "DE5087972690" );
//
//    }

//    @Test
//    void getPolicyHolderWithLoginCredential() {
//        CustomerRead.getPolicyHolderWithLoginCredentials(entityManager,"swalhedd0@scribd.com","33902597", "PH5264972101");
//    }
//    @Test
//    void getAllDependent(){
//        List<Dependant>  dependants = CustomerRead.getAllDependant(entityManager);
//        for(Dependant dependant : dependants){
//            System.out.println(dependant);
//        }

    @Test
    void getAllDependantTakeChargeByAnInsuranceSurveyor() {
        List<Dependant> dependants =
                CustomerRead.getAllDependantsTakeChargeByAnInsuranceSurveyor(entityManager, "IS402665881");
//        System.out.println(dependants);
        for (Dependant dependant : dependants) {
            System.out.println(dependant);
        }

    }


}