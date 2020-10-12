package com.study.question.dto;

public class ImagesDto {

	private long id;
	
	private byte[] questionImage;
	
	private String imageFormat;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public byte[] getQuestionImage() {
		return questionImage;
	}

	public void setQuestionImage(byte[] questionImage) {
		this.questionImage = questionImage;
	}

	public String getImageFormat() {
		return imageFormat;
	}

	public void setImageFormat(String imageFormat) {
		this.imageFormat = imageFormat;
	}
}
