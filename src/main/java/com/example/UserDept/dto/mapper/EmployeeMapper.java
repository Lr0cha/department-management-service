package com.example.UserDept.dto.mapper;

import org.modelmapper.ModelMapper;

import com.example.UserDept.dto.EmployeeCreateDto;
import com.example.UserDept.dto.EmployeeResponseDto;
import com.example.UserDept.entities.Employee;

public class EmployeeMapper {
	public static Employee toEmployee(EmployeeCreateDto createDto) {
		return new ModelMapper().map(createDto, Employee.class);
	}
	
	public static EmployeeResponseDto toDto(Employee employee) {
		return new ModelMapper().map(employee, EmployeeResponseDto.class);
	}
}
