package com.example.UserDept.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.UserDept.entities.Department;
import com.example.UserDept.repositories.DepartmentRepository;
import com.example.UserDept.services.exceptions.DatabaseException;
import com.example.UserDept.services.exceptions.ResourceNotFoundException;



@Service
public class DepartmentService {
	@Autowired
	private DepartmentRepository repository;
	
	@Transactional(readOnly = true)
	public List<Department> findAll(){
		return repository.findAll();
	}
	@Transactional(readOnly = true)
	public Department findById(Long id){
		Optional<Department> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ResourceNotFoundException(id));
	}
	
	@Transactional
	public Department insert(Department obj) {
		return repository.save(obj);
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
	
}
