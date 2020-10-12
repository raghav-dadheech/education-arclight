package com.study.question.dao;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.study.exceptions.DaoException;
import com.study.question.entity.ExamPaperQuestions;

@Repository
public class DefaultExamPaperQuestionsDao extends GenericDAOImpl<ExamPaperQuestions> implements ExamPaperQuestionsDao {

	@Override
	public void addExamPaperQuestions(ExamPaperQuestions question) throws DaoException {
		try {
			saveOrUpdate(question);
		} catch (SQLException e) {
			throw new DaoException(e.getMessage());
		}
	}

	@Override
	public List<ExamPaperQuestions> list(SearchParam searchParam) throws DaoException {
		try {
			return findAllBy(searchParam);
		} catch (SQLException e) {
			throw new DaoException(e.getMessage());
		}
	}

	@Override
	public void deleteExamPaperQuestions(long id) throws DaoException {
		try {
			deleteById(id);
		} catch (SQLException e) {
			throw new DaoException(e.getMessage());
		}
	}
	
	@Override
	public ExamPaperQuestions getExamPaperQuestion(SearchParam searchParam) throws DaoException {
		try {
			return findBy(searchParam);
		} catch (SQLException e) {
			throw new DaoException(e.getMessage());
		}
	}
}
