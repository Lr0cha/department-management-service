package com.example.UserDept.services;


import com.example.UserDept.entities.employee.Employee;
import com.example.UserDept.exceptions.InvalidAuthenticationException;
import com.example.UserDept.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements UserDetailsService {

    @Autowired
    @Lazy
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private EmployeeRepository empRepository;

    @Override
    public UserDetails loadUserByUsername(String email) {
        return empRepository.findByEmail(email);
    }

    public String authenticateAndGenerateToken(String email, String password) {
        try {
            var authenticationToken = new UsernamePasswordAuthenticationToken(email, password);
            var auth = authenticationManager.authenticate(authenticationToken);

            return tokenService.generateToken((Employee) auth.getPrincipal());
        } catch (AuthenticationException e) {
            throw new InvalidAuthenticationException("Invalid email and/or password.");
        }
    }
}
