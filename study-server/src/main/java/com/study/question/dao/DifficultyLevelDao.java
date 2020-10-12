package com.study.question.dao;

import java.util.List;

import com.study.exceptions.DaoException;
import com.study.question.entity.DifficultyLevel;

public interface DifficultyLevelDao {

	public List<DifficultyLevel> listAll() throws DaoException;
}
