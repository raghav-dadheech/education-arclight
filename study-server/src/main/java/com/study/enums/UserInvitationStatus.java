package com.study.enums;

public enum UserInvitationStatus {

	PENDING("PENDING"), ACCEPTED("ACCEPTED"), CANCELLED("CANCELLED"), EXPIRED("EXPIRED");
	
	private String value;
	
	private UserInvitationStatus(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
}
