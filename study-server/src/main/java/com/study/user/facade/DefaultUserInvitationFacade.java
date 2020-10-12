package com.study.user.facade;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.study.app.StudyUtils;
import com.study.enums.UserInvitationStatus;
import com.study.exceptions.FacadeException;
import com.study.exceptions.ServiceException;
import com.study.question.dao.SearchParam;
import com.study.question.dto.ResponseDto;
import com.study.service.EmailService;
import com.study.user.convertor.UserInvitationConvertor;
import com.study.user.dto.UserInvitationDto;
import com.study.user.entity.OrganisationModal;
import com.study.user.entity.UserInvitation;
import com.study.user.entity.UserModal;
import com.study.user.service.OrganisationService;
import com.study.user.service.UserInvitationService;
import com.study.user.service.UserRoleService;
import com.study.user.service.UserService;

@Component
public class DefaultUserInvitationFacade implements UserInvitationFacade {

	@Autowired
	private UserInvitationService userInvitationService;
	
	@Autowired
	private UserInvitationConvertor userInvitationConvertor;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRoleService userRoleService;
	
	@Autowired
	private OrganisationService organisationService;
	
	@Override
	public ResponseDto sendUserInvitation(UserInvitationDto userInvitationDto) throws FacadeException {
		ResponseDto response = new ResponseDto();
		try {
			String username = null;
			if(userInvitationDto.getEmail() != null) { 
				username = userInvitationDto.getEmail();
			} else if(userInvitationDto.getPhone() != null) {
				username = userInvitationDto.getPhone();
			}
			UserInvitation invitation = null;
			if(username != null) {
				UserModal u = userService.loadUserByEmailOrPhone(username);
				if(u != null) {
					response.setStatus("error");
					response.setMessage("User already exists");
					return response;	
				}
				invitation = userInvitationService.getUserInvitationByEmailOrPhone(username);
				if(invitation != null) {
					userInvitationService.removeInvitation(invitation);
				} 	
			} else {
				response.setStatus("error");
				response.setMessage("Email or password is required");
				return response;
			}
			
			SearchParam searchParam = new SearchParam();
			searchParam.getSearchMap().put("role", userInvitationDto.getRole().getRole());

			String token = StudyUtils.generateOTP();
			
			invitation = new UserInvitation();
			
			invitation.setEmail(userInvitationDto.getEmail());
			invitation.setRole(userRoleService.getUserRole(searchParam));
			invitation.setStatus(UserInvitationStatus.PENDING.getValue());
			invitation.setInvitationToken(token);
			invitation.setPhone(userInvitationDto.getPhone());
			invitation.setKeyword(userInvitationDto.getKeyword());
			
			OrganisationModal orgModal = organisationService.get(userInvitationDto.getOrganisation().getId());
			invitation.setOrganisation(orgModal);
			String userName = SecurityContextHolder.getContext().getAuthentication().getName();
			
			searchParam = new SearchParam();
			searchParam.getSearchMap().put("email", userName);
			UserModal user = userService.get(searchParam);
			invitation.setInvitedBy(user);
			
			userInvitationService.sendInvitation(invitation);
			
			Map<String, Object> model = new LinkedHashMap<String, Object>();
			model.put("token", token);
			emailService.sendInvitationEmail(invitation.getEmail(), EmailService.INVITATION_MAIL_SUBJECT, model);
			response.setStatus("success");
			response.setMessage("User invited successfully.");
			return response;
		} catch (ServiceException e) {
			e.printStackTrace();
			throw new FacadeException(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			throw new FacadeException(e.getMessage());
		}
	}
	
	@Override
	public List<UserInvitationDto> listInvitations() throws FacadeException {
		try {
			SearchParam searchParam = new SearchParam();
			String userName = SecurityContextHolder.getContext().getAuthentication().getName();
			
			searchParam = new SearchParam();
			searchParam.getSearchMap().put("email", userName);
			UserModal user = userService.get(searchParam);
			
			searchParam = new SearchParam();
			searchParam.getSearchMap().put("organisation", user.getOrganisation());
			List<UserInvitation> sourceInvitations = userInvitationService.listInvitations(searchParam);
			List<UserInvitationDto> targets = new ArrayList<>(0);
			sourceInvitations.forEach(source->{
				UserInvitationDto target = new UserInvitationDto();
				userInvitationConvertor.convert(source, target);
				targets.add(target);
			});
			return targets;
		} catch(ServiceException e) {
			e.printStackTrace();
			throw new FacadeException(e.getMessage());
		}
	}
	
	@Override
	public ResponseDto cancelInvitation(Long invitationId) throws FacadeException {
		try {
			ResponseDto response = new ResponseDto();
			SearchParam searchParam = new SearchParam();
			searchParam.getSearchMap().put("id", invitationId);
			UserInvitation invitation = userInvitationService.getUserInvitation(searchParam);
			if(invitation != null) {
				invitation.setActive(false);
				invitation.setStatus(UserInvitationStatus.CANCELLED.getValue());
				userInvitationService.sendInvitation(invitation);
				response.setStatus("success");
				response.setMessage("Invitation cancelled successfully.");
			} else {
				response.setStatus("success");
				response.setMessage("Invitation not found");
			}
			return response;
		} catch(ServiceException e) {
			e.printStackTrace();
			throw new FacadeException(e.getMessage());
		}
	}
}
