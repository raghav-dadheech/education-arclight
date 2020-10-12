package com.study.service;

import java.util.Map;

public interface EmailService {
	
	public static final String WELCOME_MAIL_SUBJECT = "Welcome to Education Arclight";
	public static final String INVITATION_MAIL_SUBJECT = "Education Arclight : Invitation";
	public static final String FORGET_PASSWORD_MAIL_SUBJECT = "Education Arclight : Reset Your Password";

	public void sendEmail(String email, String subject, Map<String, Object> model) throws Exception;
	
	public void sendWelcomeEmail(String email, String subject, Map<String, Object> model) throws Exception;
	
	public void sendInvitationEmail(String email, String subject, Map<String, Object> model) throws Exception;
	
	public void sendForgetPasswordEmail(String email, String subject, Map<String, Object> model) throws Exception;
	
}
