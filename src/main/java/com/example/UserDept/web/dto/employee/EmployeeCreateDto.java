package com.example.UserDept.web.dto.employee;

import jakarta.validation.constraints.*;
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
	@Size(min = 6, max = 20)
	private String password;

	@NotBlank
	@Pattern(regexp = "^\\(\\d{2}\\) \\d{5}-\\d{4}$", message = "Número de celular inválido. O formato deve ser (XX) XXXXX-XXXX")
	private String phoneNumber;

	@NotBlank
	@Pattern(
			message = "Formato do cep inválido",
			regexp = "^(([0-9]{2}\\.[0-9]{3}-[0-9]{3})|([0-9]{5}-[0-9]{3})|([0-9]{8}))$"
	)
	private String zipCode;

	@NotNull
	private Integer houseNumber;

	@NotNull
	private Long departmentId;
}
