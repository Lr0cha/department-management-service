package com.example.UserDept.web.dto.mapper;

import com.example.UserDept.entities.department.Department;
import com.example.UserDept.entities.employee.embedded.Address;
import org.modelmapper.ModelMapper;

import com.example.UserDept.web.dto.employee.EmployeeCreateDto;
import com.example.UserDept.web.dto.employee.EmployeeResponseDto;
import com.example.UserDept.entities.employee.Employee;
import org.springframework.data.domain.Page;

public class EmployeeMapper {
	private static final ModelMapper modelMapper = new ModelMapper();

	public static Employee toEmployee(EmployeeCreateDto createDto) {
		Employee emp = modelMapper.map(createDto,Employee.class);
		emp.setId(null);
		emp.setDepartment(new Department(createDto.getDepartmentId()));
		emp.setAddress(new Address());
		emp.getAddress().setZipCode(createDto.getZipCode());
		emp.getAddress().setHouseNumber(createDto.getHouseNumber());
        return emp;
	}
	
	public static EmployeeResponseDto toDto(Employee employee) {
		EmployeeResponseDto dto = modelMapper.map(employee, EmployeeResponseDto.class);
	    
	    dto.setDepartmentName(employee.getDepartment().getName());

	    return dto;
	}
	
	public static Page<EmployeeResponseDto> toListDto(Page<Employee> employees){
		return employees.map(EmployeeMapper::toDto);
	}
}
