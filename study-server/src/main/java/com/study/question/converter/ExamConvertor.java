package com.study.question.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.study.question.dto.Exam;
import com.study.question.dto.ExamDto;
import com.study.question.dto.ExamPaperDto;
import com.study.question.entity.Chapter;
import com.study.question.entity.DifficultyLevel;
import com.study.question.entity.ExamPaper;
import com.study.question.entity.QuestionType;
import com.study.question.entity.Subject;

@Component
public class ExamConvertor {

	@Autowired
	private SubjectConvertor subjectConvertor;
	
	@Autowired
	private ChapterConvertor chapterConvertor;
	
	@Autowired
	private QuestionTypeConvertor questionTypeConvertor;
	
	@Autowired
	private DifficultyLevelConvertor difficultyLevelConvertor;
	
	
	public Exam convert(ExamDto source, Exam target) {
		
		List<Chapter> chapters = new ArrayList<Chapter>();
		source.getChapters().forEach(ch->{
			chapters.add(chapterConvertor.convert(ch, new Chapter()));
		});
		
		List<QuestionType> questionTypes = new ArrayList<>();
		source.getQuestionTypes().forEach(qt->{
			questionTypes.add(questionTypeConvertor.convert(qt, new QuestionType()));
		});
		
		List<DifficultyLevel> difficultyLevels = new ArrayList<>();
		source.getDifficultyLevels().forEach(dl->{
			difficultyLevels.add(difficultyLevelConvertor.convert(dl, new DifficultyLevel()));
		});
		
		target.setPageSize(source.getPageSize());
		target.setSubject(subjectConvertor.convert(source.getSubject(), new Subject()));
		target.setChapters(chapters);
		target.setQuestionTypes(questionTypes);
		target.setDifficultyLevels(difficultyLevels);
		return target;
	}
	
	public ExamPaper convert(ExamPaperDto source, ExamPaper target) {
		target.setId(source.getId());
		target.setName(source.getName());
		return target;
	}
	
	public ExamPaperDto convert(ExamPaper source, ExamPaperDto target) {
		target.setId(source.getId());
		target.setName(source.getName());
		return target;
	}
}
