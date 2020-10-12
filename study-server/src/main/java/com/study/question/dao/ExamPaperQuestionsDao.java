package com.study.question.dao;

import java.util.List;

import com.study.exceptions.DaoException;
import com.study.question.entity.ExamPaperQuestions;

public interface ExamPaperQuestionsDao {

	public void addExamPaperQuestions(ExamPaperQuestions question) throws DaoException;
	
	public List<ExamPaperQuestions> list(SearchParam searchParam) throws DaoException;
	
	public void deleteExamPaperQuestions(long id) throws DaoException;
	
	public ExamPaperQuestions getExamPaperQuestion(SearchParam searchParam) throws DaoException;
}
