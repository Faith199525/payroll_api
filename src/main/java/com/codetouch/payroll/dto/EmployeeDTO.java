package com.codetouch.payroll.dto;

public class EmployeeDTO {

	private String firstName;
	private String middleName;
	private String lastName;
	private String phoneNumber;
	private String email;
	private String bank;
	private String accountName;
	private String accountNumber;
	private String annualHMO;
	private String annualPension;
	private String annualTax;
	private String annualLoan;
	private Long organization;
	private Long grade;
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public Long getOrganization() {
		return organization;
	}
	public void setOrganization(Long organization) {
		this.organization = organization;
	}
	public Long getGrade() {
		return grade;
	}
	public void setGrade(Long grade) {
		this.grade = grade;
	}
	public String getMiddleName() {
		return middleName;
	}
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getBank() {
		return bank;
	}
	public void setBank(String bank) {
		this.bank = bank;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public String getAnnualHMO() {
		return annualHMO;
	}
	public void setAnnualHMO(String annualHMO) {
		this.annualHMO = annualHMO;
	}
	public String getAnnualPension() {
		return annualPension;
	}
	public void setAnnualPension(String annualPension) {
		this.annualPension = annualPension;
	}
	public String getAnnualTax() {
		return annualTax;
	}
	public void setAnnualTax(String annualTax) {
		this.annualTax = annualTax;
	}
	public String getAnnualLoan() {
		return annualLoan;
	}
	public void setAnnualLoan(String annualLoan) {
		this.annualLoan = annualLoan;
	}
	
	
}
