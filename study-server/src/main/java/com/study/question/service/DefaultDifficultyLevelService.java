package com.study.question.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.study.exceptions.DaoException;
import com.study.exceptions.ServiceException;
import com.study.question.dao.DifficultyLevelDao;
import com.study.question.entity.DifficultyLevel;

@Service
@Transactional
public class DefaultDifficultyLevelService implements DifficultyLevelService {

	@Autowired
	private DifficultyLevelDao difficultyLevelDao;
	@Override
	public List<DifficultyLevel> listAll() throws ServiceException {
		try {
			return difficultyLevelDao.listAll();
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage());
		}
	}

}
