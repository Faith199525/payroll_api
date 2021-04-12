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
@Table(name="core_organisation")
public class Organization {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="org_generator")
	@SequenceGenerator(name="org_generator", sequenceName="org_gen", allocationSize = 10,initialValue = 1)
	@Column(name="id")
    public long id;
	
	@Column(name="short_name", length =  20, nullable = true)
	private String shortName;
	
	
	@Column(name="full_name", length =  50, nullable = false)
	private String fullName;
	
	
	@Column(name="reg_number", length =  50, nullable = true)
	private String registrationNumber;
	

	@Column(name="vat_number", length =  50, nullable = true)
	private String vatNumber;
	
	
	@Column(name="address", length =  80, nullable = false)
	private String address;
	
	@Column(name="state", length =  80, nullable = false)
	private String state;
	
	@Column(name="city", length =  80, nullable = false)
	private String city;
	
	@Basic
	@Column(name="date_Of_Incoporation", nullable = false)
	private LocalDate dateOfIncoporation;

	@Basic
	@Column(name="created_date",  nullable = false)
	private LocalDate createdDate;
	
	@Basic
	@Column(name="time_created", nullable = false)
	private LocalTime timeCreated;
	
	@ManyToOne
	@JoinColumn(name="createdBy", nullable = false)
	private User createdBy;
	
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
	
	public Organization() {
		super();
		// TODO Auto-generated constructor stub
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public LocalDate getDateOfIncoporation() {
		return dateOfIncoporation;
	}

	public void setDateOfIncoporation(LocalDate dateOfIncoporation) {
		this.dateOfIncoporation = dateOfIncoporation;
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public LocalDate getCreatedDate() {
		return createdDate;
	}
	public LocalTime getTimeCreated() {
		return timeCreated;
	}

	public void setTimeCreated(LocalTime timeCreated) {
		this.timeCreated = timeCreated;
	}

	public void setCreatedDate(LocalDate createdDate) {
		this.createdDate = createdDate;
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
	
}
