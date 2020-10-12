package com.study.question.entity.facade;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.study.components.ExamFilter;
import com.study.exceptions.FacadeException;
import com.study.exceptions.ServiceException;
import com.study.question.converter.ExamConvertor;
import com.study.question.converter.QuestionnaireConverter;
import com.study.question.dao.SearchParam;
import com.study.question.dto.Exam;
import com.study.question.dto.ExamDto;
import com.study.question.dto.ExamPaperDto;
import com.study.question.dto.ExamPaperQuestionsDto;
import com.study.question.dto.QuestionnaireDto;
import com.study.question.dto.QuestionnaireListDto;
import com.study.question.entity.ExamPaper;
import com.study.question.entity.ExamPaperQuestions;
import com.study.question.entity.QuestionnaireModel;
import com.study.question.service.ExamPaperQuestionsService;
import com.study.question.service.ExamPaperService;
import com.study.question.service.QuestionnaireService;

@Component
public class DefaultExamPaperFacade implements ExamPaperFacade {

	@Autowired
	private QuestionnaireService questionnaireService;

	@Autowired
	private QuestionnaireConverter questionnaireConverter;

	@Autowired
	private ExamConvertor examConvertor;

	@Autowired
	private ExamFilter examFilter;

	@Autowired
	private ExamPaperService examPaperService;

	@Autowired
	private ExamPaperQuestionsService examPaperQuestionsService;
	
	@Autowired
	private QuestionnaireFacade questionnaireFacade;

	@Override
	public void addExamPaper(ExamPaperDto examPaper) throws FacadeException {
		try {
			examPaperService.addExamPaper(examConvertor.convert(examPaper, new ExamPaper()));
		} catch (ServiceException e) {
			throw new FacadeException(e.getMessage());
		}
	}

	@Override
	public List<ExamPaperDto> listExamPapers() throws FacadeException {
		List<ExamPaperDto> papers = new ArrayList<>();
		try {
			List<ExamPaper> examPapers = examPaperService.listAll();
			examPapers.forEach(paper -> {
				papers.add(examConvertor.convert(paper, new ExamPaperDto()));
			});
		} catch (ServiceException e) {
			throw new FacadeException(e.getMessage());
		}
		return papers;
	}

	@Override
	public void addExamPaperQuestions(ExamPaperQuestionsDto examPaperQuestions) throws FacadeException {
		try {
			ExamPaperDto examPaper = examPaperQuestions.getExamPaper();
			ExamPaper exam = null;
			if (examPaper.getId() == 0) {
				exam = new ExamPaper();
				examConvertor.convert(examPaper, exam);
			} else {
				exam = examPaperService.getExamPaper(examPaper.getId());
			}
			List<Long> tempIds = exam.getQuestions().stream().map(x -> x.getQuestion().getId())
					.collect(Collectors.toList());
			List<Long> questionIds = examPaperQuestions.getQuestionIds();
			for (int i = 0; i < questionIds.size(); i++) {
				if (tempIds.contains(questionIds.get(i)))
					continue;
				QuestionnaireModel question = questionnaireService.getQuestion(questionIds.get(i));
				ExamPaperQuestions q = new ExamPaperQuestions();
				q.setExamPaper(exam);
				q.setQuestion(question);
//				q.setExamPaper(exam);
				exam.getQuestions().add(q);
			}
			examPaperService.addExamPaper(exam);
		} catch (Exception e) {
			throw new FacadeException(e.getMessage());
		}
	}

	@Override
	public QuestionnaireListDto listExamPaperQuestions(ExamDto examDto) throws FacadeException {
		try {
			SearchParam searchParam = new SearchParam();
			searchParam.getSearchMap().put("examPaper", examPaperService.getExamPaper(examDto.getExamPaper().getId()));
//			searchParam.getSearchMap().put("question.enabled", true);
			List<ExamPaperQuestions> examQuestions = examPaperQuestionsService.list(searchParam);
			List<QuestionnaireModel> questions = examQuestions.stream()
					.filter(examPaperQuestions -> examPaperQuestions.getQuestion().getEnabled())
					.map(ExamPaperQuestions::getQuestion).collect(Collectors.toList());
//			List<QuestionnaireModel> questions = examFilter.filter(tempQuestions, examDto);
			List<QuestionnaireDto> questionDtos = new ArrayList<>(0);
			questions.forEach(source -> {
				QuestionnaireDto target = new QuestionnaireDto();
				questionnaireConverter.convert(source, target);
				questionDtos.add(target);
			});
			QuestionnaireListDto response = new QuestionnaireListDto();
			response.setQuestions(questionDtos);
			response.setQuestionImages(questionnaireFacade.setQuestionInages(questionDtos));
			return response;
		} catch (ServiceException e) {
			e.printStackTrace();
			throw new FacadeException("Failed to fetch question", e);
		}
	}

	@Override
	public QuestionnaireListDto listExamPracticeQuestions(ExamDto examDto) throws FacadeException {
		try {
			Exam exam = examConvertor.convert(examDto, new Exam());
			SearchParam searchParam = new SearchParam();
			searchParam.getSearchMap().put("enabled", true);
			searchParam.getSearchMap().put("subject", exam.getSubject());
			if (exam.getChapters().size() > 0)
				searchParam.getSearchMap().put("chapter", exam.getChapters());
			if (exam.getQuestionTypes().size() > 0)
				searchParam.getSearchMap().put("questionType", exam.getQuestionTypes());
			List<QuestionnaireModel> tempQuestions = questionnaireService.listAll(searchParam);
			List<QuestionnaireModel> questions = examFilter.filter(tempQuestions, examDto);
			List<QuestionnaireDto> questionDtos = new ArrayList<>(0);
			questions.forEach(source -> {
				QuestionnaireDto target = new QuestionnaireDto();
				questionnaireConverter.convert(source, target);
				questionDtos.add(target);
			});
			QuestionnaireListDto response = new QuestionnaireListDto();
			response.setQuestions(questionDtos);
			response.setQuestionImages(questionnaireFacade.setQuestionInages(questionDtos));
			return response;
		} catch (ServiceException e) {
			e.printStackTrace();
			throw new FacadeException("Failed to fetch question", e);
		}
	}

	@Override
	public List<ExamPaperDto> deleteExamPaper(long paperId) throws FacadeException {
		try {
			examPaperService.deleteExamPaper(paperId);
			return listExamPapers();
		} catch (ServiceException e) {
			throw new FacadeException(e.getMessage());
		}
	}

	@Override
	public ExamPaperDto getExamPaper(ExamDto examDto) throws FacadeException {
		try {
			ExamPaper exam = examPaperService.getExamPaper(examDto.getExamPaper().getId());
			ExamPaperDto dto = examConvertor.convert(exam, new ExamPaperDto());
			Set<ExamPaperQuestions> questions = exam.getQuestions();
			questions.stream().filter(q->q.getQuestion().getEnabled()).forEach(q -> {
				dto.getQuestions().add(questionnaireConverter.convert(q.getQuestion(), new QuestionnaireDto()));
			});
			dto.setQuestionImages(questionnaireFacade.setQuestionInages(dto.getQuestions()));
			return dto;
		} catch (ServiceException e) {
			throw new FacadeException(e.getMessage());
		}
	}

	@Override
	public ExamPaperDto deleteExamPaperQuestions(ExamDto examDto) throws FacadeException {
		try {
			ExamPaper examPaper = examPaperService.getExamPaper(examDto.getExamPaper().getId());
			List<QuestionnaireDto> questions = examDto.getExamPaper().getQuestions();
			List<Long> deleteIds = questions.stream().map(QuestionnaireDto::getId).collect(Collectors.toList());
			Set<ExamPaperQuestions> temp = examPaper.getQuestions().stream().filter(q -> {
				return !deleteIds.contains(q.getQuestion().getId());
			}).collect(Collectors.toSet());
			examPaper.setQuestions(temp);
			examPaperService.addExamPaper(examPaper);
			return getExamPaper(examDto);
		} catch (Exception e) {
			throw new FacadeException(e.getMessage());
		}
	}

}
