package com.study.question.dto;

import java.util.List;

public class ExamPaperQuestionsDto {

	private ExamPaperDto examPaper;

	private List<Long> questionIds;

	public ExamPaperDto getExamPaper() {
		return examPaper;
	}

	public void setExamPaper(ExamPaperDto examPaper) {
		this.examPaper = examPaper;
	}

	public List<Long> getQuestionIds() {
		return questionIds;
	}

	public void setQuestionIds(List<Long> questionIds) {
		this.questionIds = questionIds;
	}
}
