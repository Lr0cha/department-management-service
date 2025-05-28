package com.example.UserDept.web.dto.employee;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class EmployeePhoneDto {
    @Pattern(regexp = "^\\(\\d{2}\\) \\d{5}-\\d{4}$", message = "Número de celular inválido. O formato deve ser (XX) XXXXX-XXXX")
    private String phone;
}
