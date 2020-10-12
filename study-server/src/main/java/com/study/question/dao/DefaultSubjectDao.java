package com.study.question.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.study.exceptions.DaoException;
import com.study.question.entity.Subject;

@Repository
public class DefaultSubjectDao extends GenericDAOImpl<Subject> implements SubjectDao {

	@Override
	public List<Subject> listSubjects() throws DaoException {
		try {
			return findAll();
		} catch (Exception e) {
			throw new DaoException(e.getMessage());
		}
	}

}
