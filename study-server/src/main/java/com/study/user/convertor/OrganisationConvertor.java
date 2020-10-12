package com.study.user.convertor;

import org.springframework.stereotype.Component;

import com.study.user.dto.OrganisationDto;
import com.study.user.entity.OrganisationModal;

@Component
public class OrganisationConvertor {

	public OrganisationDto convert(OrganisationModal source, OrganisationDto target) {
		if(source == null || target == null)
			return target;
		
		target.setActive(source.isActive());
		target.setAddressLine1(source.getAddressLine1());
		target.setAddressLine2(source.getAddressLine2());
		target.setArea(source.getArea());
		target.setName(source.getName());
		target.setCity(source.getCity());
		target.setCode(source.getCode());
		target.setCountry(source.getCountry());
		target.setId(source.getId());
		target.setJoiningDate(source.getJoiningDate());
		target.setPincode(source.getPincode());
		target.setState(source.getState());
		
		return target;
	}
	
	public OrganisationModal convert(OrganisationDto source, OrganisationModal target) {
		if(source == null || target == null)
			return target;
		
		target.setActive(source.isActive());
		target.setAddressLine1(source.getAddressLine1());
		target.setAddressLine2(source.getAddressLine2());
		target.setArea(source.getArea());
		target.setName(source.getName());
		target.setCity(source.getCity());
		target.setCode(source.getCode());
		target.setCountry(source.getCountry());
		target.setId(source.getId());
		target.setJoiningDate(source.getJoiningDate());
		target.setPincode(source.getPincode());
		target.setState(source.getState());
		
		return target;
	}
	
}
