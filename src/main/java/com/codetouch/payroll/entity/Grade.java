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
@Table(name="core_grade")
public class Grade {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="emp_grade")
	@SequenceGenerator(name="emp_grade", sequenceName="emp_grade", allocationSize = 10,initialValue = 1)
	@Column(name="id")
    public long id;
	
	@Column(name="name", length =  30, nullable = false)
	private String name;
	
	@Column(name="number", length =  10, nullable = false)
	private String number;
	
	@Column(name="annual_gross_earning", length =  30, nullable = false)
	private String annualGrossEarning;
	
	@Column(name="annual_tax", length =  30, nullable = true)
	private String annualTax;
	
	@Column(name="annual_pension", length =  30, nullable = true)
	private String annualPension;
	
	@Column(name="annual_HMO", length =  30, nullable = true)
	private String annualHMO;

	@ManyToOne
	@JoinColumn(name="organization", nullable = false)
	private Organization organization;
	
	@ManyToOne
	@JoinColumn(name="created_By", nullable = false)
	private User createdBy;
	
	@Basic
	@Column(name="created_date", nullable = false)
	private LocalDate createdDate;
	
	@Basic
	@Column(name="time_created", nullable = false)
	private LocalTime timeCreated;
	
	@Basic
	@Column(name="date_deleted", nullable = true)
	private LocalDate dateDeleted;
	
	@Basic
	@Column(name="time_deleted", nullable = true)
	private LocalTime timeDeleted;

	private boolean deleted;
	
	@ManyToOne
	@JoinColumn(name="deleted_By", nullable = true)
	private User deletedBy;
	
	public Grade() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	public LocalDate getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDate createdDate) {
		this.createdDate = createdDate;
	}

	public LocalTime getTimeCreated() {
		return timeCreated;
	}

	public void setTimeCreated(LocalTime timeCreated) {
		this.timeCreated = timeCreated;
	}

	public LocalDate getDateDeleted() {
		return dateDeleted;
	}

	public void setDateDeleted(LocalDate dateDeleted) {
		this.dateDeleted = dateDeleted;
	}

	public LocalTime getTimeDeleted() {
		return timeDeleted;
	}

	public void setTimeDeleted(LocalTime timeDeleted) {
		this.timeDeleted = timeDeleted;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public User getDeletedBy() {
		return deletedBy;
	}

	public void setDeletedBy(User deletedBy) {
		this.deletedBy = deletedBy;
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}
	
	
}
