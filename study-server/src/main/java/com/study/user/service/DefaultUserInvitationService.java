package com.study.user.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.study.exceptions.DaoException;
import com.study.exceptions.ServiceException;
import com.study.question.dao.SearchParam;
import com.study.user.dao.UserInvitationDao;
import com.study.user.entity.UserInvitation;

@Service
@Transactional
public class DefaultUserInvitationService implements UserInvitationService {

	@Autowired
	private UserInvitationDao userInvitationDao;
	
	
	@Override
	public void sendInvitation(UserInvitation invitation) throws ServiceException {
		try {
			userInvitationDao.sendInvitation(invitation);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		}
	}

	@Override
	public void removeInvitation(UserInvitation invitation) throws ServiceException {
		try {
			userInvitationDao.removeInvitation(invitation);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		}
	}

	@Override
	public UserInvitation getUserInvitation(SearchParam searchParam) throws ServiceException {
		try {
			return userInvitationDao.getUserInvitation(searchParam);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		}
	}

	@Override
	public UserInvitation getUserInvitationByEmailOrPhone(String emailOrPhone) throws ServiceException {
		try {
			return userInvitationDao.getUserInvitationByEmailOrPhone(emailOrPhone);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		}
	}
	
	@Override
	public List<UserInvitation> listInvitations(SearchParam searchParam) throws ServiceException {
		try {
			return userInvitationDao.listInvitations(searchParam);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		}
	}
}
