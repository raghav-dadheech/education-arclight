package com.study.question.service;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.study.exceptions.DaoException;
import com.study.exceptions.ServiceException;
import com.study.question.dao.GenericDAOImpl;
import com.study.question.dao.SubjectDao;
import com.study.question.entity.Subject;

@Service
@Transactional
public class DefaultSubjectService extends GenericDAOImpl<Subject> implements SubjectService {

	@Autowired
	private SubjectDao subjectDao;
	
	@Override
	public List<Subject> listSubjects() throws ServiceException {
		try {
			List<Subject> subjects = subjectDao.listSubjects();
//			subjects.forEach(subject->{
//				Hibernate.initialize(subject.getChapters());
//			});
			return subjects;
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage());
		}
	}

}
