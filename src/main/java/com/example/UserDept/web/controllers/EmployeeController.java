package com.example.UserDept.web.controllers;

import com.example.UserDept.entities.enums.Role;
import com.example.UserDept.web.dto.address.UpdateAddressDto;
import com.example.UserDept.web.dto.employee.*;
import com.example.UserDept.web.dto.mapper.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import com.example.UserDept.entities.employee.Employee;
import com.example.UserDept.services.EmployeeService;

import jakarta.validation.Valid;

@PreAuthorize("hasRole('ADMIN')")
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

	@PreAuthorize("hasRole('ADMIN') or #id == authentication.principal.id")
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
	public ResponseEntity<Void> delete(@PathVariable Long id,
									   @AuthenticationPrincipal Employee currentUser){
		if (!canUpdateOrDelete(id, currentUser)) {
			throw new AuthorizationDeniedException("Do not have access.");
		}

		service.delete(id);
		return ResponseEntity.noContent().build();
	}

	@PatchMapping(value = "/email/{id}")
	public ResponseEntity<Void> updateEmail(@PathVariable long id,
											@Valid @RequestBody EmployeeEmailDto dto,
											@AuthenticationPrincipal Employee currentUser) {
		if (!canUpdateOrDelete(id, currentUser)) {
			throw new AuthorizationDeniedException("Do not have access.");
		}

		service.updateEmail(id, dto.getCurrentEmail(), dto.getNewEmail());
		return ResponseEntity.noContent().build();
	}

	@PatchMapping(value = "/phone/{id}")
	public ResponseEntity<Void> updatePhone(@PathVariable long id,
											@Valid @RequestBody EmployeePhoneDto dto,
											@AuthenticationPrincipal Employee currentUser){

		if (!canUpdateOrDelete(id, currentUser)) {
			throw new AuthorizationDeniedException("Do not have access.");
		}

		service.updatePhone(id, dto.getPhone());
		return ResponseEntity.noContent().build();
	}

	@PatchMapping(value = "/address/{id}")
	public ResponseEntity<Void> updateAddress(@PathVariable long id,
											  @Valid @RequestBody UpdateAddressDto dto,
											  @AuthenticationPrincipal Employee currentUser){


		if (!canUpdateOrDelete(id, currentUser)) {
			throw new AuthorizationDeniedException("Do not have access.");
		}

		service.updateAddress(id, dto.getZipCode(), dto.getHouseNumber());
		return ResponseEntity.noContent().build();
	}

	@PatchMapping(value = "/department/{id}")
	public ResponseEntity<Void> updateDepartment(@PathVariable long id,
												 @Valid @RequestBody EmployeeDepartmentDto dto,
												 @AuthenticationPrincipal Employee currentUser){

		if (!canUpdateOrDelete(id, currentUser)) {
			throw new AuthorizationDeniedException("Do not have access.");
		}

		service.updateDepartment(id, dto.getDepartmentId());
		return ResponseEntity.noContent().build();
	}

	/**
	 * Checks whether the authenticated user has permission to update or delete the target employee's data.
	 * <p>
	 * The authorization rules are as follows:
	 * <ul>
	 *   <li>The user can update or delete their own data.</li>
	 *   <li>A user with the ADMIN role can update or delete data of users with the COMMON role.</li>
	 *   <li>An ADMIN cannot update or delete data of another ADMIN (unless it's their own).</li>
	 * </ul>
	 *
	 * @param targetId     the ID of the employee whose data is being updated or deleted
	 * @param currentUser  the currently authenticated user
	 * @return true if the update or delete is allowed; false otherwise
	 */
	private boolean canUpdateOrDelete(Long targetId, Employee currentUser) {
		Employee targetUser = service.findById(targetId);

		boolean isSelf = currentUser.getId().equals(targetUser.getId());
		boolean isAdmin = currentUser.getRole() == Role.ADMIN;
		boolean targetIsCommon = targetUser.getRole() == Role.COMMON;

		return isSelf || (isAdmin && targetIsCommon);
	}
}
