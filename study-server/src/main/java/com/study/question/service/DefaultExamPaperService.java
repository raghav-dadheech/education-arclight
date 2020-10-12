package com.study.question.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.study.exceptions.DaoException;
import com.study.exceptions.ServiceException;
import com.study.question.dao.ExamPaperDao;
import com.study.question.entity.ExamPaper;

@Service
@Transactional
public class DefaultExamPaperService implements ExamPaperService {

	@Autowired
	private ExamPaperDao examPaperDao;
	
	@Override
	public void addExamPaper(ExamPaper examPaper) throws ServiceException {
		try {
			examPaperDao.addExamPaper(examPaper);
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage());
		}
	}

	@Override
	public List<ExamPaper> listAll() throws ServiceException {
		try {
			return examPaperDao.listAll();
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage());
		}
	}

	@Override
	public ExamPaper getExamPaper(long id) throws ServiceException {
		try {
			return examPaperDao.getExamPaper(id);
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage());
		}
	}
	
	@Override
	public void deleteExamPaper(long id) throws ServiceException {
		try {
			examPaperDao.deleteExamPaper(id);
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage());
		}
	}
}
