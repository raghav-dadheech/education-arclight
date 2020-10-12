package com.study.user.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.study.exceptions.DaoException;
import com.study.exceptions.ServiceException;
import com.study.question.dao.SearchParam;
import com.study.user.dao.UserRoleDao;
import com.study.user.entity.UserRole;

@Service
@Transactional
public class DefaultUserRoleService implements UserRoleService {

	@Autowired
	private UserRoleDao userRoleDao;
	
	@Override
	public UserRole getUserRole(SearchParam searchParam) throws ServiceException {
		try {
			return userRoleDao.getUserRole(searchParam);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		}
	}

	@Override
	public List<UserRole> getUserRoles() throws ServiceException {
		try {
			return userRoleDao.listUserRoles();
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		}
	}
}
