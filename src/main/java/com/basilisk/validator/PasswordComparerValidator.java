package com.basilisk.validator;

import com.basilisk.dto.RegisterDTO;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordComparerValidator implements ConstraintValidator<PasswordComparer, RegisterDTO> {
    @Override
    public void initialize(PasswordComparer constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(RegisterDTO value, ConstraintValidatorContext context) {
        return value.getPassword().equals(value.getConfirmPassword());
    }
}
