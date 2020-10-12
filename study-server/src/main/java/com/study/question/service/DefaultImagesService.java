package com.study.question.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.study.exceptions.DaoException;
import com.study.exceptions.ServiceException;
import com.study.question.dao.ImagesDao;
import com.study.question.entity.Images;

@Service
@Transactional
public class DefaultImagesService implements ImagesService {

	@Autowired
	private ImagesDao imagesDao;
	
	@Override
	public void addImage(Images image) throws ServiceException {
		try {
			imagesDao.addImage(image);
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage());
		}
	}

	@Override
	public Images get(long id) throws ServiceException {
		try {
			return imagesDao.get(id);
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage());
		}
	}

}
