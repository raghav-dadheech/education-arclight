package com.study.user.dao;

import java.util.List;

import com.study.exceptions.DaoException;
import com.study.question.dao.SearchParam;
import com.study.user.entity.UserModal;

public interface UserDao {

	
	public void add(UserModal user) throws DaoException;
	
	public List<UserModal> list(SearchParam searchParam) throws DaoException;
	
	public UserModal get(long userId) throws DaoException;
	
	public UserModal get(SearchParam searchParam) throws DaoException;
	
	public UserModal getUserByUserName(String userName) throws DaoException;
	
}
