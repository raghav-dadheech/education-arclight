package com.study.question.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.study.exceptions.DaoException;
import com.study.exceptions.ServiceException;
import com.study.question.dao.ExamPaperQuestionsDao;
import com.study.question.dao.SearchParam;
import com.study.question.entity.ExamPaperQuestions;

@Service
@Transactional
public class DefaultExamPaperQuestionsService implements ExamPaperQuestionsService {

	@Autowired
	private ExamPaperQuestionsDao examPaperQuestionsDao;
	@Override
	public void addExamPaperQuestions(ExamPaperQuestions question) throws ServiceException {
		try {
			examPaperQuestionsDao.addExamPaperQuestions(question);
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
	}

	@Override
	public List<ExamPaperQuestions> list(SearchParam searchParam) throws ServiceException {
		try {
			return examPaperQuestionsDao.list(searchParam);
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
	}
	
	@Override
	public void deleteExamPaperQuestions(long id) throws ServiceException {
		try {
			examPaperQuestionsDao.deleteExamPaperQuestions(id);
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage());
		}
	}

	@Override
	public ExamPaperQuestions getExamPaperQuestion(SearchParam searchParam) throws ServiceException {
		try {
			return examPaperQuestionsDao.getExamPaperQuestion(searchParam);
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage());
		}
	}
}
