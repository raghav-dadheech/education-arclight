package com.study.question.dao;

import com.study.exceptions.DaoException;
import com.study.question.entity.Images;

public interface ImagesDao {

	public void addImage(Images image) throws DaoException;
	
	public Images get(long id) throws DaoException;
}
