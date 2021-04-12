package com.codetouch.payroll.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "core_parameter")
public class PayrollParameter implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public static String SYSTEM = "system";
	
	@Id
	@Column(length = 10)
	private String id;
	
	@Column(name = "pswd_reset_expiry")
	private Integer passworResetdExpiry; //
	
	
	
	@Column(name = "max_user_active")
	private Integer maxUserActive; //

	
	public Integer getMaxUserActive() {
		return maxUserActive;
	}

	public void setMaxUserActive(Integer maxUserActive) {
		this.maxUserActive = maxUserActive;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getPassworResetdExpiry() {
		return passworResetdExpiry;
	}

	public void setPassworResetdExpiry(Integer passworResetdExpiry) {
		this.passworResetdExpiry = passworResetdExpiry;
	}
	
	
	
	
	

}
