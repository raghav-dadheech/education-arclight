package com.study.user.dto;

import java.sql.Date;
import java.sql.Timestamp;

import com.study.user.entity.UserRole;

public class UserDto {

	private long id;
	
	private String userName;
	
	private String password;
	
	private String email;
	
	private String phone;
	
	private Timestamp joiningDate;
	
	private boolean enabled;
	
	private long createdBy;
	
	private String firstName;
	
	private String lastName;
	
	private Date dob;
	
	private UserRole role;
	
	private boolean profileComplete;
	
	private String token;
	
	private String oldPassword;
	
	private OrganisationDto organisation;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getPassword() {
		return password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Timestamp getJoiningDate() {
		return joiningDate;
	}

	public void setJoiningDate(Timestamp joiningDate) {
		this.joiningDate = joiningDate;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(long createdBy) {
		this.createdBy = createdBy;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}

	public boolean isProfileComplete() {
		return profileComplete;
	}

	public void setProfileComplete(boolean profileComplete) {
		this.profileComplete = profileComplete;
	}

	public String getToken() {
		return token;
	}
	
	public void setToken(String token) {
		this.token = token;
	}
	
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	
	public String getOldPassword() {
		return oldPassword;
	}

	public OrganisationDto getOrganisation() {
		return organisation;
	}
	
	public void setOrganisation(OrganisationDto organisation) {
		this.organisation = organisation;
	}
}
