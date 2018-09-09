package com.company.sailorsmarketplace.validators;

import com.company.sailorsmarketplace.rest.CreateUserRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

public class UserValidationUtil {
    private static ValidatorFactory vf = Validation.buildDefaultValidatorFactory();
    private static Validator validator = vf.getValidator();

    public UserValidationUtil() {    }

    public static void validate(CreateUserRequest user) {
        Set<ConstraintViolation<CreateUserRequest>> constraintViolations = validator.validate(user);

        System.out.println(user);
        System.out.println(String.format("Кол-во ошибок: %d",
                constraintViolations.size()));

        for (ConstraintViolation<CreateUserRequest> cv : constraintViolations) {
            System.out.println(String.format(
                    "Внимание, ошибка! property: [%s], value: [%s], message: [%s]",
                    cv.getPropertyPath(), cv.getInvalidValue(), cv.getMessage()));
        }
    }
}
