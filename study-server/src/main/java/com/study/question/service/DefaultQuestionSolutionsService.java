package com.study.question.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.study.exceptions.DaoException;
import com.study.exceptions.ServiceException;
import com.study.question.dao.QuestionSolutionsDao;
import com.study.question.dao.SearchParam;
import com.study.question.entity.QuestionSolutions;

@Service
@Transactional
public class DefaultQuestionSolutionsService implements QuestionSolutionsService {

	@Autowired
	private QuestionSolutionsDao questionSolutionsDao;
	
	@Override
	public void add(QuestionSolutions image) throws ServiceException {
		try {
			questionSolutionsDao.add(image);
		} catch(DaoException ex) {
			ex.printStackTrace();
			throw new ServiceException(ex.getMessage());
		}
	}
	
	@Override
	public void remove(SearchParam searchParam) throws ServiceException {
		try {
			questionSolutionsDao.remove(searchParam);
		} catch(DaoException ex) {
			ex.printStackTrace();
			throw new ServiceException(ex.getMessage());
		}
	}
	
	@Override
	public List<QuestionSolutions> listSolutionImages(SearchParam searchParam) throws ServiceException {
		try {
			return questionSolutionsDao.listSolutionImages(searchParam);
		} catch(DaoException ex) {
			ex.printStackTrace();
			throw new ServiceException(ex.getMessage());
		}
	}

}
