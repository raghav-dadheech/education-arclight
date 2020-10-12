package com.study.question.dao;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.study.exceptions.DaoException;
import com.study.question.entity.ExamPaper;

@Repository
public class DefaultExamPaperDao extends GenericDAOImpl<ExamPaper> implements ExamPaperDao {

	@Override
	public List<ExamPaper> listAll() throws DaoException {
		try {
			return findAll();
		} catch (SQLException e) {
			throw new DaoException(e.getMessage());
		}
	}
	
	@Override
	public void addExamPaper(ExamPaper examPaper) throws DaoException {
		try {
			saveOrUpdate(examPaper);
		} catch (Exception e) {
			throw new DaoException(e.getMessage());
		}
	}
	
	@Override
	public ExamPaper getExamPaper(long id) throws DaoException {
		try {
			return findById(id);
		} catch (SQLException e) {
			throw new DaoException(e.getMessage());
		}
	}
	
	@Override
	public void deleteExamPaper(long id) throws DaoException {
		try {
			deleteById(id);
		} catch (SQLException e) {
			throw new DaoException(e.getMessage());
		}
	}

}
