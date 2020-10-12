package com.study.question.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.study.question.dto.ChapterDto;
import com.study.question.dto.DifficultyLevelDto;
import com.study.question.dto.QuestionListFilter;
import com.study.question.dto.QuestionListFilterDto;
import com.study.question.dto.QuestionTypeDto;
import com.study.question.dto.SubjectDto;
import com.study.question.entity.Chapter;
import com.study.question.entity.DifficultyLevel;
import com.study.question.entity.QuestionType;
import com.study.question.entity.Subject;

@Component
public class QuestionFilterConvertor {

	
	@Autowired
	private SubjectConvertor subjectConvertor;
	
	@Autowired
	private ChapterConvertor chapterConvertor;
	
	@Autowired
	private QuestionTypeConvertor questionTypeConvertor;
	
	@Autowired
	private DifficultyLevelConvertor difficultyLevelConvertor;
	
	public QuestionListFilterDto convert(QuestionListFilter source, QuestionListFilterDto target) {
		if(source.getSubject() != null)
			target.setSubject(subjectConvertor.convert(source.getSubject(), new SubjectDto()));
		if(source.getChapter() != null)
			target.setChapter(chapterConvertor.convert(source.getChapter(), new ChapterDto()));
		if(source.getQuestionType() != null)
			target.setQuestionType(questionTypeConvertor.convert(source.getQuestionType(), new QuestionTypeDto()));
		if(source.getDifficultyLevel() != null)
			target.setDifficultyLevel(difficultyLevelConvertor.convert(source.getDifficultyLevel(), new DifficultyLevelDto()));
		target.setApproval(source.getApproval());
		return target;
	}
	
	public QuestionListFilter convert(QuestionListFilterDto source, QuestionListFilter target) {
		if(source.getSubject() != null)
			target.setSubject(subjectConvertor.convert(source.getSubject(), new Subject()));
		if(source.getChapter() != null)
			target.setChapter(chapterConvertor.convert(source.getChapter(), new Chapter()));
		if(source.getQuestionType() != null)
			target.setQuestionType(questionTypeConvertor.convert(source.getQuestionType(), new QuestionType()));
		if(source.getDifficultyLevel() != null)
			target.setDifficultyLevel(difficultyLevelConvertor.convert(source.getDifficultyLevel(), new DifficultyLevel()));
		target.setApproval(source.getApproval());
		return target;
	}
}
