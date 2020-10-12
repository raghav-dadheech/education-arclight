package com.study.question.service;

import java.util.List;

import com.study.exceptions.ServiceException;
import com.study.question.dao.SearchParam;
import com.study.question.entity.QuestionnaireModel;

public interface QuestionnaireService {

	public void addQuestion(QuestionnaireModel questionnaireModel) throws ServiceException;

	public List<QuestionnaireModel> listAll() throws ServiceException;
	
	public List<QuestionnaireModel> listAll(SearchParam searchParam) throws ServiceException;
	
	public List<QuestionnaireModel> listAllTrash() throws ServiceException;
	
	public QuestionnaireModel getQuestion(final Long questionId) throws ServiceException;

	public void deleteQuestion(long questionId) throws ServiceException;
	
	public void restoreQuestion(long questionId) throws ServiceException;
	
	public void updateApprovalStatus(long questionId, boolean status) throws ServiceException;
}
