package com.study.question.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="exam_paper_questions")
public class ExamPaperQuestions {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;

	@ManyToOne
//	@Column(name="question_id")
	@JoinColumn(name="question_id")
	private QuestionnaireModel question;
	
	@ManyToOne
//	@JoinColumn(name="examPaper_id")
    private ExamPaper examPaper;

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

	public ExamPaper getExamPaper() {
		return examPaper;
	}

	public void setExamPaper(ExamPaper examPaper) {
		this.examPaper = examPaper;
	}
}
