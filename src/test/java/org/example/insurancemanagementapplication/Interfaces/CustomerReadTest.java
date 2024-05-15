package org.example.insurancemanagementapplication.Interfaces;

import Entity.PolicyOwner;
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

//    @Test
//    void getAllDependantTakeChargeByAnEmployee() {
//        List<Dependant> dependants =
//                CustomerRead.getAllDependantsTakeChargeByAnEmployee(entityManager, "IS402665881", "InsuranceSurveyor");
////        System.out.println(dependants);
//        for (Dependant dependant : dependants) {
//            System.out.println(dependant);
//        }
//
//    }
//    @Test
//    void getAllPolicyHolderTakeChargeByAnEmployee(){
//        List<PolicyHolder> policyHolders =
//                CustomerRead.getAllPolicyHoldersTakeChargeByAnEmployee(entityManager,"IM17150987", "InsuranceManager");
//    for (PolicyHolder policyHolder : policyHolders) {
//        System.out.println(policyHolder);
//    }
//    }

//    @Test
//    void  getAllPolicyOwnerTakeChargeByAnEmployee(){
//        List<PolicyOwner> policyOwners =
//                CustomerRead.getAllPolicyOwnersTakeChargeByAnEmployee(entityManager, "IM4719020624","InsuranceManager" );
//        for (PolicyOwner policyOwner : policyOwners) {
//            System.out.println(policyOwner);
//        }
//    }
    @Test
    void getAllPOandPrintAlClaims(){
        List<PolicyOwner> policyOwnerList = CustomerRead.getAllPolicyOwner(entityManager);
        for(PolicyOwner policyOwner : policyOwnerList){
            System.out.println(policyOwner.getListOfClaims());
        }
    }



}