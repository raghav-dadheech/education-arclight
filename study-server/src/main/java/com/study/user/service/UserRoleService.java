package com.study.user.service;

import java.util.List;

import com.study.exceptions.ServiceException;
import com.study.question.dao.SearchParam;
import com.study.user.entity.UserRole;

public interface UserRoleService {

	public UserRole getUserRole(SearchParam searchParam) throws ServiceException;
	
	public List<UserRole> getUserRoles() throws ServiceException;
}
