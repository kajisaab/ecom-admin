package com.kajisaab.ecommerce.core.validation;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.ValidatorFactory;

import java.util.Objects;
import java.util.Set;

public class ValidationUtils {

    public static <S> String validate(S request) {
        return validate(request, "");
    }

    public static <S> String validate(S request, String email) {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        jakarta.validation.Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<S>> violations = validator.validate(request);
        if (!violations.isEmpty()) {
            return violations.stream().findFirst().get().getMessage();
        }

        if(!Objects.equals(email, "") && !isValidEmail(email)){
            return "Email is invalid";
        }

        return null;
    }

    public static boolean isValidEmail(String email) {
        String emailRegex = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" +
                "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})";

        return email.matches(emailRegex);
    }

}