package com.example.UserDept.services;


import com.example.UserDept.exceptions.DatabaseConflictException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.UserDept.entities.department.Department;
import com.example.UserDept.repositories.DepartmentRepository;
import com.example.UserDept.exceptions.ResourceNotFoundException;


@Slf4j
@Service
public class DepartmentService {

	@Autowired
	private DepartmentRepository repository;

	@Transactional(readOnly = true)
	public Page<Department> findAll(Pageable pageable){
		return repository.findAll(pageable);
	}

	@Transactional(readOnly = true)
	public Department findById(Long id){
		log.info("Buscando departamento com ID: {}", id);
		return repository.findById(id).orElseThrow(() -> {
			log.error("Departamento com ID {} não encontrado", id);
			return new ResourceNotFoundException("Department ID not found: " + id);
		});
	}
	
	@Transactional
	public Department insert(Department dept) {
		log.info("Criando departamento: {}", dept.getName());
		if(repository.findByName(dept.getName()) != null){
			throw new DatabaseConflictException(String.format("The department '%s' already exists", dept.getName()));
		}
		return repository.save(dept);
	}
	
	@Transactional
	public void delete(Long id) {
		findById(id);
		if(repository.countEmployeesInDepartment(id) > 0){
			throw new DatabaseConflictException("The department cannot be deleted because it has associated employees.");
		}
		repository.deleteById(id);
	}
}
