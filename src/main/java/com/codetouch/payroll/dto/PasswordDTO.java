package com.codetouch.payroll.dto;

public class PasswordDTO {

	private String password;
	private String passwordRepeat;
	private String token;
	private Long resetId;
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPasswordRepeat() {
		return passwordRepeat;
	}
	public void setPasswordRepeat(String passwordRepeat) {
		this.passwordRepeat = passwordRepeat;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Long getResetId() {
		return resetId;
	}
	public void setResetId(Long resetId) {
		this.resetId = resetId;
	}
	
	
	

	
	
}
