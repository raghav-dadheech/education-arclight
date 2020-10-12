package com.study.user.dao;

import java.util.List;

import com.study.exceptions.DaoException;
import com.study.question.dao.SearchParam;
import com.study.user.entity.OrganisationModal;

public interface OrganisationDao {

	public OrganisationModal get(Long organisationId) throws DaoException;
	
	public OrganisationModal get(SearchParam searchParam) throws DaoException;
	
	public List<OrganisationModal> list() throws DaoException;
}
