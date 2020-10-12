package com.study.exceptions;


public class FacadeException extends Exception {
	
	private static final long serialVersionUID = 1L;

	private String message;
	
	public FacadeException(String message) {
		this.message = message;
	}
	
	public FacadeException(String message, Throwable e) {
		super(message,e);
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
