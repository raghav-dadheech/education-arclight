package com.study.question.service;

import java.util.List;

import com.study.exceptions.ServiceException;
import com.study.question.dao.SearchParam;
import com.study.question.entity.ExamPaperQuestions;

public interface ExamPaperQuestionsService {

	public void addExamPaperQuestions(ExamPaperQuestions question) throws ServiceException;
	
	public List<ExamPaperQuestions> list(SearchParam searchParam) throws ServiceException;
	
	public void deleteExamPaperQuestions(long id) throws ServiceException;
	
	public ExamPaperQuestions getExamPaperQuestion(SearchParam searchParam) throws ServiceException;
}
