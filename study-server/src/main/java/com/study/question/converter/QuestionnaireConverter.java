package com.study.question.converter;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.study.exceptions.ServiceException;
import com.study.question.dao.SearchParam;
import com.study.question.dto.ChapterDto;
import com.study.question.dto.DifficultyLevelDto;
import com.study.question.dto.QuestionTypeDto;
import com.study.question.dto.QuestionnaireDto;
import com.study.question.dto.SubjectDto;
import com.study.question.entity.Chapter;
import com.study.question.entity.DifficultyLevel;
import com.study.question.entity.QuestionSolutions;
import com.study.question.entity.QuestionType;
import com.study.question.entity.QuestionnaireModel;
import com.study.question.entity.Subject;
import com.study.question.service.QuestionSolutionsService;

@Component
public class QuestionnaireConverter {

	@Autowired
	private SubjectConvertor subjectConvertor;
	
	@Autowired
	private ChapterConvertor chapterConvertor;
	
	@Autowired
	private QuestionTypeConvertor questionTypeConvertor;
	
	@Autowired
	private DifficultyLevelConvertor difficultyLevelConvertor;
	
	@Autowired
	private QuestionSolutionsService questionSolutionsService;
	
	public QuestionnaireModel convert(QuestionnaireDto source, QuestionnaireModel target) {
		if(source == null) return target;
		target.setId(source.getId());
		target.setQuestion(source.getQuestion());
		target.setApproved(source.isApproved());
		
		SubjectDto sourceSubject = source.getSubject();
		ChapterDto sourceChapter = source.getChapter();
		Subject targetSubject = new Subject();
		Chapter targetChapter = new Chapter();
		target.setSubject(subjectConvertor.convert(sourceSubject, targetSubject));
		target.setChapter(chapterConvertor.convert(sourceChapter, targetChapter));
		
		QuestionTypeDto sourceQuestionType = source.getQuestionType();
		DifficultyLevelDto sourceDifficultyLevel = source.getDifficultyLevel();
		QuestionType targetQuestionType = new QuestionType();
		DifficultyLevel targetDifficultyLevel = new DifficultyLevel();
		target.setQuestionType(questionTypeConvertor.convert(sourceQuestionType, targetQuestionType));
		target.setDifficultyLevel(difficultyLevelConvertor.convert(sourceDifficultyLevel, targetDifficultyLevel));
		target.setAnswer(source.getAnswer());
		return target;
	}
	
	public QuestionnaireDto convert(QuestionnaireModel source, QuestionnaireDto target) {
		if(source == null) return target;
		target.setId(source.getId());
		target.setQuestion(source.getQuestion());
		target.setApproved(source.getApproved());
		
		Subject sourceSubject = source.getSubject();
		Chapter sourceChapter = source.getChapter();
		SubjectDto targetSubject = new SubjectDto();
		ChapterDto targetChapter = new ChapterDto();
		targetSubject.setId(sourceSubject.getId());
		targetSubject.setName(sourceSubject.getName());
		target.setSubject(targetSubject);
		target.setChapter(chapterConvertor.convert(sourceChapter, targetChapter));

		QuestionType sourceQuestionType = source.getQuestionType();
		DifficultyLevel sourceDifficultyLevel = source.getDifficultyLevel();
		QuestionTypeDto targetQuestionType = new QuestionTypeDto();
		DifficultyLevelDto targetDifficultyLevel = new DifficultyLevelDto();
		target.setQuestionType(questionTypeConvertor.convert(sourceQuestionType, targetQuestionType));
		target.setDifficultyLevel(difficultyLevelConvertor.convert(sourceDifficultyLevel, targetDifficultyLevel));
		target.setAnswer(source.getAnswer());
		

		
		SearchParam searchParam = new SearchParam();
		searchParam.getSearchMap().put("question", source);
		List<QuestionSolutions> images;
		try {
			images = questionSolutionsService.listSolutionImages(searchParam);
			if(images != null && images.size() > 0) {
				target.setSolution(images.get(0).getImageUrl());
			}
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		
		return target;
	}
	
}
