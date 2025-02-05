package com.example.UserDept.dto;

public class EmployeeResponseDto {
	private Long id;
	private String name;
	private String email;
	private String department_name;
	
	public EmployeeResponseDto() {

	}

	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDepartment_name() {
		return department_name;
	}

	public void setDepartment_name(String dep) {
		this.department_name = dep;
	}
}
