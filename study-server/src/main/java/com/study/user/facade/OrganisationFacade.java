package com.study.user.facade;

import java.util.List;

import com.study.exceptions.FacadeException;
import com.study.user.dto.OrganisationDto;

public interface OrganisationFacade {

	public OrganisationDto get(Long organisationId) throws FacadeException;
	
	public List<OrganisationDto> list() throws FacadeException;
}
