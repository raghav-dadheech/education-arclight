package com.study.user.dao;

import com.study.exceptions.DaoException;
import com.study.question.dao.SearchParam;
import com.study.user.entity.ForgetPassword;

public interface ForgetPasswordDao {

	public void sendForgetPassword(ForgetPassword forgetPassword) throws DaoException;
	
	public void removeForgetPassword(ForgetPassword forgetPassword) throws DaoException;
	
	public ForgetPassword find(SearchParam searchParam) throws DaoException;
}
