package com.study.user.facade;

import java.sql.Timestamp;
import java.time.LocalTime;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.study.app.StudyUtils;
import com.study.enums.Role;
import com.study.enums.UserInvitationStatus;
import com.study.exceptions.FacadeException;
import com.study.exceptions.ServiceException;
import com.study.question.dao.SearchParam;
import com.study.question.dto.ResponseDto;
import com.study.service.EmailService;
import com.study.user.convertor.UserConvertor;
import com.study.user.dto.UserDto;
import com.study.user.entity.ForgetPassword;
import com.study.user.entity.OrganisationModal;
import com.study.user.entity.UserInvitation;
import com.study.user.entity.UserModal;
import com.study.user.entity.UserRole;
import com.study.user.service.ForgetPasswordService;
import com.study.user.service.OrganisationService;
import com.study.user.service.UserInvitationService;
import com.study.user.service.UserRoleService;
import com.study.user.service.UserService;

@Component
public class DefaultUserFacade implements UserFacade {

	@Autowired
	private UserService userService;
	
	@Autowired
	private UserConvertor userConvertor;
	
	@Autowired
	private PasswordEncoder bcryptEncoder;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private UserRoleService userRoleService;
	
	@Autowired
	private UserInvitationService invitationService;
	
	@Autowired
	private ForgetPasswordService forgetPasswordService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private OrganisationService organisationService;
	
	
	@Override
	public UserDto getLoggedinUser(String username) throws FacadeException {
		try{
			UserModal u = userService.loadUserByEmailOrPhone(username);
			if(!u.isEnabled()) {
				throw new FacadeException("You account has been disabled, Please contact to admin to reactivate");	
			}
			UserDto dto = new UserDto();
			userConvertor.convert(u, dto);
			return dto;
		} catch(Exception ex) {
			throw new FacadeException("Failed to load user", ex);
		}
	}
	
	@Override
	public ResponseDto register(UserDto userDto) throws FacadeException {
		ResponseDto responseDto = new ResponseDto();
		try {
			UserModal user =  findUserExists(userDto);//userService.loadUserByEmailOrPhone(userDto.getEmail(), true);
			if(user != null && user.isEnabled()) {
				// user found and active
				responseDto.setMessage("An account with email address '"+ userDto.getEmail() + "' and phone number '"+userDto.getPhone()+"' already exists.");
				responseDto.setStatus("error");
				return responseDto;
			} if(user !=null && !user.isEnabled()) {
				// user found and not active
				if(userDto.getToken()!=null) {
					// token found
					UserInvitation invitation = getUserInvitation(userDto);
					if(invitation == null) {
						responseDto.setMessage("Invalid invitation token");
						responseDto.setStatus("error");
						return responseDto;
					} else if(!invitation.isActive()) {
						responseDto.setMessage("Invitation token expired, please contact to your admin.");
						responseDto.setStatus("error");
						return responseDto;
					} else {
						// register with token
						// enable user with token
						return registerWithToken(userDto, invitation, user);
					}
				} else {
					// token not found
					// enable user to default
					return registerToDefault(userDto, user);
				}
			}
			else {
				// user not found
				if(userDto.getToken()!=null) {
					UserInvitation invitation = getUserInvitation(userDto);
					if(invitation == null) {
						responseDto.setMessage("Invalid invitation token");
						responseDto.setStatus("error");
						return responseDto;
					} else if(!invitation.isActive()) {
						responseDto.setMessage("Invitation token expired, please contact to your admin.");
						responseDto.setStatus("error");
						return responseDto;
					} else {
						// register with token
						return registerWithToken(userDto, invitation, null);
					}
				} else {
					// register with default organization
					return registerToDefault(userDto, null);
				}
			}
		} catch(ServiceException ex) {
			responseDto.setStatus("error");
			ex.printStackTrace();
		} catch(Exception ex) {
			responseDto.setStatus("error");
			responseDto.setMessage("Unknown Error");
//			responseDto.setMessage("Your registration is successfull but failed to send invitation email");
			ex.printStackTrace();
		}
		return responseDto;
			/*
			
			if(user != null) {
				if(!user.isEnabled()) {
					
				}
				responseDto.setMessage("An account with email address '"+ userDto.getEmail() + "' already exists.");
				responseDto.setStatus("error");
				return responseDto;
			}
			user = userService.loadUserByEmailOrPhone(userDto.getPhone(), true);
			if(user != null) {
				responseDto.setMessage("An account with phone number '"+ userDto.getPhone() + "' already exists.");
				responseDto.setStatus("error");
				return responseDto;
			}
			
			SearchParam searchParam = new SearchParam();	
			boolean isInvited = false;
			UserInvitation invitation=null;
			if(userDto.getToken() != null) {
				searchParam.getSearchMap().put("email", userDto.getEmail());
				searchParam.getSearchMap().put("invitationToken", userDto.getToken());
				invitation = invitationService.getUserInvitation(searchParam);
				if(invitation == null || !invitation.isActive()) {
					responseDto.setMessage("Invitation link expired");
					responseDto.setStatus("error");
					return responseDto;
				} else {
					userDto.setRole(invitation.getRole());
					isInvited = true;
				}
			} else {
				searchParam = new SearchParam();
				searchParam.getSearchMap().put("role", Role.STUDENT.getValue());
				userDto.setRole(userRoleService.getUserRole(searchParam));
			}
			
			if(user == null) {
				user = new UserModal();
			}
			userConvertor.convert(userDto, user);
			
			user.setPassword(bcryptEncoder.encode(userDto.getPassword()));
			user.setJoiningDate(new Timestamp(System.currentTimeMillis()));
			searchParam = new SearchParam();
			searchParam.getSearchMap().put("role", userDto.getRole().getRole());
			UserRole role = userRoleService.getUserRole(searchParam);
			user.setRole(role);
			user.setProfileComplete(true);
			
			userService.add(user);
			if(isInvited) {
				invitation.setActive(false);
				invitation.setStatus(UserInvitationStatus.ACCEPTED.getValue());
				invitationService.sendInvitation(invitation);
			}
			if(user.getEmail() != null) {
				Map<String, Object> model = new LinkedHashMap<String, Object>();
				model.put("firstName", user.getFirstName());
				emailService.sendWelcomeEmail(user.getEmail(), EmailService.WELCOME_MAIL_SUBJECT, model);
			}
			responseDto.setStatus("success");
		} catch(ServiceException ex) {
			responseDto.setStatus("error");
			ex.printStackTrace();
		} catch(Exception ex) {
			responseDto.setStatus("success");
			responseDto.setMessage("Unknown Error");
//			responseDto.setMessage("Your registration is successfull but failed to send invitation email");
			ex.printStackTrace();
		}
		return responseDto;*/
	}
	
	private ResponseDto registerWithToken(UserDto userDto, UserInvitation invitation, UserModal user) throws Exception {
		if(user == null) {
			user = new UserModal();
			user.setJoiningDate(new Timestamp(System.currentTimeMillis()));
		}
		userConvertor.convert(userDto, user);
		user.setRole(invitation.getRole());
		user.setOrganisation(invitation.getOrganisation());
		user.setCreatedBy(invitation.getInvitedBy().getId());
		user.setEnabled(true);
		user.setProfileComplete(false);
		user.setPassword(bcryptEncoder.encode(userDto.getPassword()));
		userService.add(user);
		
		invitation.setActive(false);
		invitation.setStatus(UserInvitationStatus.ACCEPTED.getValue());
		invitationService.sendInvitation(invitation);
		
		Map<String, Object> model = new LinkedHashMap<String, Object>();
		model.put("firstName", user.getFirstName());
		emailService.sendWelcomeEmail(user.getEmail(), EmailService.WELCOME_MAIL_SUBJECT, model);
		
		ResponseDto responseDto = new ResponseDto();
		responseDto.setStatus("success");
		responseDto.setMessage("You are now successfully registered");
		return responseDto;
	}
	
	private ResponseDto registerToDefault(UserDto userDto, UserModal user) throws Exception {
		SearchParam searchParam = new SearchParam();
		searchParam.getSearchMap().put("role", Role.STUDENT.getValue());
		UserRole role = userRoleService.getUserRole(searchParam);
		
		searchParam = new SearchParam();
		searchParam.getSearchMap().put("code", "DEFAULT");
		OrganisationModal org = organisationService.get(searchParam);
		
		UserModal defaultUser = userService.loadUserByEmailOrPhone("educationarclight@gmail.com");
		
		if(user == null) {
			user = new UserModal();
			user.setJoiningDate(new Timestamp(System.currentTimeMillis()));
		}
		userConvertor.convert(userDto, user);
		user.setRole(role);
		user.setOrganisation(org);
		user.setCreatedBy(defaultUser.getId());
		user.setEnabled(true);
		user.setProfileComplete(false);
		user.setPassword(bcryptEncoder.encode(userDto.getPassword()));
		userService.add(user);
		
		Map<String, Object> model = new LinkedHashMap<String, Object>();
		model.put("firstName", user.getFirstName());
		emailService.sendWelcomeEmail(user.getEmail(), EmailService.WELCOME_MAIL_SUBJECT, model);
		ResponseDto responseDto = new ResponseDto();
		responseDto.setStatus("success");
		responseDto.setMessage("You are now successfully registered");
		return responseDto;
	}
	

	/*
	 * @Override public void login(UserDto userDto) throws FacadeException { try {
	 * SearchParam searchParam = new SearchParam();
	 * searchParam.getSearchMap().put("userName", userDto.getUserName()); UserModal
	 * user = userService.get(searchParam); } catch(ServiceException ex) { throw new
	 * FacadeException(ex.getMessage()); } }
	 */

	/*
	 * @Override public void logout(UserDto userDto) throws FacadeException { //
	 * TODO Auto-generated method stub
	 * 
	 * }
	 */

	
	@Override
	public ResponseDto forgetPassword(UserDto userDto) throws FacadeException {
		ResponseDto response = new ResponseDto();
		String token = StudyUtils.generateOTP();
		try {
//			SearchParam searchParam = new SearchParam();
//			searchParam.getSearchMap().put("userName", userDto.getUserName());
			String message = null;
			String username = null;
			if(userDto.getEmail() != null) { 
				username = userDto.getEmail();
				message = "Email has been sent successfully to reset your password";
			} else if(userDto.getPhone() != null) {
				username = userDto.getPhone();
				message = "OTP has been sent successfully to reset your password";
			}
			
			UserModal user = null;
			if(username != null)
				user = userService.loadUserByEmailOrPhone(username);
			if(user == null) {
				response.setStatus("error");
				response.setMessage("User does not exists.");
				return response;
			} else if(!user.isEnabled()) {
				response.setStatus("error");
				response.setMessage("Your account has been disabled, please contact to admin");
				return response;
			}
			SearchParam searchParam = new SearchParam();
			searchParam.getSearchMap().put("user", user);
			ForgetPassword forgetPassword = forgetPasswordService.find(searchParam);
			if(forgetPassword != null) {
				forgetPasswordService.removeForgetPassword(forgetPassword);
			}
			forgetPassword = new ForgetPassword();
			forgetPassword.setToken(token);
			forgetPassword.setUser(user);
			forgetPasswordService.sendForgetPassword(forgetPassword);
			
			Map<String, Object> model = new LinkedHashMap<>();
			model.put("firstName", user.getFirstName());
			model.put("token", token);
			emailService.sendForgetPasswordEmail(user.getEmail(), EmailService.FORGET_PASSWORD_MAIL_SUBJECT, model);
			response.setStatus("success");
			response.setMessage(message);
		} catch(ServiceException ex) {
			throw new FacadeException(ex.getMessage());
		} catch(Exception ex) {
			throw new FacadeException(ex.getMessage());
		}
		return response;
	}
	
	
	@Override
	public ResponseDto resetPassword(UserDto userDto) throws FacadeException {
		ResponseDto response = new ResponseDto();
		try {
			String username = null;
			if(userDto.getEmail() != null) { 
				username = userDto.getEmail();
			} else if(userDto.getPhone() != null) {
				username = userDto.getPhone();
			}
			
			UserModal user = null;
			if(username != null)
				user = userService.loadUserByEmailOrPhone(username);
			
			if(user == null) {
				response.setStatus("error");
				response.setMessage("User does not exists.");
				return response;
			} else if(!user.isEnabled()) {
				response.setStatus("error");
				response.setMessage("Your account has been disabled, please contact to admin");
				return response;
			}
			SearchParam searchParam = new SearchParam();
			searchParam.getSearchMap().put("user", user);
			ForgetPassword forgetPassword = forgetPasswordService.find(searchParam);
			if(forgetPassword != null && userDto.getToken() != null) {
				// password reset by user using token
				if(!forgetPassword.getToken().equalsIgnoreCase(userDto.getToken())) {
					response.setStatus("error");
					response.setMessage("OTP not matched. Please try again.");
					return response;
				} else if (!forgetPassword.isActive()) {
					response.setStatus("error");
					response.setMessage("Link is expired, use forget password to reset password again.");
					return response;					
				} else {
//					user.setPassword(bcryptEncoder.encode(userDto.getPassword()));
//					userService.add(user);
					forgetPassword.setActive(false);
					forgetPasswordService.sendForgetPassword(forgetPassword);
				}
			} else {
				// password reset by user while logged in
				String loggedinUser = SecurityContextHolder.getContext().getAuthentication().getName();
				System.out.println("userName system : "+loggedinUser);
				System.out.println("userName request : "+username);
				if(!loggedinUser.equalsIgnoreCase(username)) {
					response.setStatus("error");
					response.setMessage("Authentication failed.");
					return response;
				} else if(!userAuthenticated(username, userDto.getOldPassword())) {
					response.setStatus("error");
					response.setMessage("Exsiting password is incorrect.");
					return response;
				} else {
//					user.setPassword(bcryptEncoder.encode(userDto.getPassword()));
//					userService.add(user);	
				}
			}
			user.setPassword(bcryptEncoder.encode(userDto.getPassword()));
			userService.add(user);
			response.setStatus("success");
			response.setMessage("Password updated successfully.");
		} catch(ServiceException ex) {
			throw new FacadeException(ex.getMessage());
		} catch(Exception ex) {
			throw new FacadeException(ex.getMessage());
		}
		return response;
	}
	
	private boolean userAuthenticated(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	private UserModal findUserExists(UserDto userDto) {
		UserModal user =  userService.loadUserByEmailOrPhone(userDto.getEmail());
		if(user != null) {
			return user;
		}
		user = userService.loadUserByEmailOrPhone(userDto.getPhone());
		if(user != null) {
			return user;
		}
		return null;
	}
	
	private UserInvitation getUserInvitation(UserDto userDto) throws ServiceException{
		SearchParam searchParam = new SearchParam();
		searchParam.getSearchMap().put("email", userDto.getEmail());
		searchParam.getSearchMap().put("invitationToken", userDto.getToken());
		UserInvitation invitation = invitationService.getUserInvitation(searchParam);
		if(invitation != null) {
			return invitation;
		}
		searchParam = new SearchParam();
		searchParam.getSearchMap().put("phone", userDto.getPhone());
		searchParam.getSearchMap().put("invitationToken", userDto.getToken());
		invitation = invitationService.getUserInvitation(searchParam);
		if(invitation != null) {
			return invitation;
		}
		return null;
	}
}
