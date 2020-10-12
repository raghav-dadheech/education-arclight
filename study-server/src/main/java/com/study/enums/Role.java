package com.study.enums;

public enum Role {

	SUPER_ADMIN("SUPER_ADMIN"), ADMIN("ADMIN"), 
	MANAGER("MANAGER"), QUESTION_AGENT("QUESTION_AGENT"), 
	FACULTY("FACULTY"), STAFF("STAFF"), STUDENT("STUDENT");
	
	private String value;
	
	private Role(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
}
