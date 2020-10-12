package com.study.question.dto;

public class QuestionListFilterDto {

	private SubjectDto subject;

	private ChapterDto chapter;

	private QuestionTypeDto questionType;

	private DifficultyLevelDto difficultyLevel;

	private Integer approval = 0;

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

	public Integer getApproval() {
		return approval;
	}

	public void setApproval(Integer approval) {
		this.approval = approval;
	}

}
