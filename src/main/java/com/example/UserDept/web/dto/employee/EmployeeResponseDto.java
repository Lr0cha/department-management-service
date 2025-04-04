package com.example.UserDept.web.dto.employee;

import com.example.UserDept.entities.enums.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeResponseDto {
	private Long id;
	private String name;
	private String email;
	private String phoneNumber;
	private String departmentName;
	private Role role;
}
