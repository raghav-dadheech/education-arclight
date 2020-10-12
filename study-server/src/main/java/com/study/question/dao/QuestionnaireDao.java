package com.study.question.dao;

import java.util.List;

import com.study.exceptions.DaoException;
import com.study.question.entity.QuestionnaireModel;

public interface QuestionnaireDao {

	
	public void addQuestion(QuestionnaireModel questionnaireModel) throws DaoException;
	
	public List<QuestionnaireModel> listAllBy(SearchParam searchParam) throws DaoException;
	
	public List<QuestionnaireModel> listAllBy(SearchParam searchParam, int pageNo, int pageSize) throws DaoException;
	
	public QuestionnaireModel getQuestion(final Long questionId) throws DaoException;
	
}
