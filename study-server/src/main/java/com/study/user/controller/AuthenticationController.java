package com.study.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.study.config.JwtRequest;
import com.study.config.JwtResponse;
import com.study.config.JwtTokenUtil;
import com.study.question.dto.ResponseDto;
import com.study.user.dto.UserDto;
import com.study.user.facade.UserFacade;
import com.study.user.service.UserService;


@RestController
@RequestMapping("/authenticate")
@CrossOrigin
public class AuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private UserService userDetailsService;
	
	@Autowired
	private UserFacade userFacade;

	@PostMapping(value = "/login")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

		authenticate(authenticationRequest.getUserName(), authenticationRequest.getPassword());

		final UserDetails userDetails = userDetailsService
				.loadUserByUsername(authenticationRequest.getUserName());

		final String token = jwtTokenUtil.generateToken(userDetails);
		UserDto user = userFacade.getLoggedinUser(authenticationRequest.getUserName());
		user.setToken(token);
//		return ResponseEntity.ok(new JwtResponse(token));
		return ResponseEntity.ok(user);
	}
	
	@PostMapping(value = "/register")
	public ResponseEntity<?> register(@RequestBody UserDto userDto) throws Exception {

		ResponseDto responseDto = userFacade.register(userDto);
		return ResponseEntity.ok(responseDto);
	}
	
	@PostMapping(value = "/forget-password")
	public ResponseEntity<?> forgetPassword(@RequestBody UserDto userDto) throws Exception {

		ResponseDto responseDto = userFacade.forgetPassword(userDto);
		return ResponseEntity.ok(responseDto);
	}
	
	@PostMapping(value = "/reset-password")
	public ResponseEntity<?> resetPassword(@RequestBody UserDto userDto) throws Exception {

		ResponseDto responseDto = userFacade.resetPassword(userDto);
		return ResponseEntity.ok(responseDto);
	}

	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
}