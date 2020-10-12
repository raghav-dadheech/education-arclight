package com.study.question.dao;

import java.util.List;

import com.study.exceptions.DaoException;
import com.study.question.entity.QuestionCounter;

public interface QuestionCounterDao {

	public List<QuestionCounter> listAll() throws DaoException;
	
	public void updateQuestionCounter(QuestionCounter questionCounter) throws DaoException;
}
