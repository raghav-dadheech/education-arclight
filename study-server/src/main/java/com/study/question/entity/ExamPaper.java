package com.study.question.entity;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="exam_paper")
public class ExamPaper {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;

	@Column(name = "name",length=100, nullable = false, unique=true)
	private String name;
	
	@Column(name = "enabled",  columnDefinition = "boolean default true")
	private Boolean enabled = true;
	
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name="examPaper_id")
	private Set<ExamPaperQuestions> questions = new LinkedHashSet<>();


	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public Boolean getEnabled() {
		return enabled;
	}


	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}


	public Set<ExamPaperQuestions> getQuestions() {
		return questions;
	}


	public void setQuestions(Set<ExamPaperQuestions> questions) {
		this.questions = questions;
	}
	
}
