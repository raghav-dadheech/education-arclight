package com.study.question.entity.facade;

import java.util.List;

import com.study.exceptions.FacadeException;
import com.study.question.dto.ExamDto;
import com.study.question.dto.ExamPaperDto;
import com.study.question.dto.ExamPaperQuestionsDto;
import com.study.question.dto.QuestionnaireDto;
import com.study.question.dto.QuestionnaireListDto;

public interface ExamPaperFacade {

	public void addExamPaper(ExamPaperDto examPaper) throws FacadeException;
	
	public List<ExamPaperDto> listExamPapers() throws FacadeException;
	
	public void addExamPaperQuestions(ExamPaperQuestionsDto examPaperQuestions) throws FacadeException;
	
	public QuestionnaireListDto listExamPaperQuestions(ExamDto examDto) throws FacadeException;
	
	public QuestionnaireListDto listExamPracticeQuestions(ExamDto examDto) throws FacadeException;
	
	public List<ExamPaperDto> deleteExamPaper(final long paperId) throws FacadeException;
	
	public ExamPaperDto getExamPaper(ExamDto examDto) throws FacadeException;
	
	public ExamPaperDto deleteExamPaperQuestions(final ExamDto examDto) throws FacadeException;
}
