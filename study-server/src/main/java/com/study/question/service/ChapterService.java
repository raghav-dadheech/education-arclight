package com.study.question.service;

import java.util.List;

import com.study.exceptions.ServiceException;
import com.study.question.entity.Chapter;
import com.study.question.entity.Subject;

public interface ChapterService {

	public List<Chapter> listChaptersBySubject(Subject subject) throws ServiceException;
}
