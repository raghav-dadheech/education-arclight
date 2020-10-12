package com.study.user.dto;

import java.sql.Timestamp;

import com.study.enums.UserInvitationStatus;
import com.study.user.entity.OrganisationModal;
import com.study.user.entity.UserRole;

public class UserInvitationDto {

	private long id;
	
	private String email;
	
	private String phone;
	
	private UserRole role;
	
	private OrganisationDto organisation;
	
	private String status;
	
	private String keyword;
	
	private UserDto invitedBy;
	
	private Timestamp invitedOn;
	
	private String invitedOnText;
	
	private String invitationToken;
	
	private boolean active = true;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public UserDto getInvitedBy() {
		return invitedBy;
	}

	public void setInvitedBy(UserDto invitedBy) {
		this.invitedBy = invitedBy;
	}

	public Timestamp getInvitedOn() {
		return invitedOn;
	}

	public void setInvitedOn(Timestamp invitedOn) {
		this.invitedOn = invitedOn;
	}

	public String getInvitationToken() {
		return invitationToken;
	}

	public void setInvitationToken(String invitationToken) {
		this.invitationToken = invitationToken;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public OrganisationDto getOrganisation() {
		return organisation;
	}

	public void setOrganisation(OrganisationDto organisation) {
		this.organisation = organisation;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getInvitedOnText() {
		return invitedOnText;
	}
	
	public void setInvitedOnText(String invitedOnText) {
		this.invitedOnText = invitedOnText;
	}
}
