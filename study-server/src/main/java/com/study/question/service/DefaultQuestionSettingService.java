package com.study.question.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.study.exceptions.DaoException;
import com.study.exceptions.ServiceException;
import com.study.question.dao.QuestionSettingDao;
import com.study.question.entity.QuestionSetting;

@Service
@Transactional
public class DefaultQuestionSettingService implements QuestionSettingService {

	@Autowired
	private QuestionSettingDao questionSettingDao;
	
	@Override
	public QuestionSetting getQuestionSetting() throws ServiceException {
		try {
			return questionSettingDao.getQuestionSetting();
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage());
		}
	}

	@Override
	public void updateQuestionSetting(QuestionSetting questionSetting) throws ServiceException {
		try {
			questionSettingDao.updateQuestionSetting(questionSetting);
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage());
		}
	}

}
