package com.study.question.dao;

import java.util.List;

import com.study.exceptions.DaoException;
import com.study.question.entity.QuestionType;

public interface QuestionTypeDao {

	public List<QuestionType> listAll() throws DaoException;
}
