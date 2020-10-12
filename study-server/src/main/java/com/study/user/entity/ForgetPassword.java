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

@Table(name = "forget_password")
@Entity
public class ForgetPassword {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@ManyToOne
	@JoinColumn(name = "user")
	private UserModal user;
	
	@Column(name="token")
	private String token;
	
	@Column(name="token_time")
	private Timestamp token_generated_on = new Timestamp(System.currentTimeMillis());
	
	@Column(name="active")
	private boolean active = true;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public UserModal getUser() {
		return user;
	}

	public void setUser(UserModal user) {
		this.user = user;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Timestamp getToken_generated_on() {
		return token_generated_on;
	}

	public void setToken_generated_on(Timestamp token_generated_on) {
		this.token_generated_on = token_generated_on;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
	
}
