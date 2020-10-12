package com.study.user.dao;

import java.util.List;

import com.study.exceptions.DaoException;
import com.study.question.dao.SearchParam;
import com.study.user.entity.UserInvitation;

public interface UserInvitationDao {

	public void sendInvitation(UserInvitation invitation) throws DaoException;
	
	public void removeInvitation(UserInvitation invitation) throws DaoException;
	
	public UserInvitation getUserInvitation(SearchParam searchParam) throws DaoException;
	
	public UserInvitation getUserInvitationByEmailOrPhone(String emailOrPhone) throws DaoException;
	
	public List<UserInvitation> listInvitations(SearchParam searchParam) throws DaoException;
}
