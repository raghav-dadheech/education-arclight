package com.study.question.dao;

import java.util.List;

import com.study.exceptions.DaoException;
import com.study.question.entity.Chapter;
import com.study.question.entity.Subject;

public interface ChapterDao {

	public List<Chapter> listChaptersBySubject(Subject subject) throws DaoException;
}
