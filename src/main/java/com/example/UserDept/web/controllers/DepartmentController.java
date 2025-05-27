package com.example.UserDept.web.controllers;

import com.example.UserDept.web.dto.department.DepartmentDto;
import com.example.UserDept.web.dto.mapper.DepartmentMapper;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.UserDept.entities.department.Department;
import com.example.UserDept.services.DepartmentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/departments")
public class DepartmentController {
	@Autowired
	private DepartmentService service;
	
	@GetMapping
	public ResponseEntity<Page<DepartmentDto>> findAll(Pageable pageable){
		Page<Department> departments = service.findAll(pageable);
		return ResponseEntity.ok().body(DepartmentMapper.toDtos(departments));
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<DepartmentDto> findById(@PathVariable Long id){
		Department dept = service.findById(id);
		return ResponseEntity.ok().body(DepartmentMapper.toDto(dept));
	}
	
	@PostMapping
	public ResponseEntity<DepartmentDto> insert(@Valid @RequestBody DepartmentDto dto){
		Department dept = service.insert(DepartmentMapper.toDepartment(dto));
		return ResponseEntity.status(HttpStatus.CREATED).body(DepartmentMapper.toDto(dept));
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id){
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
}
