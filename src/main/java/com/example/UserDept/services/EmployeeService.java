package com.example.UserDept.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.example.UserDept.entities.Employee;
import com.example.UserDept.repositories.EmployeeRepository;
import com.example.UserDept.services.exceptions.DatabaseException;
import com.example.UserDept.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;
@Service
public class EmployeeService {
	
	@Autowired
	private EmployeeRepository repository;
	
	public List<Employee> findAll() {
		return repository.findAll();
	}

	public Employee findById(Long id) {
		Optional<Employee> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ResourceNotFoundException(id));
	}
	
	public Employee insert(Employee obj) {
		return repository.save(obj);
	}
	
	public void delete(Long id) {
		verifyEmployeeExistsById(id);
		try {
			repository.deleteById(id);
		}catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e.getMessage());
		}
	}
	
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
	
	private Employee verifyEmployeeExistsById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with " + id));
    }
}
