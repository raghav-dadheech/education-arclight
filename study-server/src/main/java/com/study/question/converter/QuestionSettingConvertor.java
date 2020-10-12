package com.study.question.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.study.question.dto.ChapterDto;
import com.study.question.dto.DifficultyLevelDto;
import com.study.question.dto.QuestionSettingDto;
import com.study.question.dto.QuestionTypeDto;
import com.study.question.dto.SubjectDto;
import com.study.question.entity.Chapter;
import com.study.question.entity.DifficultyLevel;
import com.study.question.entity.QuestionSetting;
import com.study.question.entity.QuestionType;
import com.study.question.entity.Subject;

@Component
public class QuestionSettingConvertor {

	@Autowired
	private QuestionTypeConvertor questionTypeConvertor;

	@Autowired
	private DifficultyLevelConvertor difficultyLevelConvertor;

	@Autowired
	private SubjectConvertor subjectConvertor;

	@Autowired
	private ChapterConvertor chapterConvertor;

	public QuestionSettingDto convert(QuestionSetting source, QuestionSettingDto target) {
		if (source == null)
			return target;
		target.setId(source.getId());
		target.setSubject(subjectConvertor.convert(source.getSubject(), new SubjectDto()));
		target.setChapter(chapterConvertor.convert(source.getChapter(), new ChapterDto()));
		target.setQuestionType(questionTypeConvertor.convert(source.getQuestionType(), new QuestionTypeDto()));
		target.setDifficultyLeval(
				difficultyLevelConvertor.convert(source.getDifficultyLevel(), new DifficultyLevelDto()));
		target.setRememberQuestionProperties(source != null && source.getRememberQuestionProperties() != null
				? source.getRememberQuestionProperties()
				: false);
		return target;
	}

	public QuestionSetting convert(QuestionSettingDto source, QuestionSetting target) {
		if (source == null)
			return target;
		target.setId(source.getId());
		target.setSubject(subjectConvertor.convert(source.getSubject(), new Subject()));
		target.setChapter(chapterConvertor.convert(source.getChapter(), new Chapter()));
		target.setQuestionType(questionTypeConvertor.convert(source.getQuestionType(), new QuestionType()));
		target.setDifficultyLevel(difficultyLevelConvertor.convert(source.getDifficultyLeval(), new DifficultyLevel()));
		target.setRememberQuestionProperties(source.isRememberQuestionProperties());
		return target;
	}
}
