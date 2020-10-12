package com.study.question.converter;

import org.springframework.stereotype.Component;

import com.study.question.dto.ChapterDto;
import com.study.question.entity.Chapter;

@Component
public class ChapterConvertor {

	public Chapter convert(ChapterDto source, Chapter target) {
		if(source == null) return target;
		target.setId(source.getId());
		target.setName(source.getName());
		return target;
	}
	
	public ChapterDto convert(Chapter source, ChapterDto target) {
		if(source == null) return target;
		target.setId(source.getId());
		target.setName(source.getName());
		return target;
	}
}
