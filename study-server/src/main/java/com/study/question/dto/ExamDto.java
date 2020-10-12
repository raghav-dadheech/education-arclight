package com.study.question.dto;

import java.util.ArrayList;
import java.util.List;

import com.study.enums.ExamPaperType;

public class ExamDto {

	private int pageNo;

	private int pageSize;

	private List<QuestionnaireDto> questions = new ArrayList<>();

	private SubjectDto subject;

	private List<ChapterDto> chapters;

	private List<QuestionTypeDto> questionTypes;

	private List<DifficultyLevelDto> difficultyLevels;

	private ExamPaperDto examPaper;

	private ExamPaperType examType;

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public List<QuestionnaireDto> getQuestions() {
		return questions;
	}

	public void setQuestions(List<QuestionnaireDto> questions) {
		this.questions = questions;
	}

	public SubjectDto getSubject() {
		return subject;
	}

	public void setSubject(SubjectDto subject) {
		this.subject = subject;
	}

	public List<ChapterDto> getChapters() {
		return chapters;
	}

	public void setChapters(List<ChapterDto> chapters) {
		this.chapters = chapters;
	}

	public List<QuestionTypeDto> getQuestionTypes() {
		return questionTypes;
	}

	public void setQuestionTypes(List<QuestionTypeDto> questionTypes) {
		this.questionTypes = questionTypes;
	}

	public List<DifficultyLevelDto> getDifficultyLevels() {
		return difficultyLevels;
	}

	public void setDifficultyLevels(List<DifficultyLevelDto> difficultyLevels) {
		this.difficultyLevels = difficultyLevels;
	}

	public ExamPaperDto getExamPaper() {
		return examPaper;
	}

	public void setExamPaper(ExamPaperDto examPaper) {
		this.examPaper = examPaper;
	}

	public ExamPaperType getExamType() {
		return examType;
	}

	public void setExamType(ExamPaperType examType) {
		this.examType = examType;
	}

}
