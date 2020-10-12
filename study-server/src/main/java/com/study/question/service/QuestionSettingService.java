package com.study.question.service;

import com.study.exceptions.ServiceException;
import com.study.question.entity.QuestionSetting;

public interface QuestionSettingService {

	public QuestionSetting getQuestionSetting() throws ServiceException;
	
	public void updateQuestionSetting(QuestionSetting questionSetting) throws ServiceException;
}
