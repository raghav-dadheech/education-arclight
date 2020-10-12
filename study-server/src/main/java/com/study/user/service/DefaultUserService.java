package com.study.user.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.study.exceptions.DaoException;
import com.study.exceptions.ServiceException;
import com.study.question.dao.SearchParam;
import com.study.user.dao.UserDao;
import com.study.user.entity.UserModal;

@Service
@Transactional
public class DefaultUserService implements UserService, UserDetailsService {

	@Autowired
	private UserDao userDao;
	
	@Override
	public void add(UserModal user) throws ServiceException {
		try {
			userDao.add(user);
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage());
		}
	}

	@Override
	public List<UserModal> list(SearchParam searchParam) throws ServiceException {
		try {
			return userDao.list(searchParam);
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage());
		}
	}

	@Override
	public UserModal get(long userId) throws ServiceException {
		try {
			return userDao.get(userId);
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage());
		}
	}

	@Override
	public UserModal get(SearchParam searchParam) throws ServiceException {
		try {
			return userDao.get(searchParam);
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage());
		}
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		try {
			UserModal user =  userDao.getUserByUserName(username);
			if(user == null) return null;
			return new User(user.getEmail(), user.getPassword(), new ArrayList<>(0));
		} catch (DaoException e) {
			e.printStackTrace();
			throw new UsernameNotFoundException(e.getMessage());
		}
	}
	
	@Override
	public UserModal loadUserByEmailOrPhone(String username) throws UsernameNotFoundException {
		try {
			return userDao.getUserByUserName(username);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new UsernameNotFoundException(e.getMessage());
		}
	}
}
