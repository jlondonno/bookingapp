package com.kimera.upgrade.bookingapp.controller;

/**
 * @author jlondono
 */
public class ErrorMessage{
	
	private String message;
	private int status;
	
	public ErrorMessage(String message, int statusCode) {
		this.message = message;
		this.status = statusCode;
	}

	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
}
