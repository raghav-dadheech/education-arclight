package com.study.question.service;

import java.util.List;

import com.study.exceptions.ServiceException;
import com.study.question.dao.SearchParam;
import com.study.question.entity.QuestionSolutions;

public interface QuestionSolutionsService {

	public void add(QuestionSolutions image) throws ServiceException;
	
	public void remove(SearchParam searchParam) throws ServiceException;

	public List<QuestionSolutions> listSolutionImages(SearchParam searchParam) throws ServiceException;
}
