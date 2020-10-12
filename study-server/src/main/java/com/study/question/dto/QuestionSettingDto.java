package com.study.question.dto;

public class QuestionSettingDto {

	private long id;

	private SubjectDto subject;

	private ChapterDto chapter;

	private QuestionTypeDto questionType;

	private DifficultyLevelDto difficultyLeval;

	private boolean rememberQuestionProperties;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public DifficultyLevelDto getDifficultyLeval() {
		return difficultyLeval;
	}

	public void setDifficultyLeval(DifficultyLevelDto difficultyLeval) {
		this.difficultyLeval = difficultyLeval;
	}

	public boolean isRememberQuestionProperties() {
		return rememberQuestionProperties;
	}

	public void setRememberQuestionProperties(boolean rememberQuestionProperties) {
		this.rememberQuestionProperties = rememberQuestionProperties;
	}

}
