package com.study.question.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "questionsetting")
public class QuestionSetting {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;

	@ManyToOne
	private Subject subject;

	@ManyToOne
	private Chapter chapter;

	@ManyToOne
	private QuestionType questionType;

	@ManyToOne
	private DifficultyLevel difficultyLevel;

	@Column(name = "remember", columnDefinition = "boolean default true", nullable = false)
	private Boolean rememberQuestionProperties;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	public Chapter getChapter() {
		return chapter;
	}

	public void setChapter(Chapter chapter) {
		this.chapter = chapter;
	}

	public QuestionType getQuestionType() {
		return questionType;
	}

	public void setQuestionType(QuestionType questionType) {
		this.questionType = questionType;
	}

	public DifficultyLevel getDifficultyLevel() {
		return difficultyLevel;
	}

	public void setDifficultyLevel(DifficultyLevel difficultyLevel) {
		this.difficultyLevel = difficultyLevel;
	}

	public Boolean getRememberQuestionProperties() {
		return rememberQuestionProperties;
	}

	public void setRememberQuestionProperties(Boolean rememberQuestionProperties) {
		this.rememberQuestionProperties = rememberQuestionProperties;
	}

}
