package com.study.question.dao;

import java.util.List;

import com.study.exceptions.DaoException;
import com.study.question.entity.QuestionImages;

public interface QuestionImagesDao {

	public void add(QuestionImages image) throws DaoException;
	
	public void remove(SearchParam searchParam) throws DaoException;
	
	public List<QuestionImages> listQUestionImages(SearchParam searchParam) throws DaoException;
}
