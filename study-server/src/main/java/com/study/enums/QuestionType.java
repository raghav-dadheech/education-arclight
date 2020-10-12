package com.study.enums;

public enum QuestionType {

	SINGLE_SELECT("Single Select"), MULTIPLE_SELECT("Multiple Select"), NUMBER("Number");
	
	private String value;
	
	private QuestionType(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
}
