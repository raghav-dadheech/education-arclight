package com.study.user.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.study.enums.UserInvitationStatus;

@Table(name = "user_invitation")
@Entity
public class UserInvitation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column
	private String email;
	
	@Column
	private String phone;
	
	@ManyToOne
	@JoinColumn(name = "role")
	private UserRole role;
	
	@ManyToOne
	@JoinColumn(name = "organisation")
	private OrganisationModal organisation;
	
	@Column(name="status")
	private String status;
	
	@Column(name="keyword")
	private String keyword;
	
	@ManyToOne
	@JoinColumn(name = "invited_by")
	private UserModal invitedBy;
	
	@Column(name="invited_on")
	private Timestamp invitedOn = new Timestamp(System.currentTimeMillis());
	
	@Column(name="invitation_token", length = 100)
	private String invitationToken;
	
	@Column(name="active")
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

	public UserModal getInvitedBy() {
		return invitedBy;
	}

	public void setInvitedBy(UserModal invitedBy) {
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

	public OrganisationModal getOrganisation() {
		return organisation;
	}

	public void setOrganisation(OrganisationModal organisation) {
		this.organisation = organisation;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	
	
}
