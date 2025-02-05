package com.example.UserDept.dto;

public class EmployeeEmailDto {
	private String currentEmail;
	private String newEmail;
	private String confirmEmail;
	
	public EmployeeEmailDto() {
		
	}
	public String getCurrentEmail() {
		return currentEmail;
	}
	public void setCurrentEmail(String currentEmail) {
		this.currentEmail = currentEmail;
	}
	public String getNewEmail() {
		return newEmail;
	}
	public void setNewEmail(String newEmail) {
		this.newEmail = newEmail;
	}
	public String getConfirmEmail() {
		return confirmEmail;
	}
	public void setConfirmEmail(String confirmEmail) {
		this.confirmEmail = confirmEmail;
	}
	
	
	
}
