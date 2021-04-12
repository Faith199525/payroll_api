/**
 * 
 */
package com.codetouch.payroll;

import java.util.List;

import com.codetouch.payroll.entity.User;

/**
 * @author CODETOUCH
 *
 */
public class GsonResponse {
	
	private Integer status;
	private List<StatusMessage> statusMessages;
	private Object payload;
	
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public List<StatusMessage> getStatusMessages() {
		return statusMessages;
	}
	public void setStatusMessages(List<StatusMessage> statusMessages) {
		this.statusMessages = statusMessages;
	}
	public Object getPayload() {
		return payload;
	}
	public void setPayload(Object payload) {
		this.payload = payload;
	}
	public void setPayload(User es, String name) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	

}
