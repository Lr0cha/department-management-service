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
	
	public void delete(Long id) {
		repository.deleteById(id);
	}
	
	public Employee update(Long id, Employee obj) {
		Employee entity = repository.getReferenceById(id);
		updateData(entity, obj);
		return repository.save(entity);
	}

	private void updateData(Employee entity, Employee obj) {
		entity.setName(obj.getName());
		entity.setEmail(obj.getEmail());	
	}
}
