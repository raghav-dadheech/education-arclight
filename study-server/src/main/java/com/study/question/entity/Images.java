package com.study.question.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.type.descriptor.sql.LobTypeMappings;

@Entity
@Table(name = "images")
public class Images {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;

	@Column(name = "image_format",length=45)
	private String imageFormate;
	
	@Lob
	@Column(name = "question_image", nullable = false)
	private byte[] questionImage;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getImageFormate() {
		return imageFormate;
	}

	public void setImageFormate(String imageFormate) {
		this.imageFormate = imageFormate;
	}

	public byte[] getQuestionImage() {
		return questionImage;
	}

	public void setQuestionImage(byte[] questionImage) {
		this.questionImage = questionImage;
	}
}
