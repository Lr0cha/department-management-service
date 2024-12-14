package com.example.UserDept.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.UserDept.entities.Employee;
import com.example.UserDept.repositories.EmployeeRepository;
@Service
public class EmployeeService {
	
	@Autowired
	private EmployeeRepository repository;
	
	public List<Employee> findAll() {
		return repository.findAll();
	}

	public Employee findById(Long id) {
		Optional<Employee> obj = repository.findById(id);
		return obj.get();
	}
	
	public Employee insert(Employee obj) {
		return repository.save(obj);
	}
}
