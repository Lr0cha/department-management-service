package com.example.UserDept.dto.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;

import com.example.UserDept.dto.EmployeeCreateDto;
import com.example.UserDept.dto.EmployeeResponseDto;
import com.example.UserDept.entities.Employee;

public class EmployeeMapper {
	public static Employee toEmployee(EmployeeCreateDto createDto) {
		return new ModelMapper().map(createDto, Employee.class);
	}
	
	public static EmployeeResponseDto toDto(Employee employee) {
		EmployeeResponseDto dto = new ModelMapper().map(employee, EmployeeResponseDto.class);
	    
	    dto.setDepartment_name(employee.getDepartment().getName());
	    
	    return dto;
	}
	
	public static List<EmployeeResponseDto> toListDto(List<Employee> employees){
		return employees.stream().map(emp -> toDto(emp)).collect(Collectors.toList());
	}
}
