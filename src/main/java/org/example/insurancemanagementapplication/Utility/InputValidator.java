package org.example.insurancemanagementapplication.Utility;

import Entity.InsuranceManager;
import Entity.InsuranceSurveyor;
import jakarta.persistence.EntityManager;
import org.example.insurancemanagementapplication.Interfaces.CustomerAnalytics;
import org.example.insurancemanagementapplication.Interfaces.EmployeeAnalytics;

import java.util.ArrayList;
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
    public static boolean validateBankingInfo(EntityManager entityManager, String bankName, String bankAccountName, String bankAccountNumber) {
        String message = "";
        if (validateNonEmptyString(bankName) == false) {
            return false;
        }
        if (validateNonEmptyString(bankAccountName) == false) {
            return false;
        }
        if (validateNonEmptyString(bankAccountNumber) == false) {
            return false;
        } else {
            return true;
        }
    }

    public static String ClaimCreateValidator(EntityManager entityManager, String bankName, String accountName, String accountNumber) {
        String message = "";
        if (validateBankingInfo(entityManager, bankName, accountName, accountNumber) == false) {
            return message = "Invalid Banking Information, no fields should be empty";
        } return  message = "Success";

    }
//ClaimUpdateValidator: overloading methods
    //ClaimUpdateValidator: overloading methods


    //ClaimUpdateValidator for Insurance Manager
    public static String ClaimUpdateValidator(EntityManager entityManager, int claimAmount, InsuranceManager insuranceManager, String insuranceSurveyorId) {
        String message = "";
        if (validateClaimAmount(claimAmount) == false) {
            return message = "Invalid Claim Amount, must be a positive integer";
        }
        if (validateNonEmptyString(String.valueOf(claimAmount)) == false) {
            return message = "Invalid Claim Amount, must not be empty";
        }

        if (checkIfAnInsuranceSurveyorBelongsToAnInsuranceManager(entityManager, insuranceManager, insuranceSurveyorId) == true) {

        } else {
            return message = "Success";
        }
        return message;
    }

    //ClaimUpdateValidator for PolicyHolder and PolicyOwner
    public static String ClaimUpdateValidator(EntityManager entityManager, String bankName, String accountName, String accountNumber) {
        String message = "";
       if (validateBankingInfo( entityManager,bankName, accountName, accountNumber) == false){
           return message = "Invalid Banking Information, no fields should be empty";
        } else {
            return message = "Success";
        }
    }



    public static String validatingUser(String role, EntityManager entityManager, String fullName, String email, String password, String phoneNumber, String address) {
//        boolean allInfoValid = true;
//        boolean userExists = false;
//        boolean inputValidation = false;
        String message = "";

        if (validateNonEmptyString(fullName) == false) {
            return message = "Invalid full name, cannot be empty";
        }
        if (validateNonEmptyString(email) == false) {
            return message = "Invalid email, cannot be empty";
        }
        if (validateNonEmptyString(email) == false) {
            return message = "Invalid email, cannot be empty";
        }
        if (validateNonEmptyString(password) == false) {
            return message = "Invalid email, cannot be empty";
        }
        if (validateNonEmptyString(password) == false) {
            return message = "Invalid email, cannot be empty";
        }
        if (validateNonEmptyString(phoneNumber) == false) {
            return message = "Invalid phone number, cannot be empty";
        }
        if (validateNonEmptyString(address) == false) {
            return message = "Invalid address, cannot be empty";
        }
        if (role == "Dependant") {
            if (checkIfDependantAlreadyExist(entityManager, fullName, email, password, phoneNumber, address) == true) {
                return message = "User already exist";
            }
        }
        if (role == "InsuranceManager") {
            if (checkIfInsuranceManagerAlreadyExist(entityManager, fullName, email, password, phoneNumber, address) == true) {
                return message = "User already exist";
            }
        }
        if (role == "InsuranceSurveyor") {
            if (checkIfInsuranceSurveyorAlreadyExist(entityManager, fullName, email, password, phoneNumber, address) == true) {
                return message = "User already exist";
            }
        }
        if (role == "SystemAdmin") {
            if (checkIfSystemAdminAlreadyExist(entityManager, fullName, email, password, phoneNumber, address) == true) {
                return message = "User already exist";
            }
        }
        if (role == "PolicyHolder") {
            if (checkIfPolicyHolderAlreadyExist(entityManager, fullName, email, password, phoneNumber, address) == true) {
                return message = "User already exist";
            }
        }
        if (role == "PolicyOwner") {
            if (checkIfPolicyOwnerAlreadyExist(entityManager, fullName, email, password, phoneNumber, address) == true) {
                return message = "User already exist";
            }
        }
        return message = "Success";

    }
    public static boolean checkIfAnInsuranceSurveyorBelongsToAnInsuranceManager(EntityManager entityManager, InsuranceManager insuranceManager, String targetInsuranceSurveyorId) {
        boolean insuranceSurveyorExist = false;
        String insuranceSurveyorId = "";
        ArrayList<InsuranceSurveyor> surveyors = (ArrayList<InsuranceSurveyor>) insuranceManager.getListOfSurveyors();

        // Loop through the list of surveyors
        for (InsuranceSurveyor surveyor : surveyors) {
            insuranceSurveyorId = surveyor.getId();

            if (insuranceSurveyorId.equals(targetInsuranceSurveyorId)) {
                insuranceSurveyorExist = true;
                break; // Exit the loop since we found the surveyor ID
            }
        }
        return insuranceSurveyorExist;
    }
}
