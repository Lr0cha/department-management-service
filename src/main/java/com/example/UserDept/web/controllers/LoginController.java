package com.example.UserDept.web.controllers;

import com.example.UserDept.services.AuthService;
import com.example.UserDept.web.dto.auth.LoginRequestDto;
import com.example.UserDept.web.dto.auth.LoginResponseDto;
import com.example.UserDept.web.openApi.LoginControllerOpenApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController implements LoginControllerOpenApi {

    @Autowired
    private AuthService authService;

    @PostMapping
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto dto) {
        String token = authService.authenticateAndGenerateToken(dto.getEmail(), dto.getPassword());

        return ResponseEntity.ok(new LoginResponseDto(token));
    }
}
