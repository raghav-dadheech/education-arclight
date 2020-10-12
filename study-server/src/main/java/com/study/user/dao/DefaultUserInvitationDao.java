package com.study.user.dao;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.study.exceptions.DaoException;
import com.study.question.dao.GenericDAOImpl;
import com.study.question.dao.SearchParam;
import com.study.user.entity.UserInvitation;

@Repository
public class DefaultUserInvitationDao extends GenericDAOImpl<UserInvitation> implements UserInvitationDao {

	@Override
	public void sendInvitation(UserInvitation invitation) throws DaoException {
		try {
			saveOrUpdate(invitation);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException(e.getMessage());
		}
	}

	@Override
	public void removeInvitation(UserInvitation invitation) throws DaoException {
		try {
			remove(invitation);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException(e.getMessage());
		}
	}

	@Override
	public UserInvitation getUserInvitation(SearchParam searchParam) throws DaoException {
		try {
			return findBy(searchParam);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException(e.getMessage());
		}
	}
	
	@Override
	public UserInvitation getUserInvitationByEmailOrPhone(String emailOrPhone) throws DaoException {
		try {
			Session session = getCurrentSession();
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<UserInvitation> query = builder.createQuery(UserInvitation.class);
			Root<UserInvitation> root = query.from(UserInvitation.class);
			query.select(root).where(builder.or(builder.equal(root.get("email"),  emailOrPhone), builder.equal(root.get("phone"), emailOrPhone)));
			List<UserInvitation> users = session.createQuery(query).getResultList();
			return users.size() == 1 ? users.get(0) : null;
		} catch (Exception e) {
			e.printStackTrace();
			throw new DaoException("Failed to get user");
		}
	}

	@Override
	public List<UserInvitation> listInvitations(SearchParam searchParam) throws DaoException {
		try {
			return findAllBy(searchParam);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException(e.getMessage());
		}
	}
}
