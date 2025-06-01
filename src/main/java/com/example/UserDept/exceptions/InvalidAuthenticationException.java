package com.example.UserDept.exceptions;

public class InvalidAuthenticationException extends RuntimeException {
    public InvalidAuthenticationException(String message) {
        super(message);
    }
}
