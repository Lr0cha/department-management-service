package com.example.UserDept.web.controllers;

import com.example.UserDept.entities.employee.Employee;
import com.example.UserDept.exceptions.InvalidAuthenticationException;
import com.example.UserDept.services.AuthService;
import com.example.UserDept.services.TokenService;
import com.example.UserDept.web.dto.auth.LoginRequestDto;
import com.example.UserDept.web.dto.auth.LoginResponseDto;
import com.example.UserDept.web.exceptions.StandardError;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
@Tag(name = "Login", description = "Operação necessária para autenticação via token")
public class LoginController {

    @Autowired
    private AuthService authService;

    @Operation(summary="Autenticar o usuário",description = "Recurso para autenticar um usuário",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Recurso encontrado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = LoginResponseDto.class))),
                    @ApiResponse(responseCode = "401", description = "Recurso não autorizado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = StandardError.class)))
            })
    @PostMapping
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto dto) {
        String token = authService.authenticateAndGenerateToken(dto.getEmail(), dto.getPassword());

        return ResponseEntity.ok(new LoginResponseDto(token));
    }
}
