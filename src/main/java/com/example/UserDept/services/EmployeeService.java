package com.example.UserDept.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.UserDept.dto.EmployeeCreateDto;
import com.example.UserDept.dto.EmployeeResponseDto;
import com.example.UserDept.dto.mapper.EmployeeMapper;
import com.example.UserDept.entities.Department;
import com.example.UserDept.entities.Employee;
import com.example.UserDept.repositories.DepartmentRepository;
import com.example.UserDept.repositories.EmployeeRepository;
import com.example.UserDept.services.exceptions.DatabaseException;
import com.example.UserDept.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class EmployeeService {
	
	@Autowired
	private EmployeeRepository repository;
	
	@Autowired
	private DepartmentRepository dep_repository;
	
	@Transactional(readOnly = true)
	public List<Employee> findAll() {
		return repository.findAll();
	}
	
	@Transactional(readOnly = true)
	public Employee findById(Long id) {
		Optional<Employee> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ResourceNotFoundException(id));
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
	public Employee update(Long id, Employee obj) {
		try {
			Employee entity = repository.getReferenceById(id);
			updateData(entity, obj);
			return repository.save(entity);
		}catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		}
	}

	private void updateData(Employee entity, Employee obj) {
		if (obj.getName() != null && !obj.getName().trim().isEmpty()) {
			entity.setName(obj.getName());
		}
		if (obj.getEmail() != null && !obj.getEmail().trim().isEmpty()) {
			entity.setEmail(obj.getEmail());
		}	
	}
}
