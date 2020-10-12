package com.study.question.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.study.exceptions.DaoException;
import com.study.exceptions.ServiceException;
import com.study.question.dao.QuestionTypeDao;
import com.study.question.entity.QuestionType;

@Service
@Transactional
public class DefaultQuestionTypeService implements QuestionTypeService {

	@Autowired
	private QuestionTypeDao questionTypeDao; 
	@Override
	public List<QuestionType> listAll() throws ServiceException {
		try {
			return questionTypeDao.listAll();
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage());
		}
	}

}
