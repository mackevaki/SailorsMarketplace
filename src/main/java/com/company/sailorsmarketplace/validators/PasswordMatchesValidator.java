package com.company.sailorsmarketplace.validators;

import com.company.sailorsmarketplace.rest.CreateUpdateUserRequest;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context){
        CreateUpdateUserRequest user = (CreateUpdateUserRequest) obj;
        return user.password.equals(user.matchingPassword);
    }
}