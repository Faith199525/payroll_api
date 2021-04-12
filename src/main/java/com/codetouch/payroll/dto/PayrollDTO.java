package com.codetouch.payroll.dto;

import java.time.LocalDate;

public class PayrollDTO {

	private String cycle;
	private String annualGrossAmount;
	private String annualDeduction;
	private LocalDate paymentDate;
	//private LocalTime paymentTime;
	private Long organization;
	private Long approvalLevel;
	
	public String getCycle() {
		return cycle;
	}
	public void setCycle(String cycle) {
		this.cycle = cycle;
	}
	public String getAnnualGrossAmount() {
		return annualGrossAmount;
	}
	public void setAnnualGrossAmount(String annualGrossAmount) {
		this.annualGrossAmount = annualGrossAmount;
	}
	public String getAnnualDeduction() {
		return annualDeduction;
	}
	public void setAnnualDeduction(String annualDeduction) {
		this.annualDeduction = annualDeduction;
	}
	public LocalDate getPaymentDate() {
		return paymentDate;
	}
	public void setPaymentDate(LocalDate paymentDate) {
		this.paymentDate = paymentDate;
	}
	public Long getOrganization() {
		return organization;
	}
	public void setOrganization(Long organization) {
		this.organization = organization;
	}
	public Long getApprovalLevel() {
		return approvalLevel;
	}
	public void setApprovalLevel(Long approvalLevel) {
		this.approvalLevel = approvalLevel;
	}
	
	
}
