package com.example.UserDept.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.UserDept.entities.Department;
import com.example.UserDept.services.DepartmentService;

@RestController
@RequestMapping(value = "/departments")
public class DepartmentController {
	@Autowired
	private DepartmentService service;
	
	@GetMapping
	public ResponseEntity<List<Department>> findAll(){
		List<Department> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Department> findById(@PathVariable Long id){
		Department obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}
}
