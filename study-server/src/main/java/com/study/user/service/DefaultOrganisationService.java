package com.study.user.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.study.exceptions.DaoException;
import com.study.exceptions.ServiceException;
import com.study.question.dao.SearchParam;
import com.study.user.dao.OrganisationDao;
import com.study.user.entity.OrganisationModal;

@Service
@Transactional
public class DefaultOrganisationService implements OrganisationService {

	@Autowired
	private OrganisationDao organisationDao;
	
	@Override
	public OrganisationModal get(Long organisationId) throws ServiceException {
		try {
			return organisationDao.get(organisationId);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		}
	}
	
	@Override
	public OrganisationModal get(SearchParam searchParam) throws ServiceException {
		try {
			return organisationDao.get(searchParam);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		}
	}

	@Override
	public List<OrganisationModal> list() throws ServiceException {
		try {
			return organisationDao.list();
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		}
	}

}
