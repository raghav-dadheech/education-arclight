package com.study.user.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.study.exceptions.ServiceException;
import com.study.question.dao.SearchParam;
import com.study.user.entity.UserModal;

public interface UserService {

	public void add(UserModal user) throws ServiceException;
	
	public List<UserModal> list(SearchParam searchParam) throws ServiceException;
	
	public UserModal get(long userId) throws ServiceException;
	
	public UserModal get(SearchParam searchParam) throws ServiceException;
	
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
	
	public UserModal loadUserByEmailOrPhone(String username) throws UsernameNotFoundException;
}
