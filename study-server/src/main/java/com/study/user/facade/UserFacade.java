package com.study.user.facade;

import com.study.exceptions.FacadeException;
import com.study.question.dto.ResponseDto;
import com.study.user.dto.UserDto;

public interface UserFacade {

	public ResponseDto register(UserDto userDto) throws FacadeException;
	
//	public void login(UserDto userDto) throws FacadeException;
	
//	public void logout(UserDto userDto) throws FacadeException;

	public ResponseDto forgetPassword(UserDto userDto) throws FacadeException;
	
	public ResponseDto resetPassword(UserDto userDto) throws FacadeException;
	
	public UserDto getLoggedinUser(String username) throws FacadeException;
}
