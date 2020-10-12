package com.study.user.service;

import java.util.List;

import com.study.exceptions.ServiceException;
import com.study.question.dao.SearchParam;
import com.study.user.entity.UserInvitation;

public interface UserInvitationService {

	public void sendInvitation(UserInvitation invitation) throws ServiceException;
	
	public void removeInvitation(UserInvitation invitation) throws ServiceException;
	
	public UserInvitation getUserInvitation(SearchParam searchParam) throws ServiceException;
	
	public UserInvitation getUserInvitationByEmailOrPhone(String emailOrPhone) throws ServiceException;
	
	public List<UserInvitation> listInvitations(SearchParam searchParam) throws ServiceException;
}
