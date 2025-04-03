package com.example.UserDept.exceptions;

public class DatabaseConflictException extends RuntimeException {
	public DatabaseConflictException(String msg) {
		super(msg);
	}
}
