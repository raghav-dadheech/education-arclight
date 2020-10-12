package com.study.user.dao;

import java.util.List;

import com.study.exceptions.DaoException;

import com.study.question.dao.SearchParam;
import com.study.user.entity.UserRole;

public interface UserRoleDao {

	public UserRole getUserRole(SearchParam searchParam) throws DaoException;
	
	public List<UserRole> listUserRoles() throws DaoException;
}
