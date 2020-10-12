package com.study.question.dto;

import java.util.List;
import java.util.Set;

public class QuestionProperty {

	private long id;

	private Set<SubjectDto> subjects;

	private Set<DifficultyLevelDto> difficultyLevels;

	private Set<QuestionTypeDto> questionTypes;

	private QuestionCounterDto counter;

	private QuestionSettingDto questionSettings;

	private List<ExamPaperDto> examPapers;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Set<SubjectDto> getSubjects() {
		return subjects;
	}

	public void setSubjects(Set<SubjectDto> subjects) {
		this.subjects = subjects;
	}

	public Set<DifficultyLevelDto> getDifficultyLevels() {
		return difficultyLevels;
	}

	public void setDifficultyLevels(Set<DifficultyLevelDto> difficultyLevels) {
		this.difficultyLevels = difficultyLevels;
	}

	public Set<QuestionTypeDto> getQuestionTypes() {
		return questionTypes;
	}

	public void setQuestionTypes(Set<QuestionTypeDto> questionTypes) {
		this.questionTypes = questionTypes;
	}

	public QuestionCounterDto getCounter() {
		return counter;
	}

	public void setCounter(QuestionCounterDto counter) {
		this.counter = counter;
	}

	public QuestionSettingDto getQuestionSettings() {
		return questionSettings;
	}

	public void setQuestionSettings(QuestionSettingDto questionSettings) {
		this.questionSettings = questionSettings;
	}

	public List<ExamPaperDto> getExamPapers() {
		return examPapers;
	}

	public void setExamPapers(List<ExamPaperDto> examPapers) {
		this.examPapers = examPapers;
	}

}
