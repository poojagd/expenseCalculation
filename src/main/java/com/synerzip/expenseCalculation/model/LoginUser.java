package com.synerzip.expenseCalculation.model;

import org.springframework.stereotype.Component;

@Component
public class LoginUser {
	private String emailId;
	private String password;

	public LoginUser() {
	}

	public LoginUser(String emailId, String password) {
		this.emailId = emailId;
		this.password = password;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
