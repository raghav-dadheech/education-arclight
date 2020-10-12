package com.study.question.service;

import java.util.List;

import com.study.exceptions.ServiceException;
import com.study.question.entity.QuestionType;

public interface QuestionTypeService {

	public List<QuestionType> listAll() throws ServiceException;
}
