package com.study.question.entity.facade;

import com.study.exceptions.FacadeException;
import com.study.question.dto.QuestionProperty;

public interface QuestionPropertyFacade {

	public QuestionProperty questionProperty() throws FacadeException;
}
