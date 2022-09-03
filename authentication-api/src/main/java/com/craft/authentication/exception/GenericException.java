package com.craft.authentication.exception;

import java.util.Date;

public class GenericException {
	
	private int Id;
	private String message;
	private Date timeStamp;
	public GenericException(int id, String message, Date timeStamp) {
		super();
		Id = id;
		this.message = message;
		this.timeStamp = timeStamp;
	}
	public GenericException(String message) {
		super();
		this.message = message;
		
	}
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Date getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}
	
	

}
