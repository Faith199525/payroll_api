package com.codetouch.payroll.entity;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="core_payroll")
public class Payroll {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="pay_generator")
	@SequenceGenerator(name="pay_generator", sequenceName="pay_gen", allocationSize = 5,initialValue = 1)
	@Column(name="id")
    public long id;
	
	@Column(name="cycle", length =  20, nullable = false)
	private String cycle;
	
	@Column(name="annual_gross_amount", length =  200, nullable = false)
	private String annualGrossAmount;
	
	@Column(name="annual_deduction", length =  200, nullable = false)
	private String annualDeduction;
	
	@Basic
	@Column(name="payment_date", nullable = false)
	private LocalDate paymentDate;
	
	/*@Basic
	@Column(name="payment_time", nullable = false)
	private LocalTime paymentTime;*/
	
	@ManyToOne
	@JoinColumn(name="organization", nullable = false)
	private Organization organization;
	
	@Column(name="approval_level", length =  10, nullable = false)
	private Long approvalLevel;
	
	@Basic
	@Column(name="create_date", nullable = false)
	private LocalDate createdDate;

	@Basic
	@Column(name="create_time", nullable = false)
	private LocalTime createTime;
	
	@ManyToOne
	@JoinColumn(name="created_by", nullable = false)
	private User createdBy;
	
	private boolean deleted;
	
	@Basic
	@Column(name="deleted_date", nullable =true)
	private LocalDate deletedDate;

	@Basic
	@Column(name="deleted_time", nullable = true)
	private LocalTime deletedTime;
	
	@ManyToOne
	@JoinColumn(name="deleted_by", nullable = true)
	private User deletedBy;

	public Payroll() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

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

	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}
	public Long getApprovalLevel() {
		return approvalLevel;
	}

	public void setApprovalLevel(Long approvalLevel) {
		this.approvalLevel = approvalLevel;
	}

	public LocalDate getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDate createdDate) {
		this.createdDate = createdDate;
	}

	public LocalTime getCreateTime() {
		return createTime;
	}

	public void setCreateTime(LocalTime createTime) {
		this.createTime = createTime;
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public LocalDate getDeletedDate() {
		return deletedDate;
	}

	public void setDeletedDate(LocalDate deletedDate) {
		this.deletedDate = deletedDate;
	}

	public LocalTime getDeletedTime() {
		return deletedTime;
	}

	public void setDeletedTime(LocalTime deletedTime) {
		this.deletedTime = deletedTime;
	}

	public User getDeletedBy() {
		return deletedBy;
	}

	public void setDeletedBy(User deletedBy) {
		this.deletedBy = deletedBy;
	}
	
	
	}
