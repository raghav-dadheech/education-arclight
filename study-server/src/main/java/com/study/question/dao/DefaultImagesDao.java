package com.study.question.dao;

import java.sql.SQLException;

import org.springframework.stereotype.Repository;

import com.study.exceptions.DaoException;
import com.study.question.entity.Images;

@Repository
public class DefaultImagesDao extends GenericDAOImpl<Images> implements ImagesDao {

	@Override
	public void addImage(Images image) throws DaoException {
		try {
			saveOrUpdate(image);
		} catch (SQLException e) {
			throw new DaoException(e.getMessage());
		}
	}

	@Override
	public Images get(long id) throws DaoException {
		try {
			return findById(id);
		} catch (SQLException e) {
			throw new DaoException(e.getMessage());
		}
	}
}
