package com.example.UserDept.web.validations;

import com.example.UserDept.web.dto.employee.EmployeeUpdateDto;
import io.micrometer.common.util.StringUtils;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * Validator for employee updates.
 * Validates logical dependencies between fields like:
 * - zipCode and houseNumber must be provided together;
 * - If updating email, currentEmail must be present;
 * - If updating password, currentPassword and confirmPassword must be present and match;
 */
public class EmployeeUpdateValidator implements ConstraintValidator<ValidEmployeeUpdate, EmployeeUpdateDto> {

    @Override
    public boolean isValid(EmployeeUpdateDto dto, ConstraintValidatorContext context) {
        boolean valid = true;

        // Allow multiple violations to be reported
        context.disableDefaultConstraintViolation();

        if (!validateAddress(dto, context)) valid = false;
        if (!validateEmail(dto, context)) valid = false;
        if (!validatePassword(dto, context)) valid = false;

        return valid;
    }

    private boolean validateAddress(EmployeeUpdateDto dto, ConstraintValidatorContext context) {
        boolean isValid = true;
        boolean zipProvided = StringUtils.isNotBlank(dto.getZipCode());
        boolean numberProvided = dto.getHouseNumber() != null;

        if (zipProvided != numberProvided) {
            context.buildConstraintViolationWithTemplate("Zip code and house number must be provided together.")
                    .addPropertyNode("zipCode")
                    .addConstraintViolation();
            isValid = false;
        }
        return isValid;
    }

    private boolean validateEmail(EmployeeUpdateDto dto, ConstraintValidatorContext context) {
        boolean isValid = true;
        if (StringUtils.isNotBlank(dto.getNewEmail()) && StringUtils.isBlank(dto.getCurrentEmail())) {
            context.buildConstraintViolationWithTemplate("Current email is required when updating email.")
                    .addPropertyNode("currentEmail")
                    .addConstraintViolation();
            isValid = false;
        }
        return isValid;
    }

    private boolean validatePassword(EmployeeUpdateDto dto, ConstraintValidatorContext context) {
        boolean isValid = true;

        boolean newPwdPresent = StringUtils.isNotBlank(dto.getNewPassword());
        boolean currentPwdPresent = StringUtils.isNotBlank(dto.getCurrentPassword());
        boolean confirmPwdPresent = StringUtils.isNotBlank(dto.getConfirmPassword());

        if (newPwdPresent) {
            if (!currentPwdPresent) {
                context.buildConstraintViolationWithTemplate("Current password is required when updating password.")
                        .addPropertyNode("currentPassword")
                        .addConstraintViolation();
                isValid = false;
            }
            if (!confirmPwdPresent) {
                context.buildConstraintViolationWithTemplate("Password confirmation is required.")
                        .addPropertyNode("confirmPassword")
                        .addConstraintViolation();
                isValid = false;
            }
            if (confirmPwdPresent && !dto.getNewPassword().equals(dto.getConfirmPassword())) {
                context.buildConstraintViolationWithTemplate("New password and confirmation do not match.")
                        .addPropertyNode("confirmPassword")
                        .addConstraintViolation();
                isValid = false;
            }
        }

        return isValid;
    }
}


