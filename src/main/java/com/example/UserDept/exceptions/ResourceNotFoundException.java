package com.example.UserDept.exceptions;

import java.io.Serial;

public class ResourceNotFoundException extends RuntimeException {
	public ResourceNotFoundException(Object id) {
		super("Recurso não encontrado com id: " + id);
	}
}
