package com.study.question.entity.facade;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.study.exceptions.FacadeException;
import com.study.question.dto.ImagesDto;
import com.study.question.dto.LongListDto;
import com.study.question.dto.QuestionCounterDto;
import com.study.question.dto.QuestionListFilterDto;
import com.study.question.dto.QuestionnaireDto;
import com.study.question.dto.QuestionnaireListDto;

public interface QuestionnaireFacade {

	public QuestionnaireDto addQuestionnaire(QuestionnaireDto questionnaireDto) throws FacadeException;
	
	public QuestionnaireListDto listAll(final QuestionListFilterDto filter) throws FacadeException;
	
	public QuestionnaireListDto listAllTrash() throws FacadeException;
	
	public QuestionnaireDto getQuestion(final Long questionId) throws FacadeException;
	
	public QuestionCounterDto getQuestionCounter() throws FacadeException;

	public void deleteQuestion(List<Long> questionList) throws FacadeException;
	
	public void restoreQuestion(List<Long> questionList) throws FacadeException;
	
	public void updateApprovalStatus(LongListDto object) throws FacadeException;
	
	public Map<Long, Map<String, ImagesDto>> setQuestionInages(List<QuestionnaireDto> questions) throws FacadeException;
	
	public QuestionnaireDto uploadSolutions(MultipartFile file,String fileName, Long questionId) throws FacadeException;

	public QuestionnaireDto deleteSolution(Long questionId) throws FacadeException;
}
