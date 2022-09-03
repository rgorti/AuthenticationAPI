package com.craft.authentication.model;

public class UserResponse {
	
	String userToken;
	String message;
	
	public UserResponse() {
	
	}
	
	public UserResponse(String token) {
		userToken = token;

	}

	public String getUsertoken() {
		return userToken;
	}

	public void setUsertoken(String usertoken) {
		this.userToken = usertoken;
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	

}
