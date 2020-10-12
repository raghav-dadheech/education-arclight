package com.study.question.service;

import com.study.exceptions.ServiceException;
import com.study.question.entity.Images;

public interface ImagesService {

public void addImage(Images image) throws ServiceException;
	
	public Images get(long id) throws ServiceException;
}
