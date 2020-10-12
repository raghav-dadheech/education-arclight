package com.study.question.service;

import java.util.List;

import com.study.exceptions.ServiceException;
import com.study.question.entity.DifficultyLevel;

public interface DifficultyLevelService {

	public List<DifficultyLevel> listAll() throws ServiceException;
}
