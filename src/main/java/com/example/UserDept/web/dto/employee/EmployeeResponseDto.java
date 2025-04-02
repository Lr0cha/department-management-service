package com.example.UserDept.web.dto.employee;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class EmployeeResponseDto {
	private Long id;
	private String name;
	private String email;
	private String department_name;

	public void setDepartment_name(@NotBlank String name) {
	}
}
