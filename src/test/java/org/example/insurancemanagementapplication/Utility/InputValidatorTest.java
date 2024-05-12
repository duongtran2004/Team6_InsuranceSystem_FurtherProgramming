package org.example.insurancemanagementapplication.Utility;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InputValidatorTest {

//    @Test
//    void validateEmailFormat() {
//        InputValidator validator = new InputValidator();
//        validator.validateEmailFormat("duongtran@rmit.com");
//    }

    @Test
    void validatePasswordFormat() {
        InputValidator validator = new InputValidator();
        boolean correctPasswordFormat = validator.validatePasswordFormat("DuongRmit2004");
        System.out.println(correctPasswordFormat);
}}