package com.example.UserDept.web.controllers;

import com.example.UserDept.web.dto.address.UpdateAddressDto;
import com.example.UserDept.web.dto.employee.*;
import com.example.UserDept.web.dto.mapper.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.UserDept.entities.employee.Employee;
import com.example.UserDept.services.EmployeeService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/employees")
public class EmployeeController {
	@Autowired
	private EmployeeService service;
	
	@GetMapping
	public ResponseEntity<Page<EmployeeResponseDto>> findAll(@RequestParam(required = false) String name,
															 @RequestParam(required = false) String departmentName,
															 Pageable pageable) {
		Page<Employee> employees = service.findAll(name, departmentName, pageable);
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
	public ResponseEntity<Void> updatePhone(@PathVariable long id, @Valid @RequestBody EmployeePhoneDto dto){
		service.updatePhone(id, dto.getPhone());
		return ResponseEntity.noContent().build();
	}

	@PatchMapping(value = "/address/{id}")
	public ResponseEntity<Void> updateAddress(@PathVariable long id, @Valid @RequestBody UpdateAddressDto dto){
		service.updateAddress(id, dto.getZipCode(), dto.getHouseNumber());
		return ResponseEntity.noContent().build();
	}

	@PatchMapping(value = "/department/{id}")
	public ResponseEntity<Void> updateDepartment(@PathVariable long id, @Valid @RequestBody EmployeeDepartmentDto dto){
		service.updateDepartment(id, dto.getDepartmentId());
		return ResponseEntity.noContent().build();
	}
}
