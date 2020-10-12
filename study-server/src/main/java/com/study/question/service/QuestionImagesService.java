package com.study.question.service;

import java.util.List;

import com.study.exceptions.ServiceException;
import com.study.question.dao.SearchParam;
import com.study.question.entity.QuestionImages;

public interface QuestionImagesService {

	public void add(QuestionImages image) throws ServiceException;

	public void remove(SearchParam searchParam) throws ServiceException;
	
	public List<QuestionImages> listQUestionImages(SearchParam searchParam) throws ServiceException;
}
