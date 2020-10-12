package com.study.question.converter;

import org.springframework.stereotype.Component;

import com.study.question.dto.DifficultyLevelDto;
import com.study.question.entity.DifficultyLevel;

@Component
public class DifficultyLevelConvertor {

	public DifficultyLevel convert(DifficultyLevelDto source, DifficultyLevel target) {
		if(source == null) return target;
		target.setId(source.getId());
		target.setName(source.getName());
		return target;
	}
	
	public DifficultyLevelDto convert(DifficultyLevel source, DifficultyLevelDto target) {
		if(source == null) return target;
		target.setId(source.getId());
		target.setName(source.getName());
		return target;
	}
}
