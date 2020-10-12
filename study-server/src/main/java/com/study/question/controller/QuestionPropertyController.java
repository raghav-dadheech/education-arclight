package com.study.question.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.study.question.entity.facade.QuestionPropertyFacade;

@RestController
@RequestMapping("/question-property")
public class QuestionPropertyController {

	@Autowired
	private QuestionPropertyFacade questionPropertyFacade;
	
	@GetMapping(value = "/properties")
	public ResponseEntity<?> getProperties() throws Exception {
		return ResponseEntity.ok(questionPropertyFacade.questionProperty());
	}
}
