package com.example.UserDept.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.UserDept.entities.Department;
import com.example.UserDept.repositories.DepartmentRepository;
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
		repository.deleteById(id);
	}
	
}
