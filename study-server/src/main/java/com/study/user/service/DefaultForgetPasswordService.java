package com.study.user.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.study.exceptions.DaoException;
import com.study.exceptions.ServiceException;
import com.study.question.dao.SearchParam;
import com.study.user.dao.ForgetPasswordDao;
import com.study.user.entity.ForgetPassword;

@Service
@Transactional
public class DefaultForgetPasswordService implements ForgetPasswordService {

	@Autowired
	private ForgetPasswordDao forgetPasswordDao;
	
	
	@Override
	public void sendForgetPassword(ForgetPassword forgetPassword) throws ServiceException {
		try {
			forgetPasswordDao.sendForgetPassword(forgetPassword);
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage());
		}
	}

	@Override
	public void removeForgetPassword(ForgetPassword forgetPassword) throws ServiceException {
		try {
			forgetPasswordDao.removeForgetPassword(forgetPassword);
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage());
		}
	}

	@Override
	public ForgetPassword find(SearchParam searchParam) throws ServiceException {
		try {
			return forgetPasswordDao.find(searchParam);
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage());
		}
	}

}
