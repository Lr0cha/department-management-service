package com.example.UserDept.web.controllers;

import com.example.UserDept.config.security.CustomAccessDeniedHandler;
import com.example.UserDept.config.security.CustomAuthenticationEntryPoint;
import com.example.UserDept.entities.enums.Role;
import com.example.UserDept.web.dto.employee.*;
import com.example.UserDept.web.dto.mapper.EmployeeMapper;
import com.example.UserDept.web.exceptions.StandardError;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Employee", description = "Todas as operações relativas a cadastro, leitura, atualização e exclusão de um funcionário/admin")
public class EmployeeController {
	@Autowired
	private EmployeeService service;

	@Operation(summary="Obter todos os funcionários", description = "Obter funcionários com opções de parâmetros", responses = {
			@ApiResponse(responseCode = "200", description = "Recurso encontrado com sucesso",
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = EmployeeResponseDto.class))),
			@ApiResponse(responseCode = "401", description = "Recurso não autorizado",
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomAuthenticationEntryPoint.class))),
			@ApiResponse(responseCode = "403", description = "Recurso proibido",
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomAccessDeniedHandler.class))),
	})
	@GetMapping
	public ResponseEntity<Page<EmployeeResponseDto>> findAll(@RequestParam(required = false) String name,
															 @RequestParam(required = false) String departmentName,
															 Pageable pageable) {
		Page<Employee> employees = service.findAll(name, departmentName, pageable);
		return ResponseEntity.ok().body(EmployeeMapper.toListDto(employees));
	}

	@Operation(summary="Obter funcionário pelo id",responses = {
			@ApiResponse(responseCode = "200", description = "Recurso encontrado com sucesso",
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = EmployeeResponseDto.class))),
			@ApiResponse(responseCode = "401", description = "Recurso não autorizado",
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomAuthenticationEntryPoint.class))),
			@ApiResponse(responseCode = "403", description = "Recurso proibido",
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomAccessDeniedHandler.class))),
			@ApiResponse(responseCode = "404", description = "Recurso não encontrado",
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = StandardError.class)))
	})
	@PreAuthorize("hasRole('ADMIN') or #id == authentication.principal.id")
	@GetMapping(value = "/{id}")
	public ResponseEntity<EmployeeResponseDto> findById(@PathVariable Long id){
		Employee employee = service.findById(id);
		return ResponseEntity.ok().body(EmployeeMapper.toDto(employee));
	}

	@Operation(summary="Criar funcionário",responses = {
			@ApiResponse(responseCode = "201", description = "Recurso criado com sucesso",
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = EmployeeResponseDto.class))),
			@ApiResponse(responseCode = "401", description = "Recurso não autorizado",
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomAuthenticationEntryPoint.class))),
			@ApiResponse(responseCode = "403", description = "Recurso proibido",
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomAccessDeniedHandler.class))),
			@ApiResponse(responseCode = "404", description = "Departamento relacionado não encontrado",
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = StandardError.class))),
			@ApiResponse(responseCode = "409", description = "Recurso com conflito",
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = StandardError.class))),
			@ApiResponse(responseCode = "422", description = "Recurso não processado por dados de entrada inválidos",
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = StandardError.class))),
	})
	@PostMapping
	public ResponseEntity<EmployeeResponseDto> insert(@Valid @RequestBody EmployeeCreateDto dto) {
        Employee emp = service.insert(EmployeeMapper.toEmployee(dto));

        return ResponseEntity.status(HttpStatus.CREATED).body(EmployeeMapper.toDto(emp));
    }

	@Operation(summary="Apagar funcionário pelo id",responses = {
			@ApiResponse(responseCode = "204", description = "Recurso excluido com sucesso",
					content = @Content(mediaType = "application/json", schema = @Schema())),
			@ApiResponse(responseCode = "401", description = "Recurso não autorizado",
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomAuthenticationEntryPoint.class))),
			@ApiResponse(responseCode = "403", description = "Recurso proibido",
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomAccessDeniedHandler.class))),
			@ApiResponse(responseCode = "404", description = "Recurso não encontrado",
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = StandardError.class))),
	})
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id,
									   @AuthenticationPrincipal Employee currentUser){
		if (!canUpdateOrDelete(id, currentUser)) {
			throw new AuthorizationDeniedException("Do not have access.");
		}

		service.delete(id);
		return ResponseEntity.noContent().build();
	}

	@Operation(summary="Atualizar funcionário",description = "recurso para atualizar principais campos do funcionário",responses = {
			@ApiResponse(responseCode = "204", description = "Recurso criado com sucesso",
					content = @Content(mediaType = "application/json", schema = @Schema())),
			@ApiResponse(responseCode = "401", description = "Recurso não autorizado",
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomAuthenticationEntryPoint.class))),
			@ApiResponse(responseCode = "403", description = "Recurso proibido",
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomAccessDeniedHandler.class))),
			@ApiResponse(responseCode = "409", description = "Recurso com conflito",
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = StandardError.class))),
	})
	@PreAuthorize("hasRole('ADMIN') or #id == authentication.principal.id")
	@PatchMapping(value = "/{id}")
	public ResponseEntity<Void> update(@PathVariable long id,
											@Valid @RequestBody EmployeeUpdateDto dto,
											@AuthenticationPrincipal Employee currentUser) {
		if (!canUpdateOrDelete(id, currentUser)) {
			throw new AuthorizationDeniedException("Do not have access.");
		}

		service.update(id, dto);
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
