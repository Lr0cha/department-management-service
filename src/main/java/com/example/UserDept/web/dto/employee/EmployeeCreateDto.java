package com.example.UserDept.web.dto.employee;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeCreateDto {
	@NotBlank
	private String name;

	@NotBlank
	@Email(message = "Formato do email inválido", regexp = "^[a-z0-9.+-]+@[a-z0-9.-]+\\.[a-z]{2,}$")
	private String email;

	@NotBlank
	@Pattern(regexp = "^\\(\\d{2}\\) \\d{5}-\\d{4}$", message = "Número de celular inválido. O formato deve ser (XX) XXXXX-XXXX")
	private String phoneNumber;

	@NotNull
	private Long departmentId;
}
