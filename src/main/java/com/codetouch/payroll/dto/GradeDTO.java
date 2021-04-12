package com.codetouch.payroll.dto;

public class GradeDTO {

	private Long organization;
	private String name;
	private String number;
	private String annualGrossEarning;
	private String annualTax;
	private String annualPension;
	private String annualHMO;
	
	public Long getOrganization() {
		return organization;
	}
	public void setOrganization(Long organization) {
		this.organization = organization;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getAnnualGrossEarning() {
		return annualGrossEarning;
	}
	public void setAnnualGrossEarning(String annualGrossEarning) {
		this.annualGrossEarning = annualGrossEarning;
	}
	public String getAnnualTax() {
		return annualTax;
	}
	public void setAnnualTax(String annualTax) {
		this.annualTax = annualTax;
	}
	public String getAnnualPension() {
		return annualPension;
	}
	public void setAnnualPension(String annualPension) {
		this.annualPension = annualPension;
	}
	public String getAnnualHMO() {
		return annualHMO;
	}
	public void setAnnualHMO(String annualHMO) {
		this.annualHMO = annualHMO;
	}
	
	
	
}
