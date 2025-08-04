package com.example.UserDept.services;

import com.example.UserDept.entities.employee.Employee;
import com.example.UserDept.exceptions.InvalidAuthenticationException;
import com.example.UserDept.repositories.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceUnitTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private TokenService tokenService;

    @Mock
    private EmployeeRepository empRepository;

    @Mock
    private Employee employee;

    @InjectMocks
    private AuthService authService;

    @Mock
    private Authentication authentication;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private final String email = "example@email.com";
    private final String password = "test123";

    @Test
    void testAuthenticateAndGenerateToken_returnToken_whenSuccess() {
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);

        when(authentication.getPrincipal()).thenReturn(employee);
        String token = "token";
        when(tokenService.generateToken(employee)).thenReturn(token);

        String generatedToken = authService.authenticateAndGenerateToken(email, password);

        assertEquals(token, generatedToken);

        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(tokenService, times(1)).generateToken(employee);
    }

    @Test
    void testAuthenticateAndGenerateToken_throwInvalidAuthenticationException_whenInvalidCredentials() {
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new AuthenticationException("Invalid credentials") {});

        Exception exception = assertThrows(InvalidAuthenticationException.class, () -> {
            authService.authenticateAndGenerateToken(email, password);
        });

        assertEquals("Invalid email and/or password.", exception.getMessage());

        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }

    @Test
    void returnUserDetails_WhenEmailExists() {
        Employee employee = new Employee();
        employee.setEmail(email);

        when(empRepository.findByEmail(email)).thenReturn(employee);

        UserDetails userDetails = authService.loadUserByUsername(email);

        assertNotNull(userDetails);
        assertEquals(email, userDetails.getUsername());
    }
}
