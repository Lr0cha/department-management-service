package com.example.UserDept.config.security;

import com.example.UserDept.exceptions.InvalidTokenException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

/**
 * CustomAuthenticationEntryPoint is triggered whenever an unauthenticated user
 * attempts to access a protected resource. Instead of handling the exception directly,
 * it delegates the error to Spring's HandlerExceptionResolver so that it can be
 * processed by the application's global exception handler (@ControllerAdvice).
 */
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final HandlerExceptionResolver resolver;

    public CustomAuthenticationEntryPoint(@Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver) {
        this.resolver = resolver;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException {
        resolver.resolveException(request, response, null, new InvalidTokenException("Invalid or empty token."));
    }
}

