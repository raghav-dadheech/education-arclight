package com.study.question.converter;

import org.springframework.stereotype.Component;

import com.study.question.dto.ImagesDto;
import com.study.question.entity.Images;

@Component
public class ImagesConvertor {

	public ImagesDto convert(Images source, ImagesDto target) {
		target.setId(source.getId());
		target.setImageFormat(source.getImageFormate());
		target.setQuestionImage(source.getQuestionImage());
		return target;
	}
}
