package com.study.enums;

public enum ExamPaperType {

	PRACTICE("practice"), PAPER("paper");
	
	private String value;
	
	private ExamPaperType(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
}
