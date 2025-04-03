package com.example.UserDept.services;

import com.example.UserDept.exceptions.InvalidDataException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.UserDept.web.dto.employee.EmployeeEmailDto;
import com.example.UserDept.entities.Employee;
import com.example.UserDept.repositories.EmployeeRepository;
import com.example.UserDept.exceptions.DatabaseConflictException;
import com.example.UserDept.exceptions.ResourceNotFoundException;

@Slf4j
@Service
public class EmployeeService {

	@Autowired
	private EmployeeRepository repository;

	@Autowired
	private DepartmentService deptService;

	@Transactional(readOnly = true)
	public Page<Employee> findAll(Pageable pageable) {
		return repository.findAll(pageable);
	}

	@Transactional(readOnly = true)
	public Employee findById(Long id) {
		return repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(id));
	}

	@Transactional
	public Employee insert(Employee emp) {
		emp.setDepartment(deptService.findById(emp.getDepartment().getId()));

		if (repository.findByEmail(emp.getEmail()) != null) {
			throw new DatabaseConflictException(String.format("Email '%s' já existe no sistema", emp.getEmail()));
		}

		return repository.save(emp);
	}

	@Transactional
	public void delete(Long id) {
		findById(id);
		repository.deleteById(id);
	}

	@Transactional
	public void updateEmail(Long id, String currentEmail, String newEmail) {
		Employee emp = findById(id);
		if (!emp.getEmail().equals(currentEmail)) {
			throw new InvalidDataException("Email atual incorreto!");
		}

		emp.setEmail(newEmail);

		repository.save(emp);
	}

	protected boolean hasDependentEmployees(Long departmentId) {
		return repository.countByDepartmentId(departmentId) > 0;
	}
}
