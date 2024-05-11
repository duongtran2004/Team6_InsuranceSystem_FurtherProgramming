package org.example.insurancemanagementapplication.Utility;

import Entity.InsuranceManager;
import jakarta.persistence.EntityManager;
import org.example.insurancemanagementapplication.Interfaces.CustomerRead;
import org.example.insurancemanagementapplication.Interfaces.EmployeeRead;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputValidator {
    public static boolean validatePasswordFormat(String password) {
        // Password format: 1 uppercase, 1 special character, more than 8 characters
        String pattern = "^(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
        return password.matches(pattern);
    }

    public static boolean passwordValidator(String password, String passwordValidator) {
        if (passwordValidator.equals(password)) {
            return true;
        }
        return false;
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
        if ((CustomerRead.getPolicyOwnerByCredential(entityManager, fullName, email, password, phoneNumber, address)) != null) {
            return true;
        }
        return false;
    }

    public static boolean checkIfPolicyHolderAlreadyExist(EntityManager entityManager, String fullName, String email, String password, String phoneNumber, String address) {
        if ((CustomerRead.getPolicyHolderByCredential(entityManager, fullName, email, password, phoneNumber, address)) != null) {
            return true;
        }
        return false;
    }

    public static boolean checkIfDependantAlreadyExist(EntityManager entityManager, String fullName, String email, String password, String phoneNumber, String address) {
        if ((CustomerRead.getDependantByCredential(entityManager, fullName, email, password, phoneNumber, address)) != null) {
            return true;
        }
        return false;
    }

    public static boolean checkIfInsuranceManagerAlreadyExist(EntityManager entityManager, String fullName, String email, String password, String phoneNumber, String address) {
        if ((EmployeeRead.getInsuranceManagerByCredential(entityManager, fullName, email, password, phoneNumber, address)) != null) {
            return true;
        }
        return false;
    }

    public static boolean checkIfInsuranceSurveyorAlreadyExist(EntityManager entityManager, String fullName, String email, String password, String phoneNumber, String address) {
        if ((EmployeeRead.getInsuranceSurveyorByCredential(entityManager, fullName, email, password, phoneNumber, address)) != null) {
            return true;
        }
        return false;
    }


    public static boolean checkIfSystemAdminAlreadyExist(EntityManager entityManager, String fullName, String email, String password, String phoneNumber, String address) {
        if ((EmployeeRead.getSystemAdminByCredential(entityManager, fullName, email, password, phoneNumber, address)) != null) {
            return true;
        }
        return false;
    }

    public static boolean validateBankingInfo(EntityManager entityManager, String bankName, String bankAccountName, String bankAccountNumber) {
        String message = "";
        if (!validateNonEmptyString(bankName)) {
            return false;
        }
        if (!validateNonEmptyString(bankAccountName)) {
            return false;
        }
        if (!validateNonEmptyString(bankAccountNumber)) {
            return false;
        } else {
            return true;
        }
    }

    public static String ClaimCreateValidator(EntityManager entityManager, String bankName, String accountName, String accountNumber) {
        String message = "";
        if (!validateBankingInfo(entityManager, bankName, accountName, accountNumber)) {
            return message = "Invalid Banking Information, no fields should be empty";
        }
        return message = "Success";

    }
//ClaimUpdateValidator: overloading methods
    //ClaimUpdateValidator: overloading methods


    //ClaimUpdateValidator for Insurance Manager
    public static String ClaimUpdateValidator(EntityManager entityManager, int claimAmount, InsuranceManager insuranceManager) {
        String message = "";
        if (!validateClaimAmount(claimAmount)) {
            return message = "Invalid Claim Amount, must be a positive integer";
        }
        if (!validateNonEmptyString(String.valueOf(claimAmount))) {
            return message = "Invalid Claim Amount, must not be empty";
        } else {
            return message = "Success";
        }
    }

    //ClaimUpdateValidator for PolicyHolder and PolicyOwner
    public static String ClaimUpdateValidator(EntityManager entityManager, String bankName, String accountName, String accountNumber) {
        String message = "";
        if (!validateBankingInfo(entityManager, bankName, accountName, accountNumber)) {
            return message = "Invalid Banking Information, no fields should be empty";
        } else {
            return message = "Success";
        }
    }

    //overloading method
    public static String validatingUser(String email, String password, String phoneNumber, String address, String passwordValidator) {
        if (!validateNonEmptyString(email)) {
            return "Invalid email, cannot be empty";
        } else if (!validateEmailFormat(email)) {
            return "Invalid email format";
        } else if (!validateNonEmptyString(password)) {
            return "Invalid password, cannot be empty";
        } else if (validatePasswordFormat(password)) {
            return "Invalid password format";
        } else if (!validateNonEmptyString(phoneNumber)) {
            return "Invalid phone number, cannot be empty";
        } else if (!validateNonEmptyString(address)) {
            return "Invalid address format";
        } else if (passwordValidator(passwordValidator, password)) {
            return "Passwords do not match.";
        } else {
            return "Success";
        }
    }

    //overloading method
    public static String validatingUser(String role, EntityManager entityManager, String fullName, String email, String password, String phoneNumber, String address, String passwordValidator) {
//        boolean allInfoValid = true;
//        boolean userExists = false;
//        boolean inputValidation = false;


        if (!validateNonEmptyString(fullName)) {
            return "Invalid full name, cannot be empty";
        }
        if (!validateNonEmptyString(email)) {
            return "Invalid email, cannot be empty";
        }
        if (!validateEmailFormat(email)) {
            return "Invalid email format";
        }
        if (!validateNonEmptyString(password)) {
            return "Invalid password, cannot be empty";
        }
        if (validatePasswordFormat(password)) {
            return "Invalid password format";
        }
        if (!validateNonEmptyString(phoneNumber)) {
            return "Invalid phone number, cannot be empty";
        }
        if (validatePhoneFormat(phoneNumber)) {
            return "Invalid address format";
        }
        if (passwordValidator(passwordValidator, password)) {
            return "Passwords do not match.";
        }

        if (role.equals("Dependant")) {
            if (checkIfDependantAlreadyExist(entityManager, fullName, email, password, phoneNumber, address)) {
                return "User already exist";
            }
        }
        if (role.equals("Insurance Manager")) {
            if (checkIfInsuranceManagerAlreadyExist(entityManager, fullName, email, password, phoneNumber, address)) {
                return "User already exist";
            }
        }
        if (role.equals("Insurance Surveyor")) {
            if (checkIfInsuranceSurveyorAlreadyExist(entityManager, fullName, email, password, phoneNumber, address)) {
                return "User already exist";
            }
        }
        if (role.equals("System Admin")) {
            if (checkIfSystemAdminAlreadyExist(entityManager, fullName, email, password, phoneNumber, address)) {
                return "User already exist";
            }
        }
        if (role.equals("Policy Holder")) {
            if (checkIfPolicyHolderAlreadyExist(entityManager, fullName, email, password, phoneNumber, address)) {
                return "User already exist";
            }
        }
        if (role.equals("Policy Owner")) {
            if (checkIfPolicyOwnerAlreadyExist(entityManager, fullName, email, password, phoneNumber, address)) {
                return "User already exist";
            }
        }
        return "Success";

    }

}
