package com.example.UserDept.web.openApi;

import com.example.UserDept.config.security.CustomAccessDeniedHandler;
import com.example.UserDept.config.security.CustomAuthenticationEntryPoint;
import com.example.UserDept.web.dto.department.DepartmentDto;
import com.example.UserDept.web.exceptions.StandardError;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

@Tag(name = "Departament", description = "Todas as operações relativas a cadastro, leitura e exclusão de um departamento")
public interface DepartmentControllerOpenAPi {

    @Operation(summary="Obter todos os departamentos", responses = {
            @ApiResponse(responseCode = "200", description = "Recurso encontrado com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = DepartmentDto.class))),
            @ApiResponse(responseCode = "401", description = "Recurso não autorizado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomAuthenticationEntryPoint.class))),
            @ApiResponse(responseCode = "403", description = "Recurso proibido",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomAccessDeniedHandler.class))),
    })
    ResponseEntity<Page<DepartmentDto>> findAll(Pageable pageable);

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
    ResponseEntity<DepartmentDto> findById(Long id);

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
    ResponseEntity<DepartmentDto> insert(DepartmentDto dto);

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
    ResponseEntity<Void> delete(Long id);


}


