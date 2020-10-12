package com.study.question.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.study.exceptions.DaoException;
import com.study.exceptions.ServiceException;
import com.study.question.dao.QuestionnaireDao;
import com.study.question.dao.SearchParam;
import com.study.question.entity.QuestionnaireModel;

@Service
@Transactional
public class DefaultQuestionnaireService implements QuestionnaireService {

	@Autowired
	private QuestionnaireDao questionnaireDao;
	
	@Override
	public void addQuestion(QuestionnaireModel questionnaireModel) throws ServiceException {
		try {
			questionnaireDao.addQuestion(questionnaireModel);
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage());
		}
	}

	@Override
	public List<QuestionnaireModel> listAll() throws ServiceException {
		try {
			SearchParam searchParam = new SearchParam();
			searchParam.getSearchMap().put("enabled", true);
			List<QuestionnaireModel> questions = questionnaireDao.listAllBy(searchParam);
			return questions;
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage());
		}
	}
	
	@Override
	public List<QuestionnaireModel> listAll(SearchParam searchParam) throws ServiceException {
		try {
			return questionnaireDao.listAllBy(searchParam);
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage());
		}
	}
	
	
	@Override
	public List<QuestionnaireModel> listAllTrash() throws ServiceException {
		try {
			SearchParam searchParam = new SearchParam();
			searchParam.getSearchMap().put("enabled", false);
			List<QuestionnaireModel> questions = questionnaireDao.listAllBy(searchParam);
			return questions;
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage());
		}
	}
	
	@Override
	public QuestionnaireModel getQuestion(final Long questionId) throws ServiceException {
		try {
			QuestionnaireModel questionnaireModel = questionnaireDao.getQuestion(questionId);
			return questionnaireModel;
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage());
		}
	}
	
	@Override
	public void deleteQuestion(long questionId) throws ServiceException {
		QuestionnaireModel question = getQuestion(questionId);
		question.setEnabled(false);
		addQuestion(question);
	}
	
	@Override
	public void restoreQuestion(long questionId) throws ServiceException {
		QuestionnaireModel question = getQuestion(questionId);
		question.setEnabled(true);
		addQuestion(question);
	}
	
	@Override
	public void updateApprovalStatus(long questionId, boolean status) throws ServiceException {
		QuestionnaireModel question = getQuestion(questionId);
		question.setApproved(status);
		addQuestion(question);
	}

}
