package com.study.question.dto;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class QuestionnaireListDto {

	List<QuestionnaireDto> questions = new ArrayList<>(0);
	
	Map<Long, Map<String, ImagesDto>> questionImages = new LinkedHashMap<>();

	public List<QuestionnaireDto> getQuestions() {
		return questions;
	}

	public void setQuestions(List<QuestionnaireDto> questions) {
		this.questions = questions;
	}

	public Map<Long, Map<String, ImagesDto>> getQuestionImages() {
		return questionImages;
	}

	public void setQuestionImages(Map<Long, Map<String, ImagesDto>> questionImages) {
		this.questionImages = questionImages;
	}

	
	
}
