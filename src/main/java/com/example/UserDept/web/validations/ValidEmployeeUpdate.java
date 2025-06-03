package com.example.UserDept.web.validations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Constraint(validatedBy = EmployeeUpdateValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ValidEmployeeUpdate {
    String message() default "Invalid update request";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}