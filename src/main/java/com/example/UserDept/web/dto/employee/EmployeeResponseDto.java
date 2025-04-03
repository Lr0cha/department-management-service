package com.example.UserDept.web.dto.employee;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeResponseDto {
	private Long id;
	private String name;
	private String email;
	private String departmentName;
}
