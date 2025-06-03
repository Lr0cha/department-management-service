package com.example.UserDept.web.dto.employee;

import com.example.UserDept.web.validations.ValidEmployeeUpdate;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@ValidEmployeeUpdate
@Getter
public class EmployeeUpdateDto {

    @Email(message = "Invalid email format", regexp = "^[a-z0-9.+-]+@[a-z0-9.-]+\\.[a-z]{2,}$")
    private String currentEmail;

    @Email(message = "Invalid email format", regexp = "^[a-z0-9.+-]+@[a-z0-9.-]+\\.[a-z]{2,}$")
    private String newEmail;

    @Size(min=6, max=20)
    private String currentPassword;

    @Size(min=6, max=20)
    private String newPassword;

    @Size(min=6, max=20)
    private String confirmPassword;

    @Pattern(regexp = "^\\(\\d{2}\\) \\d{5}-\\d{4}$", message = "Invalid mobile number. The format should be (XX) XXXXX-XXXX")
    private String phoneNumber;

    @Positive
    private Long departmentId;

    @Pattern(
            message = "Invalid cep format",
            regexp = "^(([0-9]{2}\\.[0-9]{3}-[0-9]{3})|([0-9]{5}-[0-9]{3})|([0-9]{8}))$"
    )
    private String zipCode;

    private Integer houseNumber;
}
