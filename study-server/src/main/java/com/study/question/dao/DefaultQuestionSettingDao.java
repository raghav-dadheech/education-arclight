package com.study.question.dao;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.study.exceptions.DaoException;
import com.study.question.entity.QuestionSetting;

@Repository
public class DefaultQuestionSettingDao extends GenericDAOImpl<QuestionSetting> implements QuestionSettingDao {

	@Override
	public void updateQuestionSetting(QuestionSetting questionSetting) throws DaoException {
		try {
			saveOrUpdate(questionSetting);
		} catch (SQLException e) {
			throw new DaoException(e.getMessage());
		}
	}

	@Override
	public QuestionSetting getQuestionSetting() throws DaoException {
		try {
			List<QuestionSetting> questionSettings = findAll();
			if(questionSettings != null && questionSettings.size() > 0)
				return questionSettings.get(0);
			else
				return new QuestionSetting();
		} catch (SQLException e) {
			throw new DaoException(e.getMessage());
		}
	}

}
