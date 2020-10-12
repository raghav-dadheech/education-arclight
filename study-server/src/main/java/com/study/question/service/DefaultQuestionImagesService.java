package com.study.question.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.study.exceptions.DaoException;
import com.study.exceptions.ServiceException;
import com.study.question.dao.QuestionImagesDao;
import com.study.question.dao.SearchParam;
import com.study.question.entity.QuestionImages;

@Service
@Transactional
public class DefaultQuestionImagesService implements QuestionImagesService {

	@Autowired
	private QuestionImagesDao questionImagesDao;
	
	@Override
	public void add(QuestionImages image) throws ServiceException {
		try {
			questionImagesDao.add(image);
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage());
		}
	}

	@Override
	public void remove(SearchParam searchParam) throws ServiceException {
		try {
			questionImagesDao.remove(searchParam);
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage());
		}
	}
	
	@Override
	public List<QuestionImages> listQUestionImages(SearchParam searchParam) throws ServiceException {
		try {
			return questionImagesDao.listQUestionImages(searchParam);
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage());
		}
	}

}
