package com.study.app;

public class ApiResponse {
	private Object code;

	private Object status;

	private String message;
	
	private Object data;

	public ApiResponse() {

	}

	public ApiResponse(final Object status, final String message) {
		this.status = status;
	    this.message = message;
	}

	
	public ApiResponse(Object status, String message, Object data) {
		super();
		this.status = status;
		this.message = message;
		this.data = data;
	}

	public Object getCode() {
		return code;
	}

	public void setCode(Object code) {
		this.code = code;
	}

	public Object getStatus() {
		return status;
	}

	public void setStatus(Object status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public void setData(Object data) {
		this.data = data;
	}
	
	public Object getData() {
		return data;
	}
	
}
