package com.synerzip.ExpenseCalculation;

import org.springframework.stereotype.Component;

@Component
public class LoginUser {
	private String email_id;
	private String password;

	public LoginUser() {
	}

	public LoginUser(String email_id, String password) {
		this.email_id = email_id;
		this.password = password;
	}

	public String getEmail_id() {
		return email_id;
	}

	public void setEmail_id(String email_id) {
		this.email_id = email_id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}