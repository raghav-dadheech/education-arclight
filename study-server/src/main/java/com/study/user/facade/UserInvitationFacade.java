package com.study.user.facade;

import java.util.List;

import com.study.exceptions.FacadeException;
import com.study.question.dto.ResponseDto;
import com.study.user.dto.UserInvitationDto;

public interface UserInvitationFacade {

	public ResponseDto sendUserInvitation(UserInvitationDto UserInvitationDto) throws FacadeException;
	
	public List<UserInvitationDto> listInvitations() throws FacadeException;
	
	public ResponseDto cancelInvitation(Long invitationId) throws FacadeException;
	
	
}
