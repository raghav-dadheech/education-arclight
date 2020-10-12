package com.study.question.entity.facade;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.study.exceptions.FacadeException;
import com.study.exceptions.ServiceException;
import com.study.question.converter.ChapterConvertor;
import com.study.question.converter.DifficultyLevelConvertor;
import com.study.question.converter.ExamConvertor;
import com.study.question.converter.QuestionSettingConvertor;
import com.study.question.converter.QuestionTypeConvertor;
import com.study.question.converter.SubjectConvertor;
import com.study.question.dto.ChapterDto;
import com.study.question.dto.DifficultyLevelDto;
import com.study.question.dto.ExamPaperDto;
import com.study.question.dto.QuestionCounterDto;
import com.study.question.dto.QuestionProperty;
import com.study.question.dto.QuestionSettingDto;
import com.study.question.dto.QuestionTypeDto;
import com.study.question.dto.SubjectDto;
import com.study.question.entity.Chapter;
import com.study.question.entity.DifficultyLevel;
import com.study.question.entity.ExamPaper;
import com.study.question.entity.QuestionType;
import com.study.question.entity.Subject;
import com.study.question.service.ChapterService;
import com.study.question.service.DifficultyLevelService;
import com.study.question.service.ExamPaperService;
import com.study.question.service.QuestionSettingService;
import com.study.question.service.QuestionTypeService;
import com.study.question.service.SubjectService;

@Component
public class DefaultQuestionPropertyFacade implements QuestionPropertyFacade {

	@Autowired
	private SubjectService subjectService;

	@Autowired
	private ChapterService chapterService;

	@Autowired
	private QuestionTypeService questionTypeService;

	@Autowired
	private DifficultyLevelService difficultyLevelService;

	@Autowired
	private SubjectConvertor subjectConvertor;

	@Autowired
	private ChapterConvertor chapterConvertor;

	@Autowired
	private QuestionTypeConvertor questionTypeConvertor;

	@Autowired
	private DifficultyLevelConvertor difficultyLevelConvertor;

	@Autowired
	private QuestionnaireFacade questionnaireFacade;

	@Autowired
	private QuestionSettingService questionSettingService;

	@Autowired
	private QuestionSettingConvertor questionSettingConvertor;
	
	@Autowired
	private ExamPaperService examPaperService;
	
	@Autowired
	private ExamConvertor examConvertor;

	@Override
	public QuestionProperty questionProperty() throws FacadeException {
		try {
			QuestionProperty questionProperty = new QuestionProperty();

			List<Subject> subjects = subjectService.listSubjects();
			Set<SubjectDto> subjectDtos = new LinkedHashSet<>(0);
			if (subjects != null) {
				subjects.forEach(subject -> {
					SubjectDto subjectDto = new SubjectDto();
					subjectDtos.add(subjectConvertor.convert(subject, subjectDto));
					try {
						List<Chapter> chapters = chapterService.listChaptersBySubject(subject);
						Set<ChapterDto> chaptersDtos = new LinkedHashSet<>();
						chapters.forEach(chapter -> {
							ChapterDto chapterDto = new ChapterDto();
							chapterConvertor.convert(chapter, chapterDto);
							chaptersDtos.add(chapterDto);
						});
						subjectDto.setChapters(chaptersDtos);
					} catch (ServiceException e) {
						e.printStackTrace();
					}
				});
			}

			List<QuestionType> questionTypes = questionTypeService.listAll();
			Set<QuestionTypeDto> questionTypeDtos = new LinkedHashSet<>();
			questionTypes.forEach(questionType -> {
				QuestionTypeDto questionTypeDto = new QuestionTypeDto();
				questionTypeConvertor.convert(questionType, questionTypeDto);
				questionTypeDtos.add(questionTypeDto);
			});

			List<DifficultyLevel> difficultyLevels = difficultyLevelService.listAll();
			Set<DifficultyLevelDto> difficultyLevelDtos = new LinkedHashSet<>();
			difficultyLevels.forEach(difficultyLevel -> {
				DifficultyLevelDto difficultyLevelDto = new DifficultyLevelDto();
				difficultyLevelConvertor.convert(difficultyLevel, difficultyLevelDto);
				difficultyLevelDtos.add(difficultyLevelDto);
			});

			QuestionCounterDto questionCounterDto = questionnaireFacade.getQuestionCounter();
			QuestionSettingDto questionSettingDto = questionSettingConvertor
					.convert(questionSettingService.getQuestionSetting(), new QuestionSettingDto());

			List<ExamPaper> examPapers = examPaperService.listAll();
			System.out.println("examPapers.size  "+examPapers.size());
			List<ExamPaperDto> examPaperDtos = new ArrayList<>();
			examPapers.forEach(paper->{
				examPaperDtos.add(examConvertor.convert(paper, new ExamPaperDto()));
			});
			
			questionProperty.setQuestionTypes(questionTypeDtos);
			questionProperty.setDifficultyLevels(difficultyLevelDtos);
			questionProperty.setSubjects(subjectDtos);
			questionProperty.setCounter(questionCounterDto);
			questionProperty.setQuestionSettings(questionSettingDto);
			questionProperty.setExamPapers(examPaperDtos);
			return questionProperty;
		} catch (ServiceException ex) {
			throw new FacadeException(ex.getMessage());
		}
	}

}
