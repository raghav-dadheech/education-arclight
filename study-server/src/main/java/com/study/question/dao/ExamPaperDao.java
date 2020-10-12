package com.study.question.dao;

import java.util.List;

import com.study.exceptions.DaoException;
import com.study.question.entity.ExamPaper;

public interface ExamPaperDao {

	
	public void addExamPaper(ExamPaper examPaper) throws DaoException;
	
	public List<ExamPaper> listAll() throws DaoException;
	
	public ExamPaper getExamPaper(long id) throws DaoException;
	
	public void deleteExamPaper(long id) throws DaoException;
}
