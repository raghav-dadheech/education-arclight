package com.study.user.convertor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.study.user.dto.OrganisationDto;
import com.study.user.dto.UserDto;
import com.study.user.entity.OrganisationModal;
import com.study.user.entity.UserModal;

@Component
public class UserConvertor {

	@Autowired
	private OrganisationConvertor organisationConvertor;
	
	public void convert(UserModal source, UserDto target) {
		target.setId(source.getId());
		target.setUserName(source.getUserName());
		target.setEmail(source.getEmail());
		target.setPhone(source.getPhone());
		target.setJoiningDate(source.getJoiningDate());
		target.setEnabled(source.isEnabled());
		target.setFirstName(source.getFirstName());
		target.setLastName(source.getLastName());
		target.setDob(source.getDob());
		target.setRole(source.getRole());
		target.setProfileComplete(source.isProfileComplete());
		
		
		OrganisationDto orgdto = new OrganisationDto();
		organisationConvertor.convert(source.getOrganisation(), orgdto);
		target.setOrganisation(orgdto);
	}
	
	public void convert(UserDto source, UserModal target) {
//		target.setId(source.getId());
		target.setUserName(source.getUserName());
		target.setPassword(source.getPassword());
		target.setEmail(source.getEmail());
		target.setPhone(source.getPhone());
		target.setJoiningDate(source.getJoiningDate());
		target.setEnabled(source.isEnabled());
		target.setFirstName(source.getFirstName());
		target.setLastName(source.getLastName());
		target.setDob(source.getDob());
		target.setRole(source.getRole());
		target.setProfileComplete(source.isProfileComplete());
		
		OrganisationModal orgModal = new OrganisationModal();
		organisationConvertor.convert(source.getOrganisation(), orgModal);
		target.setOrganisation(orgModal);
	}
}
