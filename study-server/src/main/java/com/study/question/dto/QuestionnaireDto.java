package com.study.question.dto;

import org.springframework.web.multipart.MultipartFile;

public class QuestionnaireDto {

	private long id;

	private String question;

	private SubjectDto subject;

	private ChapterDto chapter;

	private QuestionTypeDto questionType;

	private DifficultyLevelDto difficultyLevel;

	private String answer;

	private QuestionSettingDto questionSettings;

	private boolean approved;

	private String solution;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public SubjectDto getSubject() {
		return subject;
	}

	public void setSubject(SubjectDto subject) {
		this.subject = subject;
	}

	public ChapterDto getChapter() {
		return chapter;
	}

	public void setChapter(ChapterDto chapter) {
		this.chapter = chapter;
	}

	public QuestionTypeDto getQuestionType() {
		return questionType;
	}

	public void setQuestionType(QuestionTypeDto questionType) {
		this.questionType = questionType;
	}

	public DifficultyLevelDto getDifficultyLevel() {
		return difficultyLevel;
	}

	public void setDifficultyLevel(DifficultyLevelDto difficultyLevel) {
		this.difficultyLevel = difficultyLevel;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public QuestionSettingDto getQuestionSettings() {
		return questionSettings;
	}

	public void setQuestionSettings(QuestionSettingDto questionSettings) {
		this.questionSettings = questionSettings;
	}

	public boolean isApproved() {
		return approved;
	}

	public void setApproved(boolean approved) {
		this.approved = approved;
	}

	
	
	
	public String getSolution() {
		return solution;
	}

	public void setSolution(String solution) {
		this.solution = solution;
	}
}
