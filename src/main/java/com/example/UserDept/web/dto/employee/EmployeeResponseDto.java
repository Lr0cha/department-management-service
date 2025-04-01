package com.example.UserDept.web.dto.employee;

import lombok.Getter;

@Getter
public class EmployeeResponseDto {
	private Long id;
	private String name;
	private String email;
	private String department_name;
}
