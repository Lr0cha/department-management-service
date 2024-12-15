package com.example.UserDept.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.example.UserDept.entities.Department;
import com.example.UserDept.repositories.DepartmentRepository;
import com.example.UserDept.services.exceptions.DatabaseException;
import com.example.UserDept.services.exceptions.ResourceNotFoundException;

@Service
public class DepartmentService {
	@Autowired
	private DepartmentRepository repository;
	
	public List<Department> findAll(){
		return repository.findAll();
	}
	
	public Department findById(Long id){
		Optional<Department> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ResourceNotFoundException(id));
	}
	
	public Department insert(Department obj) {
		return repository.save(obj);
	}
	
	public void delete(Long id) {
		verifyDepartmentExistsById(id);
		try {
			repository.deleteById(id);
		}catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e.getMessage());
		}
	}
	
	private Department verifyDepartmentExistsById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with " + id));
    }
	
}
