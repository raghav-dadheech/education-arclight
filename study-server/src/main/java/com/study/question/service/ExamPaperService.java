package com.study.question.service;

import java.util.List;

import com.study.exceptions.ServiceException;
import com.study.question.entity.ExamPaper;

public interface ExamPaperService {

	
	public void addExamPaper(ExamPaper examPaper) throws ServiceException;
	
	public List<ExamPaper> listAll() throws ServiceException;
	
	public ExamPaper getExamPaper(long id) throws ServiceException;
	
	public void deleteExamPaper(long id) throws ServiceException;
}
