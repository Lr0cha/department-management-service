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
	@Email(message = "Invalid email format", regexp = "^[a-z0-9.+-]+@[a-z0-9.-]+\\.[a-z]{2,}$")
	private String email;

	@NotBlank
	@Size(min = 6, max = 20)
	private String password;

	@NotBlank
	@Pattern(regexp = "^\\(\\d{2}\\) \\d{5}-\\d{4}$", message = "Invalid mobile number. The format should be (XX) XXXXX-XXXX")
	private String phoneNumber;

	@NotBlank
	@Pattern(
			message = "Invalid cep format",
			regexp = "^(([0-9]{2}\\.[0-9]{3}-[0-9]{3})|([0-9]{5}-[0-9]{3})|([0-9]{8}))$"
	)
	private String zipCode;

	@NotNull
	@Positive
	private Integer houseNumber;

	@NotNull
	@Positive
	private Long departmentId;
}
