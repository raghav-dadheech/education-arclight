package com.study.user.facade;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.study.exceptions.FacadeException;
import com.study.exceptions.ServiceException;
import com.study.user.convertor.UserRoleConvertor;
import com.study.user.dto.UserRoleDto;
import com.study.user.entity.UserRole;
import com.study.user.service.UserRoleService;

@Component
public class DefaultUserRoleFacade implements UserRoleFacade {

	@Autowired
	private UserRoleService userRoleService;
	
	@Autowired
	private UserRoleConvertor userRoleConvertor;
	@Override
	public List<UserRoleDto> getUserRoles() throws FacadeException {
		try {
			List<UserRole> sourceRoles = userRoleService.getUserRoles();
			List<UserRoleDto> targetRoles = new ArrayList<>(0);
			sourceRoles.forEach(source->{
				UserRoleDto target = new UserRoleDto();
				userRoleConvertor.convert(source, target);
				targetRoles.add(target);
			});
			return targetRoles;
		} catch (ServiceException e) {
			e.printStackTrace();
			throw new FacadeException(e.getMessage(), e);
		}
	}

}
