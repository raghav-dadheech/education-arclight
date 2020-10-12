package com.study.question.dao;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.study.exceptions.DaoException;
import com.study.question.entity.QuestionCounter;

@Repository
public class DefaultQuestionCounterDao extends GenericDAOImpl<QuestionCounter> implements QuestionCounterDao {

	@Override
	public List<QuestionCounter> listAll() throws DaoException {
		try {
			return findAll();
		} catch (Exception e) {
			throw new DaoException(e.getMessage());
		}
	}
	
	@Override
	public void updateQuestionCounter(QuestionCounter questionCounter) throws DaoException {
		try {
			saveOrUpdate(questionCounter);
		} catch (SQLException e) {
			throw new DaoException(e.getMessage());
		}
	}

}
