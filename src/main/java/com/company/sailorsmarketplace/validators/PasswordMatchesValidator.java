package com.company.sailorsmarketplace.validators;

import com.company.sailorsmarketplace.rest.CreateUserRequest;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context){
        CreateUserRequest user = (CreateUserRequest) obj;
        return user.password.equals(user.matchingPassword);
    }
}