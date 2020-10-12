package com.study.question.converter;

import org.springframework.stereotype.Component;

import com.study.question.dto.QuestionTypeDto;
import com.study.question.entity.QuestionType;

@Component
public class QuestionTypeConvertor {

	public QuestionType convert(QuestionTypeDto source, QuestionType target) {
		if(source == null) return target;
		target.setId(source.getId());
		target.setName(source.getName());
		return target;
	}
	
	public QuestionTypeDto convert(QuestionType source, QuestionTypeDto target) {
		if(source == null) return target;
		target.setId(source.getId());
		target.setName(source.getName());
		return target;
	}
}
