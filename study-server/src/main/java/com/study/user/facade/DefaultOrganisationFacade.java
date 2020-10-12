package com.study.user.facade;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.study.exceptions.FacadeException;
import com.study.exceptions.ServiceException;
import com.study.user.convertor.OrganisationConvertor;
import com.study.user.dto.OrganisationDto;
import com.study.user.entity.OrganisationModal;
import com.study.user.service.OrganisationService;

@Component
public class DefaultOrganisationFacade implements OrganisationFacade {

	@Autowired
	private OrganisationService organisationService;
	
	@Autowired
	private OrganisationConvertor organisationConvertor;
	
	@Override
	public OrganisationDto get(Long organisationId) throws FacadeException {
		try {
			OrganisationModal source = organisationService.get(organisationId);
			OrganisationDto target = new OrganisationDto();
			organisationConvertor.convert(source, target);
			return target;
		} catch (ServiceException e) {
			e.printStackTrace();
			throw new FacadeException(e.getMessage(), e);
		}
	}

	@Override
	public List<OrganisationDto> list() throws FacadeException {
		try {
			List<OrganisationModal> sourceList = organisationService.list();
			List<OrganisationDto> targetList = new ArrayList<>(0);
			sourceList.forEach(source-> {
				OrganisationDto target = new OrganisationDto();
				organisationConvertor.convert(source, target);
				targetList.add(target);
			});
			return targetList;
		} catch (ServiceException e) {
			e.printStackTrace();
			throw new FacadeException(e.getMessage(), e);
		}
	}

}
