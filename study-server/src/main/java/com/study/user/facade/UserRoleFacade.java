package com.study.user.facade;

import java.util.List;

import com.study.exceptions.FacadeException;
import com.study.user.dto.UserRoleDto;

public interface UserRoleFacade {

	public List<UserRoleDto> getUserRoles() throws FacadeException;
}
