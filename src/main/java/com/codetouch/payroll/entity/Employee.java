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
@Table(name="core_employee")
public class Employee {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="emp_generator")
	@SequenceGenerator(name="emp_generator", sequenceName="emp_gen", allocationSize = 15,initialValue = 1)
	@Column(name="id")
    public long id;
	
	@Column(name="first_name", length =  30, nullable = false)
	private String firstName;
	
	@Column(name="middle_name", length =  30, nullable = true)
	private String middleName;
	
	@Column(name="last_name", length =  30, nullable = false)
	private String lastName;
	
	@Column(name="phone_number", length =  20, nullable = false)
	private String phoneNumber;
	
	@Column(name="email", length =  30, nullable = false)
	private String email;
	
	@Column(name="bank", length =  20, nullable = false)
	private String bank;
	
	@Column(name="account_name", length =  50, nullable = false)
	private String accountName;
	
	@Column(name="account_number", length =  20, nullable = false)
	private String accountNumber;
	
	@ManyToOne
	@JoinColumn(name="organization", nullable = false)
	private Organization organization;
	
	@ManyToOne
	@JoinColumn(name="grade", nullable = true)
	private Grade grade;
	
	/*@Column(name="annual_Gross_salary", length =  20, nullable = true)
	private String annualGrossSalary;*/
	
	@Column(name="annual_HMO", length =  20, nullable = true)
	private String annualHMO;
	
	@Column(name="annual_pension", length =  20, nullable = true)
	private String annualPension;
	
	@Column(name="annual_tax", length =  20, nullable = true)
	private String annualTax;
	
	@Column(name="annual_loan", length =  20, nullable = true)
	private String annualLoan;

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
	
	
	public Employee() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
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

	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	public Grade getGrade() {
		return grade;
	}

	public void setGrade(Grade grade) {
		this.grade = grade;
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

	public void setAnnualTax(String annualTaxes) {
		this.annualTax = annualTaxes;
	}

	public String getAnnualLoan() {
		return annualLoan;
	}

	public void setAnnualLoan(String annualLoan) {
		this.annualLoan = annualLoan;
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

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	
	
}
