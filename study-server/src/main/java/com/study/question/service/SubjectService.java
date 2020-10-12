package com.study.question.service;

import java.util.List;

import com.study.exceptions.ServiceException;
import com.study.question.entity.Subject;

public interface SubjectService {

	public List<Subject> listSubjects() throws ServiceException;
}
