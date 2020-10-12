package com.study.question.dao;

import java.util.List;

import com.study.exceptions.DaoException;
import com.study.question.entity.QuestionSolutions;

public interface QuestionSolutionsDao {

	public void add(QuestionSolutions image) throws DaoException;
	
	public void remove(SearchParam searchParam) throws DaoException;
	
	public List<QuestionSolutions> listSolutionImages(SearchParam searchParam) throws DaoException;
}
