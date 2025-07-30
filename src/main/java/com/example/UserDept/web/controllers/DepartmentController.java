package com.example.UserDept.web.controllers;

import com.example.UserDept.config.security.CustomAccessDeniedHandler;
import com.example.UserDept.config.security.CustomAuthenticationEntryPoint;
import com.example.UserDept.web.dto.department.DepartmentDto;
import com.example.UserDept.web.dto.mapper.DepartmentMapper;
import com.example.UserDept.web.exceptions.StandardError;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

@PreAuthorize("hasRole('ADMIN')")
@RestController
@RequestMapping(value = "/departments")
@Tag(name = "Departament", description = "Todas as operações relativas a cadastro, leitura e exclusão de um departamento")
public class DepartmentController {
	@Autowired
	private DepartmentService service;


	@Operation(summary="Obter todos os departamentos", responses = {
			@ApiResponse(responseCode = "200", description = "Recurso encontrado com sucesso",
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = DepartmentDto.class))),
			@ApiResponse(responseCode = "401", description = "Recurso não autorizado",
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomAuthenticationEntryPoint.class))),
			@ApiResponse(responseCode = "403", description = "Recurso proibido",
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomAccessDeniedHandler.class))),
	})
	@GetMapping
	public ResponseEntity<Page<DepartmentDto>> findAll(Pageable pageable){
		Page<Department> departments = service.findAll(pageable);
		return ResponseEntity.ok().body(DepartmentMapper.toDtos(departments));
	}

	@Operation(summary="Obter departamento pelo id",responses = {
			@ApiResponse(responseCode = "200", description = "Recurso encontrado com sucesso",
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = DepartmentDto.class))),
			@ApiResponse(responseCode = "401", description = "Recurso não autorizado",
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomAuthenticationEntryPoint.class))),
			@ApiResponse(responseCode = "403", description = "Recurso proibido",
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomAccessDeniedHandler.class))),
			@ApiResponse(responseCode = "404", description = "Recurso não encontrado",
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = StandardError.class)))
	})
	@GetMapping(value = "/{id}")
	public ResponseEntity<DepartmentDto> findById(@PathVariable Long id){
		Department dept = service.findById(id);
		return ResponseEntity.ok().body(DepartmentMapper.toDto(dept));
	}

	@Operation(summary="Criar departamento",responses = {
			@ApiResponse(responseCode = "201", description = "Recurso criado com sucesso",
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = DepartmentDto.class))),
			@ApiResponse(responseCode = "401", description = "Recurso não autorizado",
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomAuthenticationEntryPoint.class))),
			@ApiResponse(responseCode = "403", description = "Recurso proibido",
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomAccessDeniedHandler.class))),
			@ApiResponse(responseCode = "409", description = "Recurso com conflito",
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = StandardError.class))),
			@ApiResponse(responseCode = "422", description = "Recurso não processado por dados de entrada inválidos",
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = StandardError.class))),
	})
	@PostMapping
	public ResponseEntity<DepartmentDto> insert(@Valid @RequestBody DepartmentDto dto){
		Department dept = service.insert(DepartmentMapper.toDepartment(dto));
		return ResponseEntity.status(HttpStatus.CREATED).body(DepartmentMapper.toDto(dept));
	}

	@Operation(summary="Apagar departamento pelo id",responses = {
			@ApiResponse(responseCode = "204", description = "Recurso excluido com sucesso",
					content = @Content(mediaType = "application/json", schema = @Schema())),
			@ApiResponse(responseCode = "401", description = "Recurso não autorizado",
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomAuthenticationEntryPoint.class))),
			@ApiResponse(responseCode = "403", description = "Recurso proibido",
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomAccessDeniedHandler.class))),
			@ApiResponse(responseCode = "404", description = "Recurso não encontrado",
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = StandardError.class))),
			@ApiResponse(responseCode = "409", description = "Recurso com conflito",
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = StandardError.class))),
	})
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id){
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
}
