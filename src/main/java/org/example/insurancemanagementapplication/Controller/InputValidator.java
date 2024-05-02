package org.example.insurancemanagementapplication.Controller;

import jakarta.persistence.EntityManager;
import org.example.insurancemanagementapplication.Interfaces.CustomerAnalytics;
import org.example.insurancemanagementapplication.Interfaces.EmployeeAnalytics;

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

    public static void validateAddressFormat(String address) {

    }

    public static boolean validateClaimAmount(String claimAmount) {
        // Check if the claim amount is a positive integer
        try {
            double amount = Double.parseDouble(claimAmount);
            return amount == (int) amount && amount > 0;
        } catch (NumberFormatException e) {
            return false; // If parsing fails, it's not a valid number
        }
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



}
