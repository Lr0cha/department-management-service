package com.example.UserDept.config.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

/**
 * CustomAccessDeniedHandler is invoked when an authenticated user attempts to access a resource
 * for which they do not have sufficient permissions (i.e., forbidden access).
 * Instead of writing a raw response, it delegates the exception to Spring's
 * HandlerExceptionResolver so it can be processed by a centralized
 * exception handler (@ControllerAdvice).
 */
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private final HandlerExceptionResolver resolver;

    public CustomAccessDeniedHandler(@Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver) {
        this.resolver = resolver;
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException)
            throws IOException, ServletException {

        resolver.resolveException(request, response, null,
                new AuthorizationDeniedException("You do not have permission to access this resource."));
    }
}
