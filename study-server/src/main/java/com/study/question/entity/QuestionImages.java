package com.study.question.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "question_images")
public class QuestionImages {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;
	
	@ManyToOne
	private QuestionnaireModel question;
	
	private String imageUrl;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public QuestionnaireModel getQuestion() {
		return question;
	}

	public void setQuestion(QuestionnaireModel question) {
		this.question = question;
	}

	public String getImageUrl() {
		return imageUrl;
	}
	
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	
}
