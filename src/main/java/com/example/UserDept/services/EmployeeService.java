package com.example.UserDept.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.UserDept.web.dto.employee.EmployeeCreateDto;
import com.example.UserDept.web.dto.employee.EmployeeEmailDto;
import com.example.UserDept.web.dto.employee.EmployeeResponseDto;
import com.example.UserDept.web.dto.mapper.EmployeeMapper;
import com.example.UserDept.entities.Department;
import com.example.UserDept.entities.Employee;
import com.example.UserDept.repositories.DepartmentRepository;
import com.example.UserDept.repositories.EmployeeRepository;
import com.example.UserDept.exceptions.DatabaseException;
import com.example.UserDept.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class EmployeeService {
	
	@Autowired
	private EmployeeRepository repository;
	
	@Autowired
	private DepartmentRepository dep_repository;
	
	@Transactional(readOnly = true)
	public List<EmployeeResponseDto> findAll() {
		List<Employee> employees = repository.findAll();
		
        return employees.stream()
                        .map(EmployeeMapper::toDto)
                        .collect(Collectors.toList()); 
    }
	
	@Transactional(readOnly = true)
	public EmployeeResponseDto findById(Long id) {
		Employee emp = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));
		
		return EmployeeMapper.toDto(emp);
	}
	
	@Transactional
	 public EmployeeResponseDto insert(EmployeeCreateDto dto) {
        Department dep = dep_repository.findById(dto.getDepartmentId())
                .orElseThrow(() -> new RuntimeException("Department not found"));

        Employee emp = EmployeeMapper.toEmployee(dto);
        emp.setId(null);

        emp.setDepartment(dep);
        emp = repository.save(emp);

        EmployeeResponseDto responseDto = EmployeeMapper.toDto(emp);
        
        responseDto.setDepartment_name(dep.getName());

        return responseDto;
    }
	
	@Transactional
	public void delete(Long id) {
		findById(id);
		try {
			repository.deleteById(id);
		}catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e.getMessage());
		}
	}
	
	@Transactional
	public Employee update(Long id, EmployeeEmailDto dto) {
		
		try {
			if(!dto.getNewEmail().equals(dto.getConfirmEmail())) {
				throw new RuntimeException("Novos emails não conferem!");
			}
			Employee employee = repository.findById(id).get();
			if(!employee.getEmail().equals(dto.getCurrentEmail())) {
				throw new RuntimeException("email atual não confere!");
			}
			
			employee.setEmail(dto.getNewEmail());
			
			return repository.save(employee);
		}catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		}
	}
}
