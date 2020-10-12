package com.study.question.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.study.exceptions.DaoException;
import com.study.exceptions.ServiceException;
import com.study.question.dao.ChapterDao;
import com.study.question.entity.Chapter;
import com.study.question.entity.Subject;

@Service
@Transactional
public class DefaultChapterService implements ChapterService {

	@Autowired
	private ChapterDao chapterDao;
	@Override
	public List<Chapter> listChaptersBySubject(Subject subject) throws ServiceException {
		try {
			return chapterDao.listChaptersBySubject(subject);
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage());
		}
	}

}
