package com.example.UserDept.web.controllers;

import com.example.UserDept.entities.employee.Employee;
import com.example.UserDept.services.TokenService;
import com.example.UserDept.web.dto.auth.LoginRequestDto;
import com.example.UserDept.web.dto.auth.LoginResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto dto){
        var emailPassword = new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword());
        var auth = this.authenticationManager.authenticate(emailPassword);

        var token = tokenService.generateToken((Employee) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDto(token));
    }
}
