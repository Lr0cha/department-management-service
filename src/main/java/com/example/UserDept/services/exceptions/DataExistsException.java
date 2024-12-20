package com.example.UserDept.services.exceptions;

public class DataExistsException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	public DataExistsException(String msg) {
		super(msg);	
	}

}
