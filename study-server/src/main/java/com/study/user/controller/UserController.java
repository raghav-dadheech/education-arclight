package com.study.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.study.question.dto.ResponseDto;
import com.study.user.dto.UserInvitationDto;
import com.study.user.facade.OrganisationFacade;
import com.study.user.facade.UserInvitationFacade;
import com.study.user.facade.UserRoleFacade;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {

	@Autowired
	private UserInvitationFacade userInvitationFacade;
	
	@Autowired
	private OrganisationFacade organisationFacade;
	
	@Autowired
	private UserRoleFacade userRoleFacade;
	
	@PostMapping(value = "/invite")
	public ResponseEntity<?> invite(@RequestBody UserInvitationDto invitationDto) throws Exception {
		ResponseDto response = userInvitationFacade.sendUserInvitation(invitationDto);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping(value = "/list-invitations")
	public ResponseEntity<?> listInvitations() throws Exception {
		return ResponseEntity.ok(userInvitationFacade.listInvitations());
	}
	
	@GetMapping(value = "/cancel-invitation")
	public ResponseEntity<?> cancelInvitation(@RequestParam final Long invitationId) throws Exception {
		return ResponseEntity.ok(userInvitationFacade.cancelInvitation(invitationId));
	}
	
	@GetMapping(value = "/list-organisations")
	public ResponseEntity<?> listOrganisations() throws Exception {
		return ResponseEntity.ok(organisationFacade.list());
	}
	
	@GetMapping(value = "/get-organisation")
	public ResponseEntity<?> getOrganisation(@RequestParam final Long organisationId) throws Exception {
		return ResponseEntity.ok(organisationFacade.get(organisationId));
	}
	
	@GetMapping(value = "/list-userroles")
	public ResponseEntity<?> getRoles() throws Exception {
		return ResponseEntity.ok(userRoleFacade.getUserRoles());
	}
	
	
}
