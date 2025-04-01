package com.example.UserDept.web.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.UserDept.web.dto.employee.EmployeeCreateDto;
import com.example.UserDept.web.dto.employee.EmployeeEmailDto;
import com.example.UserDept.web.dto.employee.EmployeeResponseDto;
import com.example.UserDept.entities.Employee;
import com.example.UserDept.services.EmployeeService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/employees")
public class EmployeeController {
	@Autowired
	private EmployeeService service;
	
	@GetMapping
	public ResponseEntity<List<EmployeeResponseDto>> findAll() {
		List<EmployeeResponseDto> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<EmployeeResponseDto> findbyId(@PathVariable Long id){
		EmployeeResponseDto dto = service.findById(id);
		return ResponseEntity.ok().body(dto);
	}
	@PostMapping
	public ResponseEntity<EmployeeResponseDto> insert(@Valid @RequestBody EmployeeCreateDto dto) {
        EmployeeResponseDto emp = service.insert(dto);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(emp.getId())
                .toUri();
        
        return ResponseEntity.created(uri).body(emp);
    }
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id){
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@PatchMapping(value = "/{id}")
	public ResponseEntity<Employee> updateEmail(@PathVariable long id, @Valid @RequestBody EmployeeEmailDto dto){
		service.update(id, dto);
		return ResponseEntity.noContent().build();
	}
}
