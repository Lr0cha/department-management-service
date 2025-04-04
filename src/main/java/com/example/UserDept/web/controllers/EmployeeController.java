package com.example.UserDept.web.controllers;

import com.example.UserDept.web.dto.employee.EmployeePhoneDto;
import com.example.UserDept.web.dto.mapper.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
	public ResponseEntity<Page<EmployeeResponseDto>> findAll(Pageable pageable) {
		Page<Employee> employees = service.findAll(pageable);
		return ResponseEntity.ok().body(EmployeeMapper.toListDto(employees));
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<EmployeeResponseDto> findById(@PathVariable Long id){
		Employee employee = service.findById(id);
		return ResponseEntity.ok().body(EmployeeMapper.toDto(employee));
	}

	@PostMapping
	public ResponseEntity<EmployeeResponseDto> insert(@Valid @RequestBody EmployeeCreateDto dto) {
        Employee emp = service.insert(EmployeeMapper.toEmployee(dto));

        return ResponseEntity.status(HttpStatus.CREATED).body(EmployeeMapper.toDto(emp));
    }
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id){
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@PatchMapping(value = "/email/{id}")
	public ResponseEntity<Void> updateEmail(@PathVariable long id, @Valid @RequestBody EmployeeEmailDto dto){
		service.updateEmail(id, dto.getCurrentEmail(),dto.getNewEmail());
		return ResponseEntity.noContent().build();
	}

	@PatchMapping(value = "/phone/{id}")
	public ResponseEntity<Void> updatePhoneNumber(@PathVariable long id, @Valid @RequestBody EmployeePhoneDto phoneDto){
		service.updatePhone(id, phoneDto.getPhone());
		return ResponseEntity.noContent().build();
	}
}
