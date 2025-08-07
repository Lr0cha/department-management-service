package com.example.UserDept.web.openApi;

import com.example.UserDept.web.dto.auth.LoginRequestDto;
import com.example.UserDept.web.dto.auth.LoginResponseDto;
import com.example.UserDept.web.exceptions.StandardError;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Login", description = "Operação necessária para autenticação via token")
public interface LoginControllerOpenApi {

    @Operation(summary="Autenticar o usuário",description = "Recurso para autenticar um usuário",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Recurso encontrado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = LoginResponseDto.class))),
                    @ApiResponse(responseCode = "401", description = "Recurso não autorizado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = StandardError.class)))
            })
    ResponseEntity<LoginResponseDto> login(LoginRequestDto dto);
}
