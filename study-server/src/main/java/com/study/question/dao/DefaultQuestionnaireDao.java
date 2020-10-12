package com.study.question.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.study.exceptions.DaoException;
import com.study.question.entity.QuestionnaireModel;

@Repository
public class DefaultQuestionnaireDao extends GenericDAOImpl<QuestionnaireModel> implements QuestionnaireDao{

	@Override
	public void addQuestion(QuestionnaireModel questionnaireModel) throws DaoException{
		try {
			saveOrUpdate(questionnaireModel);
		} catch (Exception e) {
			throw new DaoException(e.getMessage());
		}
	}

	@Override
	public List<QuestionnaireModel> listAllBy(SearchParam searchParam)  throws DaoException{
		try {
			return findAllBy(searchParam);
		} catch (Exception e) {
			throw new DaoException(e.getMessage());
		}
	}
	
	
	@Override
	public List<QuestionnaireModel> listAllBy(SearchParam searchParam, int pageNo, int pageSize) throws DaoException {
		try {
			return findAllWithPagination(searchParam, pageNo, pageSize);
		} catch (Exception e) {
			throw new DaoException(e.getMessage());
		}
	}
	
	@Override
	public QuestionnaireModel getQuestion(final Long questionId) throws DaoException {
		try {
			return findById(questionId);
		} catch (Exception e) {
			throw new DaoException(e.getMessage());
		}
	}
}
