package com.study.user.service;

import com.study.exceptions.ServiceException;
import com.study.question.dao.SearchParam;
import com.study.user.entity.ForgetPassword;

public interface ForgetPasswordService {

	public void sendForgetPassword(ForgetPassword forgetPassword) throws ServiceException;
	
	public void removeForgetPassword(ForgetPassword forgetPassword) throws ServiceException;
	
	public ForgetPassword find(SearchParam searchParam) throws ServiceException;
}
