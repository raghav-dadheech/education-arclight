package com.study.question.dto;

import java.util.List;

import com.study.question.entity.Chapter;
import com.study.question.entity.DifficultyLevel;
import com.study.question.entity.QuestionType;
import com.study.question.entity.Subject;

public class Exam {

	private int pageSize;

	private Subject subject;

	private List<Chapter> chapters;

	private List<QuestionType> questionTypes;

	private List<DifficultyLevel> difficultyLevels;

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	public List<Chapter> getChapters() {
		return chapters;
	}

	public void setChapters(List<Chapter> chapters) {
		this.chapters = chapters;
	}

	public List<QuestionType> getQuestionTypes() {
		return questionTypes;
	}

	public void setQuestionTypes(List<QuestionType> questionTypes) {
		this.questionTypes = questionTypes;
	}

	public List<DifficultyLevel> getDifficultyLevels() {
		return difficultyLevels;
	}

	public void setDifficultyLevels(List<DifficultyLevel> difficultyLevels) {
		this.difficultyLevels = difficultyLevels;
	}
}
