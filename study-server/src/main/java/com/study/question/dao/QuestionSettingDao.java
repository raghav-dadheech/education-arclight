package com.study.question.dao;

import com.study.exceptions.DaoException;
import com.study.question.entity.QuestionSetting;

public interface QuestionSettingDao {

	public void updateQuestionSetting(QuestionSetting questionSetting) throws DaoException;
	
	public QuestionSetting getQuestionSetting() throws DaoException;
}
