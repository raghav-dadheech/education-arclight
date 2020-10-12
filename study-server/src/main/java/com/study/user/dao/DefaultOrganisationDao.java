package com.study.user.dao;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.study.exceptions.DaoException;
import com.study.question.dao.GenericDAOImpl;
import com.study.question.dao.SearchParam;
import com.study.user.entity.OrganisationModal;

@Repository
public class DefaultOrganisationDao extends GenericDAOImpl<OrganisationModal> implements OrganisationDao {

	@Override
	public OrganisationModal get(Long organisationId) throws DaoException {
		try {
			return findById(organisationId);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException("Failed to find organisation with id "+organisationId,e);
		}
	}
	
	@Override
	public OrganisationModal get(SearchParam searchParam) throws DaoException {
		try {
			return findBy(searchParam);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException("Failed to find organisation",e);
		}
	}
	
	@Override
	public List<OrganisationModal> list() throws DaoException {
		try {
			return findAll();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException("Failed to find organisations ",e);
		}
	}

}
