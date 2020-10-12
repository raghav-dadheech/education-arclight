package com.study.question.dao;

import java.util.List;

import com.study.exceptions.DaoException;
import com.study.question.entity.Subject;

public interface SubjectDao {

	public List<Subject> listSubjects()  throws DaoException;
}
