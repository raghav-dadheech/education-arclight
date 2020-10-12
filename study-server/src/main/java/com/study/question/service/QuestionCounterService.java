package com.study.question.service;

import java.util.List;

import com.study.exceptions.ServiceException;
import com.study.question.entity.QuestionCounter;

public interface QuestionCounterService {

	public List<QuestionCounter> listAll() throws ServiceException;
	
	public void updateQuestionCounter() throws ServiceException;
}
