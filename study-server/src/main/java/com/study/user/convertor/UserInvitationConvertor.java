package com.study.user.convertor;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.study.user.dto.OrganisationDto;
import com.study.user.dto.UserDto;
import com.study.user.dto.UserInvitationDto;
import com.study.user.entity.OrganisationModal;
import com.study.user.entity.UserInvitation;
import com.study.user.entity.UserModal;

@Component
public class UserInvitationConvertor {

	@Autowired
	private UserConvertor userConvertor;
	
	@Autowired
	private OrganisationConvertor organisationConvertor;
	
	public void convert(UserInvitation source, UserInvitationDto target) {
		target.setId(source.getId());
		target.setEmail(source.getEmail());
		target.setRole(source.getRole());
		target.setStatus(source.getStatus());
		target.setPhone(source.getPhone());
		target.setKeyword(source.getKeyword());
		
		OrganisationDto orgDto = new OrganisationDto();
		organisationConvertor.convert(source.getOrganisation(), orgDto);
		target.setOrganisation(orgDto);
		
		UserDto invitedBy = new UserDto();
		userConvertor.convert(source.getInvitedBy(), invitedBy);
		target.setInvitedBy(invitedBy);
		
		target.setInvitedOn(source.getInvitedOn());
		target.setInvitationToken(source.getInvitationToken());
		
		DateFormat f = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        target.setInvitedOnText(f.format(source.getInvitedOn()));
	}
	
	public void convert(UserInvitationDto source, UserInvitation target) {
		target.setId(source.getId());
		target.setEmail(source.getEmail());
		target.setRole(source.getRole());
		target.setStatus(source.getStatus());
		target.setPhone(source.getPhone());
		target.setKeyword(source.getKeyword());
		
		OrganisationModal orgDto = new OrganisationModal();
		organisationConvertor.convert(source.getOrganisation(), orgDto);
		target.setOrganisation(orgDto);
		
		UserModal invitedBy = new UserModal();
		userConvertor.convert(source.getInvitedBy(), invitedBy);
		target.setInvitedBy(invitedBy);
		
		target.setInvitedOn(source.getInvitedOn());
		target.setInvitationToken(source.getInvitationToken());
	}
}
