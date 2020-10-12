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
import com.study.user.entity.UserModal;

@Repository
public class DefaultUserDao extends GenericDAOImpl<UserModal> implements UserDao {

	@Override
	public void add(UserModal user) throws DaoException {
		try {
			saveOrUpdate(user);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException("Failed to add user");
		}
	}

	@Override
	public List<UserModal> list(SearchParam searchParam) throws DaoException {
		try {
			return findAllBy(searchParam);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException("Failed to list user");
		}
	}

	@Override
	public UserModal get(long userId) throws DaoException {
		try {
			return findById(userId);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException("Failed to get user");
		}
	}

	@Override
	public UserModal get(SearchParam searchParam) throws DaoException {
		try {
			return findBy(searchParam);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException("Failed to get user");
		}
	}
	
	@Override
	public UserModal getUserByUserName(String userName) throws DaoException {
		try {
			Session session = getCurrentSession();
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<UserModal> query = builder.createQuery(UserModal.class);
			Root<UserModal> root = query.from(UserModal.class);
			query.select(root).where(builder.or(builder.equal(root.get("email"),  userName), builder.equal(root.get("phone"), userName)));
			List<UserModal> users = session.createQuery(query).getResultList();
			return users.size() == 1 ? users.get(0) : null;
		} catch (Exception e) {
			e.printStackTrace();
			throw new DaoException("Failed to get user");
		}
	}
}
