package com.study.question.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.study.exceptions.DaoException;
import com.study.exceptions.ServiceException;
import com.study.question.dao.QuestionCounterDao;
import com.study.question.entity.QuestionCounter;


@Service
@Transactional
public class DefaultQuestionCounterService implements QuestionCounterService {

	@Autowired
	private QuestionCounterDao questionCounterDao;
	
	@Override
	public List<QuestionCounter> listAll() throws ServiceException {
		try {
			return questionCounterDao.listAll();
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage());
		}
	}

	@Override
	public void updateQuestionCounter() throws ServiceException {
		try {
			QuestionCounter questionCounter = listAll().get(0);
			long counter = questionCounter.getCounter();
			questionCounter.setCounter(++counter);
			questionCounterDao.updateQuestionCounter(questionCounter);
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage());
		}
	}
}
