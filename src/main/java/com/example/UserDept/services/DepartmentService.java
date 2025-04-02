package com.example.UserDept.services;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.UserDept.entities.Department;
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
		return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
	}
	
	@Transactional
	public Department insert(Department obj) {
		log.info("Criando departamento: {}", obj.getName());
		return repository.save(obj);
	}
	
	@Transactional
	public void delete(Long id) {
		if(findById(id) == null){
			throw new ResourceNotFoundException(id);
		}
		repository.deleteById(id);
	}
}
