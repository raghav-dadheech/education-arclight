package com.study.question.dao;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.study.exceptions.DaoException;
import com.study.question.entity.DifficultyLevel;

@Repository
public class DefaultDifficultyLevelDao extends GenericDAOImpl<DifficultyLevel> implements DifficultyLevelDao {

	@Override
	public List<DifficultyLevel> listAll() throws DaoException {
		try {
			return findAll();
		} catch (SQLException e) {
			throw new DaoException(e.getMessage());
		}
	}

}
