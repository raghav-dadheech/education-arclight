package com.study.user.dao;

import java.sql.SQLException;

import org.springframework.stereotype.Repository;

import com.study.exceptions.DaoException;
import com.study.question.dao.GenericDAOImpl;
import com.study.question.dao.SearchParam;
import com.study.user.entity.ForgetPassword;

@Repository
public class DefaultForgetPasswordDao extends GenericDAOImpl<ForgetPassword> implements ForgetPasswordDao {

	@Override
	public void sendForgetPassword(ForgetPassword forgetPassword) throws DaoException {
		try {
			saveOrUpdate(forgetPassword);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException(e.getMessage());
		}
	}

	@Override
	public void removeForgetPassword(ForgetPassword forgetPassword) throws DaoException {
		try {
			remove(forgetPassword);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException(e.getMessage());
		}
	}

	@Override
	public ForgetPassword find(SearchParam searchParam) throws DaoException {
		try {
			return findBy(searchParam);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException(e.getMessage());
		}
	}

}
