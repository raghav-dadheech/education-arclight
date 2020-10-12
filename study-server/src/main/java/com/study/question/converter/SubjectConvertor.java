package com.study.question.converter;

import java.util.LinkedHashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.study.question.dto.ChapterDto;
import com.study.question.dto.SubjectDto;
import com.study.question.entity.Chapter;
import com.study.question.entity.Subject;

@Component
public class SubjectConvertor {

	@Autowired
	private ChapterConvertor chapterConvertor;
	
	public Subject convert(SubjectDto source, Subject target) {
		if(source == null) return target;
		target.setId(source.getId());
		target.setName(source.getName());
		Set<ChapterDto> sourceChapters = source.getChapters();
		Set<Chapter> targetChapters = new LinkedHashSet<>();
//		sourceChapters.forEach(sourceChapter->{
//			Chapter targetChapter = new Chapter();
//			chapterConvertor.convert(sourceChapter, targetChapter);
//			targetChapters.add(targetChapter);
//		});
		target.setChapters(targetChapters);
		return target;
	}
	
	public SubjectDto convert(Subject source, SubjectDto target) {
		if(source == null) return target;
		target.setId(source.getId());
		target.setName(source.getName());
		Set<Chapter> sourceChapters = source.getChapters();
		Set<ChapterDto> targetChapters = new LinkedHashSet<>();
//		sourceChapters.forEach(sourceChapter->{
//			ChapterDto targetChapter = new ChapterDto();
//			chapterConvertor.convert(sourceChapter, targetChapter);
//			targetChapters.add(targetChapter);
//		});
		target.setChapters(targetChapters);
		return target;
	}
}
