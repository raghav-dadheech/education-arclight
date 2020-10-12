package com.study.user.dao;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.study.exceptions.DaoException;
import com.study.question.dao.GenericDAOImpl;
import com.study.question.dao.SearchParam;
import com.study.user.entity.UserRole;

@Repository
public class DefaultUserRoleDao extends GenericDAOImpl<UserRole> implements UserRoleDao {

	@Override
	public UserRole getUserRole(SearchParam searchParam) throws DaoException {
		try {
			return findBy(searchParam);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException("Failed to list user");
		}
	}

	@Override
	public List<UserRole> listUserRoles() throws DaoException {
		try {
			return findAll();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException("Failed to list user");
		}
	}
}
