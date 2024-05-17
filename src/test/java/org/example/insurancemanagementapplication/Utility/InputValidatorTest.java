package org.example.insurancemanagementapplication.Utility;

import org.junit.jupiter.api.Test;

class InputValidatorTest {

//    @Test
//    void validateEmailFormat() {
//        InputValidator validator = new InputValidator();
//        validator.validateEmailFormat("duongtran@rmit.com");
//    }

    //@Test
    void validatePasswordFormat() {
        InputValidator validator = new InputValidator();
        boolean correctPasswordFormat = validator.validatePasswordFormat("Duongrmit2004");
        System.out.println(correctPasswordFormat);
    }

    @Test
    void validateEmailFormat() {
        InputValidator validator = new InputValidator();
        boolean correctEmailFormat = validator.validateEmailFormat("trungluong0899@gmail.com");
        System.out.println(correctEmailFormat);
    }
}