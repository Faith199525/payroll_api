package com.codetouch.payroll.dto;

import java.time.LocalDate;

public class OrganizationDTO {

	private String shortName;
	private String fullName;
	private String registrationNumber;
	private String vatNumber;
	private String state;
	private String city;
	private String address;
	private LocalDate dateOfIncoporation;
	
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getRegistrationNumber() {
		return registrationNumber;
	}
	public void setRegistrationNumber(String registrationNumber) {
		this.registrationNumber = registrationNumber;
	}
	public String getVatNumber() {
		return vatNumber;
	}
	public void setVatNumber(String vatNumber) {
		this.vatNumber = vatNumber;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public LocalDate getDateOfIncoporation() {
		return dateOfIncoporation;
	}
	public void setDateOfIncoporation(LocalDate dateOfIncoporation) {
		this.dateOfIncoporation = dateOfIncoporation;
	}
	
	
	
}
