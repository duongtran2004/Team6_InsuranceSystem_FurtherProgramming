package org.example.insurancemanagementapplication.Interfaces;

import Entity.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import Entity.Beneficiaries;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Luong Thanh Trung
 * @version ${}
 * @created 27/04/2024 04:56
 * @project InsuranceManagementTeamProject
 */
public interface CustomerRead {
    public static List<PolicyOwner> getAllPolicyOwner(EntityManager entityManager) {
        return entityManager.createQuery(
                "SELECT o FROM PolicyOwner o").getResultList();

    }

    public static List<PolicyHolder> getAllPolicyHolder(EntityManager entityManager) {
        return entityManager.createQuery(
                "SELECT e FROM PolicyHolder e WHERE e.type LIKE ?1").setParameter(1, "PH").getResultList();

    }

    public static List<Dependant> getAllDependant(EntityManager entityManager) {
        return entityManager.createQuery(
                "SELECT d FROM Beneficiaries d WHERE d.type LIKE ?1").setParameter(1, "DE").getResultList();

    } //unit testing for this method


    public static List<Dependant> getAllDependantsOfAPolicyHolder(EntityManager entityManager, String policyHolderID) {
        return entityManager.createQuery(
                        "SELECT d FROM Beneficiaries d WHERE d.type LIKE ?1 AND d.policyHolderId = ?2 AND d.type = 'DE'")
                .setParameter(1, "DE")
                .setParameter(2, policyHolderID)
                .getResultList();
    }

    public static List<PolicyHolder> getAllPolicyHoldersOfAPolicyOwner(EntityManager entityManager, String policyOwnerID) {
        return entityManager.createQuery(
                        "SELECT d FROM Beneficiaries d WHERE d.type LIKE ?1 AND d.policyOwnerId = ?2 AND d.type = 'PH'")
                .setParameter(1, "DE")
                .setParameter(2, policyOwnerID)
                .getResultList();
    }


    public static List<Dependant> getAllDependantsOfAPolicyOwner(EntityManager entityManager, String policyOwnerID) {
        return entityManager.createQuery(
                        "SELECT d FROM Beneficiaries d WHERE d.type LIKE ?1 AND d.policyOwnerId = ?2")
                .setParameter(1, "DE")
                .setParameter(2, policyOwnerID)
                .getResultList();
    }

    public static List<Beneficiaries> getAllBeneficiariesOfAPolicyOwner(EntityManager entityManager, String policyOwnerID) {
        return entityManager.createQuery(
                        "SELECT d FROM Beneficiaries d WHERE d.policyOwnerId = ?1")
                .setParameter(1, policyOwnerID)
                .getResultList();
    }


    public static List<Dependant> getAllDependantsTakeChargeByAnEmployee(EntityManager entityManager, String employeeID, String role) {
        List<Dependant> dependants = new ArrayList<>();
        Set<String> processedIds = new HashSet<>();

        String queryString;
        if (role.equals("InsuranceSurveyor")) {
            // Query for insurance surveyors
            queryString = "SELECT c FROM Claim c WHERE c.insuranceSurveyorId = :employeeId";
        } else if (role.equals("InsuranceManager")) {
            // Query for insurance managers
            queryString = "SELECT c FROM Claim c WHERE c.insuranceManagerId = :employeeId";
        } else {
            // Invalid role
            return dependants;
        }

        // Create a query
        Query query = entityManager.createQuery(queryString);
        query.setParameter("employeeId", employeeID);

        // Execute the query to get the claims
        List<Claim> claims = query.getResultList();

        // Iterate through each claim to retrieve the dependant objects
        for (Claim claim : claims) {
            // Check if the insured person ID of the claim starts with "DE"
            if (claim.getInsuredPersonId().startsWith("DE")) {
                // Retrieve the beneficiary object (insured person)
                Beneficiaries beneficiary = claim.getInsuredPerson();
                if (beneficiary instanceof Dependant) {
                    Dependant dependant = (Dependant) beneficiary;
                    // Check if the ID has already been processed
                    if (!processedIds.contains(dependant.getId())) {
                        dependants.add(dependant);
                        processedIds.add(dependant.getId());
                    }
                }
            }
        }
        return dependants;
    }


    public static List<PolicyHolder> getAllPolicyHoldersTakeChargeByAnEmployee(EntityManager entityManager, String employeeID, String role) {
        List<PolicyHolder> policyHolders = new ArrayList<>();
        Set<String> processedIds = new HashSet<>();

        String queryString;
        if (role.equals("InsuranceSurveyor")) {
            // Query for insurance surveyors
            queryString = "SELECT c FROM Claim c WHERE c.insuranceSurveyorId = :employeeId";
        } else if (role.equals("InsuranceManager")) {
            // Query for insurance managers
            queryString = "SELECT c FROM Claim c WHERE c.insuranceManagerId = :employeeId";
        } else {//invalid role
            return policyHolders;
        }

        // Create a query
        Query query = entityManager.createQuery(queryString);
        query.setParameter("employeeId", employeeID);

        // Execute the query to get the claims
        List<Claim> claims = query.getResultList();

        // Iterate through each claim to retrieve the dependant objects
        for (Claim claim : claims) {
            // Check if the insured person ID of the claim starts with "DE"
            if (claim.getInsuredPersonId().startsWith("PH")) {
                // Retrieve the beneficiary object (insured person)
                Beneficiaries beneficiary = claim.getInsuredPerson();
                if (beneficiary instanceof PolicyHolder) {
                    PolicyHolder policyHolder = (PolicyHolder) beneficiary;
                    // Check if the ID has already been processed
                    if (!processedIds.contains(policyHolder.getId())) {
                        policyHolders.add(policyHolder);
                        processedIds.add(policyHolder.getId());
                    }
                }
            }
        }
        return policyHolders;
    }
            // I




    //maybe the same is wrong for policy holder ? => fix

//get all policy holder take charge by an employee

    public static List<PolicyOwner> getAllPolicyOwnersTakeChargeByAnEmployee(EntityManager entityManager, String employeeID) {
        // Get list of claims which the insurance surveyor is processing
        List<Claim> claims = ClaimRead.getAllClaimsProcessByAnInsuranceSurveyor(entityManager, employeeID);
        List<PolicyOwner> policyOwners = new ArrayList<>(claims.size()); // Initialize the list to the size of claims

        for (Claim claim : claims) {
            // Retrieve the policyHolder associated with the claim
            PolicyOwner policyOwner = entityManager.createQuery(
                            "SELECT d FROM Beneficiaries d WHERE d.id = ?1 AND d.type = 'PH'", PolicyOwner.class)
                    .setParameter(1, claim.getInsuredPersonId())
                    .getSingleResult();

            policyOwners.add(policyOwner); // Add the policy owner to the list
        }
        return policyOwners;
    }


//this method is bugged
    //for login
//    public static Customer getCustomerWithCredentials(EntityManager entityManager, String email, String password, String id, String role) {
//        if (role.equals("Policy Owner")) {
//            PolicyOwner customer = (PolicyOwner) entityManager.createQuery("SELECT c FROM PolicyOwner c WHERE c.id LIKE ?1 AND c.password LIKE ?2 AND c.email LIKE ?3").setParameter(1, id).setParameter(2, password).setParameter(3, email).getSingleResult();
//            return customer;
//        } else {
//            if (role.equals("Dependant")) {
//                Dependant customer = (Dependant) entityManager.createQuery("SELECT c FROM Beneficiaries c WHERE c.id LIKE ?1 AND c.password LIKE ?2 AND c.email LIKE ?3 AND c.type = 'DE'").setParameter(1, id).setParameter(2, password).setParameter(3, email).setParameter(4, "DE").getSingleResult();
//                return customer;
//            } else {
//                PolicyHolder customer = (PolicyHolder) entityManager.createQuery("SELECT c FROM Beneficiaries c WHERE c.id LIKE ?1 AND c.password LIKE ?2 AND c.email LIKE ?3 AND c.type = ?4").setParameter(1, id).setParameter(2, password).setParameter(3, email).setParameter(4, "PH").getSingleResult();
//                return customer;
//            }
//        }

    //  }
    //write new methods for logins for each type of customer, as the old one does not work. remember to call them in login page controller

    public static SystemAdmin getSystemAdminWithCredential(EntityManager entityManager, String id, String email, String password) {
        SystemAdmin employee = (SystemAdmin) entityManager.createQuery("SELECT c FROM SystemAdmin c WHERE c.id LIKE ?1 AND c.password LIKE ?2 AND c.email LIKE ?3").setParameter(1, id).setParameter(2, password).setParameter(3, email).getSingleResult();
        return employee;
    }

    public static Dependant getDependentWithLoginCredentials(EntityManager entityManager, String email, String password, String id) {
        Query query = entityManager.createQuery("SELECT c FROM Beneficiaries c WHERE c.id LIKE ?1 AND c.password LIKE ?2 AND c.email LIKE ?3 AND c.type = 'DE'");
        query.setParameter(1, id);
        query.setParameter(2, password);
        query.setParameter(3, email);

        Object result = query.getSingleResult();
        //System.out.println("Type of object returned by getSingleResult(): " + result.getClass().getName());

        // Attempt casting
        Dependant dependant = (Dependant) result;
        // System.out.println("Dependant: " + dependant.toString());
        return dependant;

    }


    public static PolicyOwner getPolicyOwnerWithLoginCredentials(EntityManager entityManager, String email, String password, String id) {
        Query query = entityManager.createQuery("SELECT c FROM PolicyOwner c WHERE c.id LIKE ?1 AND c.password LIKE ?2 AND c.email LIKE ?3 ");
        query.setParameter(1, id);
        query.setParameter(2, password);
        query.setParameter(3, email);

        Object result = query.getSingleResult();
        //System.out.println("Type of object returned by getSingleResult(): " + result.getClass().getName());

        // Attempt casting
        PolicyOwner policyOwner = (PolicyOwner) result;
        return policyOwner;

    }

    public static PolicyHolder getPolicyHolderWithLoginCredentials(EntityManager entityManager, String email, String password, String id) {
        Query query = entityManager.createQuery("SELECT c FROM Beneficiaries c WHERE c.id LIKE ?1 AND c.password LIKE ?2 AND c.email LIKE ?3 AND c.type = 'PH'");
        query.setParameter(1, id);
        query.setParameter(2, password);
        query.setParameter(3, email);

        Object result = query.getSingleResult();
        //System.out.println("Type of object returned by getSingleResult(): " + result.getClass().getName());

        // Attempt casting
        PolicyHolder policyHolder = (PolicyHolder) result;
        System.out.println("PolicyHolder: " + policyHolder.toString());
        return policyHolder;

    }


    //For input validation and retrieve object

    public static PolicyOwner getPolicyOwnerByCredential(EntityManager entityManager, String fullName, String email, String password, String phoneNumber, String address) {
        try {
            PolicyOwner policyOwner = (PolicyOwner) entityManager.createQuery(
                            "SELECT c FROM PolicyOwner c WHERE c.email = :email AND c.password = :password AND c.fullName = :fullName AND c.phoneNumber = :phoneNumber AND c.address = :address")
                    .setParameter("email", email)
                    .setParameter("password", password)
                    .setParameter("fullName", fullName)
                    .setParameter("phoneNumber", phoneNumber)
                    .setParameter("address", address)
                    .getSingleResult();
            return policyOwner;
        } catch (NoResultException e) {
            return null; // Handle case where no matching policy owner is found
        }
    }

    public static Dependant getDependantByCredential(EntityManager entityManager, String fullName, String email, String password, String phoneNumber, String address) {
        try {
            Dependant dependant = (Dependant) entityManager.createQuery(
                            "SELECT c FROM Dependant c WHERE c.email = :email AND c.password = :password AND c.fullName = :fullName AND c.phoneNumber = :phoneNumber AND c.address = :address")
                    .setParameter("email", email)
                    .setParameter("password", password)
                    .setParameter("fullName", fullName)
                    .setParameter("phoneNumber", phoneNumber)
                    .setParameter("address", address)
                    .getSingleResult();
            return dependant;
        } catch (NoResultException e) {
            return null; // Handle case where no matching policy owner is found
        }
    }

    public static PolicyHolder getPolicyHolderByCredential(EntityManager entityManager, String fullName, String email, String password, String phoneNumber, String address) {
        try {
            PolicyHolder policyHolder = (PolicyHolder) entityManager.createQuery(
                            "SELECT c FROM PolicyHolder c WHERE c.email = :email AND c.password = :password AND c.fullName = :fullName AND c.phoneNumber = :phoneNumber AND c.address = :address")
                    .setParameter("email", email)
                    .setParameter("password", password)
                    .setParameter("fullName", fullName)
                    .setParameter("phoneNumber", phoneNumber)
                    .setParameter("address", address)
                    .getSingleResult();
            return policyHolder;
        } catch (NoResultException e) {
            return null; // Handle case where no matching policy owner is found
        }
    }

    //ranking user

    public static List<Customer> getAllCustomers(EntityManager entityManager) {
        List<Dependant> dependants = CustomerRead.getAllDependant(entityManager);
        List<PolicyHolder> policyHolders = CustomerRead.getAllPolicyHolder(entityManager);
        List<PolicyOwner> policyOwners = CustomerRead.getAllPolicyOwner(entityManager);
        List<Customer> allCustomers = new ArrayList<>();
        allCustomers.addAll(dependants);
        allCustomers.addAll(policyHolders);
        allCustomers.addAll(policyOwners);
        return allCustomers;

    }
    //rank each type of customer by successful claim Amount (remember to add the claim amount column)

    public static Map<String, Integer> rankAllCustomerBySuccessfulClaimAmount(EntityManager entityManager) {
        List<Customer> allCustomers = getAllCustomers(entityManager);

        // Calculate the total successful claim amount for each customer
        Map<String, Integer> totalClaimAmountMap = allCustomers.stream()
                .collect(Collectors.toMap(
                        customer -> customer.getId(),
                        customer -> ClaimRead.getTotalSuccessfulClaimAmountMadeByACustomer(entityManager, customer.getId(), customer.getClass().toString())
                ));

        // Sort the customers based on their total successful claim amount
        Map<String, Integer> rankedCustomers = totalClaimAmountMap.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, // Keep existing values (not necessary here)
                        LinkedHashMap::new // Preserve order
                ));

        return rankedCustomers;
    }


}
