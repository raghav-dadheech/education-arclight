package com.study.exceptions;

public class DaoException extends Exception {

	private static final long serialVersionUID = 1L;

	private String message;
	
	public DaoException(String message){
		this.message = message;
	}
	
	public DaoException(String message, Throwable e){
		super(message, e);
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
