package com.codetouch.payroll.entity;

import java.time.LocalDateTime;
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
@Table(name="core_password_reset")
public class PasswordReset {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="pass_generator")
	@SequenceGenerator(name="pass_generator", sequenceName="password_gen", allocationSize = 4,initialValue = 4)
	@Column(name="id")
    public Long id;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private User userId;

	@Column(name="confirmationToken", length = 80, unique=true)
	private String confirmationToken;
	
	@Column(length = 80, nullable = false)
	private String email;
	
	@Column(name="token_expiration", nullable = false)
	private LocalDateTime tokenExpiration;
	
	private boolean IsConfirmed;
	
	@Column(name="token_sent_time", nullable = false)
	private LocalDateTime tokenSentTime;
	//@Column(name="time_reset")
	//private LocalTime timeReset;
	
	@Column(name="date_reset")
	private LocalDateTime dateReseted;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUserId() {
		return userId;
	}

	public void setUserId(User userId) {
		this.userId = userId;
	}

	public String getConfirmationToken() {
		return confirmationToken;
	}

	public void setConfirmationToken(String confirmationToken) {
		this.confirmationToken = confirmationToken;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isIsConfirmed() {
		return IsConfirmed;
	}

	public void setIsConfirmed(boolean isConfirmed) {
		IsConfirmed = isConfirmed;
	}

	public LocalDateTime getTokenExpiration() {
		return tokenExpiration;
	}

	public void setTokenExpiration(LocalDateTime tokenExpiration) {
		this.tokenExpiration = tokenExpiration;
	}

	public LocalDateTime getDateReseted() {
		return dateReseted;
	}

	public void setDateReseted(LocalDateTime dateReseted) {
		this.dateReseted = dateReseted;
	}

	public LocalDateTime getTokenSentTime() {
		return tokenSentTime;
	}

	public void setTokenSentTime(LocalDateTime tokenSentTime) {
		this.tokenSentTime = tokenSentTime;
	}
	

	
}
