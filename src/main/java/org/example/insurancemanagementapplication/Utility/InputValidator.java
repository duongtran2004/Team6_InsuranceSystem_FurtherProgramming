package org.example.insurancemanagementapplication.Utility;

import jakarta.persistence.EntityManager;
import org.example.insurancemanagementapplication.Interfaces.CustomerAnalytics;
import org.example.insurancemanagementapplication.Interfaces.EmployeeAnalytics;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputValidator {


    public static boolean validatePasswordFormat(String password) {
        // Password format: 1 uppercase, 1 special character, more than 8 characters
        String pattern = "^(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
        return password.matches(pattern);
    }

    public static boolean validateEmailFormat(String email) {
        //email must include "@" and ".com"
        // Regular expression for email format
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

        // Compile the regular expression
        Pattern pattern = Pattern.compile(emailRegex);

        // Match the input email with the pattern
        Matcher matcher = pattern.matcher(email);

        // Check if the email matches the pattern
        if (matcher.matches()) {
            System.out.println("Email format is valid.");
            return true;


        } else {
            System.out.println("Email format is not valid.");
            return false;
        }
    }

    public static boolean validatePhoneFormat(String phoneNumber) {
        // Check if the phone number consists of only digits and has a length of 10
        if (phoneNumber.length() != 10) {
            return false; // If the length is not 10, return false
        }

        // Check if each character in the phone number is a digit
        for (char digit : phoneNumber.toCharArray()) {
            if (!Character.isDigit(digit)) {
                return false; // If any character is not a digit, return false
            }
        }

        return true; // If the phone number is all digits and has length 10, return true
    }


    public static boolean validateClaimAmount(int claimAmount) {
        // Check if true if the claim amount is a positive integer
        return claimAmount > 0;
    }

    public static boolean validateNonEmptyString(String nonEmptyString) {
        if (nonEmptyString == null || nonEmptyString.isEmpty()) {
            return false;
        }

        return true;
    }


    public static boolean checkIfPolicyOwnerAlreadyExist(EntityManager entityManager, String fullName, String email, String password, String phoneNumber, String address) {
        if ((CustomerAnalytics.getPolicyOwnerByCredential(entityManager, fullName, email, password, phoneNumber, address)) != null) {
            return true;
        }
        return false;
    }

    public static boolean checkIfPolicyHolderAlreadyExist(EntityManager entityManager, String fullName, String email, String password, String phoneNumber, String address) {
        if ((CustomerAnalytics.getPolicyHolderByCredential(entityManager, fullName, email, password, phoneNumber, address)) != null) {
            return true;
        }
        return false;
    }

    public static boolean checkIfDependantAlreadyExist(EntityManager entityManager, String fullName, String email, String password, String phoneNumber, String address) {
        if ((CustomerAnalytics.getDependantByCredential(entityManager, fullName, email, password, phoneNumber, address)) != null) {
            return true;
        }
        return false;
    }

    public static boolean checkIfInsuranceManagerAlreadyExist(EntityManager entityManager, String fullName, String email, String password, String phoneNumber, String address) {
        if ((EmployeeAnalytics.getInsuranceManagerByCredential(entityManager, fullName, email, password, phoneNumber, address)) != null) {
            return true;
        }
        return false;
    }

    public static boolean checkIfInsuranceSurveyorAlreadyExist(EntityManager entityManager, String fullName, String email, String password, String phoneNumber, String address) {
        if ((EmployeeAnalytics.getInsuranceSurveyorByCredential(entityManager, fullName, email, password, phoneNumber, address)) != null) {
            return true;
        }
        return false;
    }

    public static boolean checkIfSystemAdminAlreadyExist(EntityManager entityManager, String fullName, String email, String password, String phoneNumber, String address) {
        if ((EmployeeAnalytics.getSystemAdminByCredential(entityManager, fullName, email, password, phoneNumber, address)) != null) {
            return true;
        }
        return false;
    }


    public static boolean validatingClaim(EntityManager entityManager, String claimId, Date creationDate, Date settlementDate, String status, int claimAmount, String insuredPersonId, String policyOwnerId, String cardNumber, String insuranceManagerId, String insuranceSurveyorId, String bankName, String accountName, String accountNumber, byte[] documentImage) {
        if (validateClaimAmount(claimAmount) == true && validateNonEmptyString(bankName) == true && validateNonEmptyString(accountName) == true && validateNonEmptyString(accountNumber) == true) {
            return true;
        }
        return false;
    }

    public static boolean validatingUser(String role, EntityManager entityManager, String fullName, String email, String password, String phoneNumber, String address) {
        boolean allInfoValid = true;
        boolean userExists = false;
        boolean inputValidation = false;

        if (!validateNonEmptyString(fullName) ||
                !validateNonEmptyString(email) ||
                !validateNonEmptyString(password) ||
                !validateNonEmptyString(phoneNumber) ||
                !validateNonEmptyString(address) ||
                !validatePasswordFormat(password) ||
                !validateEmailFormat(email) ||
                !validatePhoneFormat(phoneNumber)
        ) {
            allInfoValid = false;
        }


        if (role == "Dependant") {
            userExists =
                    checkIfDependantAlreadyExist(entityManager, fullName, email, password, phoneNumber, address);
        }
        if (role == "InsuranceManager") {
            userExists =
                    checkIfInsuranceManagerAlreadyExist(entityManager, fullName, email, password, phoneNumber, address);
        }
        if (role == "InsuranceSurveyor") {
            userExists =
                    checkIfInsuranceSurveyorAlreadyExist(entityManager, fullName, email, password, phoneNumber, address);
        }
        if (role == "SystemAdmin") {
            userExists =
                    checkIfSystemAdminAlreadyExist(entityManager, fullName, email, password, phoneNumber, address);
        }
        if (role == "PolicyHolder") {
            userExists =
                    checkIfPolicyHolderAlreadyExist(entityManager, fullName, email, password, phoneNumber, address);
        }
        if (role == "PolicyOwner") {
            userExists =
                    checkIfPolicyOwnerAlreadyExist(entityManager, fullName, email, password, phoneNumber, address);
        }
        // user pass input validation if: AllInfoValid AND UserNotExist
        if (allInfoValid == true && userExists == false
        ) {
            inputValidation = true;
        }


        return inputValidation;


    }
}
