package com.study.user.convertor;

import org.springframework.stereotype.Component;

import com.study.user.dto.UserRoleDto;
import com.study.user.entity.UserRole;

@Component
public class UserRoleConvertor {

	public void convert(UserRole source, UserRoleDto target) {
		target.setId(source.getId());
		target.setRole(source.getRole());
		target.setActive(source.isActive());
	}
	
	public void convert(UserRoleDto source, UserRole target) {
		target.setId(source.getId());
		target.setRole(source.getRole());
		target.setActive(source.isActive());
	}
}
