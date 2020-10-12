package com.study.user.service;

import java.util.List;

import com.study.exceptions.ServiceException;
import com.study.question.dao.SearchParam;
import com.study.user.entity.OrganisationModal;

public interface OrganisationService {

	public OrganisationModal get(Long organisationId) throws ServiceException;
	
	public OrganisationModal get(SearchParam searchParam) throws ServiceException;
	
	public List<OrganisationModal> list() throws ServiceException;
	
	
}
