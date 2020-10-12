package com.study.question.dto;

import com.study.question.entity.Chapter;
import com.study.question.entity.DifficultyLevel;
import com.study.question.entity.QuestionType;
import com.study.question.entity.Subject;

public class QuestionListFilter {

	private Subject subject;

	private Chapter chapter;

	private QuestionType questionType;

	private DifficultyLevel difficultyLevel;

	private Integer approval = 0;

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	public Chapter getChapter() {
		return chapter;
	}

	public void setChapter(Chapter chapter) {
		this.chapter = chapter;
	}

	public QuestionType getQuestionType() {
		return questionType;
	}

	public void setQuestionType(QuestionType questionType) {
		this.questionType = questionType;
	}

	public DifficultyLevel getDifficultyLevel() {
		return difficultyLevel;
	}

	public void setDifficultyLevel(DifficultyLevel difficultyLevel) {
		this.difficultyLevel = difficultyLevel;
	}

	public Integer getApproval() {
		return approval;
	}

	public void setApproval(Integer approval) {
		this.approval = approval;
	}

}
