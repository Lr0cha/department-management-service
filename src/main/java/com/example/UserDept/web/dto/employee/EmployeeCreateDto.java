package com.example.UserDept.web.dto.employee;

import lombok.Getter;

@Getter
public class EmployeeCreateDto {
	private String name;
	private String email;
	private Long departmentId;
}
