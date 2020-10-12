package com.study.question.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.study.app.ApiResponse;
import com.study.service.EmailService;

@RestController
@RequestMapping("/authenticate/test/")
public class TestController {

	@Autowired
	private EmailService emailService;
	
	@PostMapping(value = "/email")
	public ResponseEntity<?> getQuestions(@RequestParam final String message) throws Exception {
		Map<String, Object> model = new LinkedHashMap<>();
		model.put("message", message);
		emailService.sendEmail("raghavdadheech@gmail.com", "Education Arclight", model);
		return ResponseEntity.ok(new ApiResponse("Success", "Mail Send successfully."));
	}
}
