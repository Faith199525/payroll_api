package com.codetouch.payroll.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
@Table(name="core_user")
public class User{

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="user_generator")
	@SequenceGenerator(name="user_generator", sequenceName="user_gen", allocationSize = 4,initialValue = 1)
	@Column(name="id")
    public long id;
	
	@Column(name="first_name", length =  50, nullable = false)
	private String firstName;
	
	@Column(name="last_name", length =  50, nullable = false)
	private String lastName;
	
	@Column(name="sex", length = 30, nullable = false)
	private String sex;
	
	@Column(name="phone_number", length = 80, nullable = false)
	private String phoneNumber;
	
	@Column(name="email", length = 80, nullable = false, unique=true)
	private String email;
	
	@Column(name="country", length = 80, nullable = false)
	private String country;
	
	@Column(name="state", length = 80, nullable = false)
	private String state;
	
	@Column(name="city", length =  50, nullable = true)
	private String city;
	
	@Column(name="password", nullable = false)
	private String password;
	
	@Column(name="confirmationToken", length = 80, unique=true)
	private String confirmationToken;

	private boolean active;
	
	@Column(name="date_activated", nullable = true)
	private LocalDateTime dateActivated;

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
	
	public User() {
	
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
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

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmationToken() {
		return confirmationToken;
	}

	public void setConfirmationToken(String confirmationToken) {
		this.confirmationToken = confirmationToken;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public LocalDateTime getDateActivated() {
		return dateActivated;
	}

	public void setDateActivated(LocalDateTime dateActivated) {
		this.dateActivated = dateActivated;
	}

	public LocalDate getCreatedDate() {
		return createdDate;
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

	public LocalTime getTimeCreated() {
		return timeCreated;
	}

	public void setTimeCreated(LocalTime timeCreated) {
		this.timeCreated = timeCreated;
	}

}
