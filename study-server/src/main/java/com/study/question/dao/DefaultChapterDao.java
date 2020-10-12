package com.study.question.dao;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.study.exceptions.DaoException;
import com.study.question.entity.Chapter;
import com.study.question.entity.Subject;

@Repository
public class DefaultChapterDao extends GenericDAOImpl<Chapter> implements ChapterDao {

	@Override
	public List<Chapter> listChaptersBySubject(Subject subject) throws DaoException {
		try {
			SearchParam searchParam = new SearchParam();
			Map<String, Object> searchMap = new LinkedHashMap<>();
			searchMap.put("subject", subject);
			searchParam.setSearchMap(searchMap);
			return findAllBy(searchParam);
		} catch (SQLException e) {
			throw new DaoException(e.getMessage());
		}
	}

}
