package com.example.UserDept.exceptions;

import java.io.Serial;

public class ResourceNotFoundException extends RuntimeException {
	public ResourceNotFoundException(String msg) {
		super(msg);
	}
}
