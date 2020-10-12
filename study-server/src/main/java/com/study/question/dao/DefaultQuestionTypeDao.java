package com.study.question.dao;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.study.exceptions.DaoException;
import com.study.question.entity.QuestionType;

@Repository
public class DefaultQuestionTypeDao extends GenericDAOImpl<QuestionType> implements QuestionTypeDao {

	@Override
	public List<QuestionType> listAll() throws DaoException {
		try {
			return findAll();
		} catch (SQLException e) {
			throw new DaoException(e.getMessage());
		}
	}

}
