package com.example.UserDept.web.openApi;

import com.example.UserDept.config.security.CustomAccessDeniedHandler;
import com.example.UserDept.config.security.CustomAuthenticationEntryPoint;
import com.example.UserDept.entities.employee.Employee;
import com.example.UserDept.web.dto.employee.EmployeeCreateDto;
import com.example.UserDept.web.dto.employee.EmployeeResponseDto;
import com.example.UserDept.web.dto.employee.EmployeeUpdateDto;
import com.example.UserDept.web.exceptions.StandardError;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

@Tag(name = "Employee", description = "Todas as operações relativas a cadastro, leitura, atualização e exclusão de um funcionário/admin")
public interface EmployeeControllerOpenApi {

    @Operation(summary="Obter todos os funcionários", description = "Obter funcionários com opções de parâmetros", responses = {
            @ApiResponse(responseCode = "200", description = "Recurso encontrado com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = EmployeeResponseDto.class))),
            @ApiResponse(responseCode = "401", description = "Recurso não autorizado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomAuthenticationEntryPoint.class))),
            @ApiResponse(responseCode = "403", description = "Recurso proibido",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomAccessDeniedHandler.class))),
    })
    ResponseEntity<Page<EmployeeResponseDto>> findAll(String name, String departmentName, Pageable pageable);

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
    ResponseEntity<EmployeeResponseDto> findById(Long id);

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
    ResponseEntity<EmployeeResponseDto> insert(EmployeeCreateDto dto);

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
    ResponseEntity<Void> delete(Long id, Employee currentUser);

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
    ResponseEntity<Void> update(long id, EmployeeUpdateDto dto, Employee currentUser);

}
