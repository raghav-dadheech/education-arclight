package com.study.question.dto;

import java.util.Set;

public class SubjectDto {

	private long id;

	private String name;

	private Set<ChapterDto> chapters;

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

	public Set<ChapterDto> getChapters() {
		return chapters;
	}

	public void setChapters(Set<ChapterDto> chapters) {
		this.chapters = chapters;
	}

}
