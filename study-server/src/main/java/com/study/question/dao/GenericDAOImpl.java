package com.study.question.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;


public class GenericDAOImpl<E> implements GenericDAO<E> {
	@Autowired
	private SessionFactory sessionFactory;

	private final Class<E> entityClass;

	@SuppressWarnings("unchecked")
	public GenericDAOImpl() {
		this.entityClass = (Class<E>) ((ParameterizedType) this.getClass().getGenericSuperclass())
				.getActualTypeArguments()[0];
	}

	protected Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public void save(final E entity) throws SQLException {
		Session session = null;
		try {
			session = getCurrentSession();
			session.save(entity);
		} catch(final Exception e) {
			throw new SQLException(e.getMessage(), null, e.getCause());
		}
	}

	@Override
	public void saveOrUpdate(final E entity) throws SQLException {
		try {
			getCurrentSession().saveOrUpdate(entity);
		} catch(final Exception e) {
			throw new SQLException(e.getMessage(), null, e.getCause());
		}
	}

	@Override
	public void update(final E entity) throws SQLException {
		try {
			getCurrentSession().update(entity);
		} catch(final Exception e) {
			throw new SQLException(e.getMessage(), null, e.getCause());
		}
	}

	@Override
	public void merge(final E entity) throws SQLException {
		try {
			getCurrentSession().merge(entity);
		} catch(final Exception e) {
			throw new SQLException(e.getMessage(), null, e.getCause());
		}
	}

	@Override
	public void remove(final E entity) throws SQLException {
		try {
			getCurrentSession().delete(entity);
		} catch(final Exception e) {
			throw new SQLException(e.getMessage(), e);
		}
	}

	@Override
	public boolean deleteById(final Serializable id) throws SQLException {
	    try {
	    	final Session session = getCurrentSession();
		    final E e = session.load(this.entityClass, id);
		    if (e != null) {
		        session.delete(e);
		        return true;
		    }
		    return false;
	    } catch(final Exception e) {
			throw new SQLException(e.getMessage(), e);
		}
	}

	@Override
	public E findById(final Serializable id) throws SQLException {
		Session session = null;
		try {
			session = getCurrentSession();
			final E e = session.get(this.entityClass, id);
			return e;
		} catch(final Exception e) {
			throw new SQLException(e.getMessage(), e);
		}
	}

	public E loadById(final Serializable id) throws SQLException {
		Session session = null;
		try {
			session = getCurrentSession();
			final E e = session.load(this.entityClass, id);
			return e;
		} catch(final Exception e) {
			throw new SQLException(e.getMessage(), e);
		}
	}

	@Override
	public E findBySomething(final String column, final Object value) throws SQLException {
		try {
			final CriteriaBuilder builder = getCurrentSession().getCriteriaBuilder();
	        final CriteriaQuery<E> criteria = builder.createQuery(this.entityClass);
	        final Root<E> from = criteria.from(this.entityClass);
	        criteria.select(from);
	        criteria.where(builder.equal(from.get(column), value));

	        final TypedQuery<E> typedQuery = getCurrentSession().createQuery(criteria);

	        final List<E> result = typedQuery.getResultList();
			if(result == null || result.size() == 0) {
				return null;
			}
			return result.get(0);
		} catch(final Exception e) {
			throw new SQLException(e.getMessage(), e);
		}
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public List<E> findAll() throws SQLException {
		try {
			return getCurrentSession().createCriteria(this.entityClass).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
		} catch(final Exception e) {
			throw new SQLException(e.getMessage(), e);
		}
	}

	@Override
	public E findBy(final SearchParam searchParam) throws SQLException {
		try {
			final CriteriaBuilder builder = getCurrentSession().getCriteriaBuilder();
			final CriteriaQuery<E> criteriaQuery = builder.createQuery(this.entityClass);
			final Root<E> root = criteriaQuery.from(this.entityClass);

			final List<Predicate> restrictions = new ArrayList<>();
			if(searchParam != null) {
				searchParam.getSearchMap().forEach((key, value) ->{
					restrictions.add(builder.equal(root.get(key), value));
				});
			}
			criteriaQuery.where(builder.and(restrictions.toArray(new Predicate[restrictions.size()])));
			final CriteriaQuery<E> select = criteriaQuery.select(root);
			final TypedQuery<E> typedQuery = getCurrentSession().createQuery(select);
			final List<E> result = typedQuery.getResultList();
			if(result == null || result.size() == 0) {
				return null;
			}
			return result.get(0);
		} catch (final Exception e) {
			throw new SQLException(e.getMessage(), e);
		}
	}

	@Override
	public List<E> findAllBy(final SearchParam searchParam) throws SQLException {
		try {
			final CriteriaBuilder builder = getCurrentSession().getCriteriaBuilder();
			final CriteriaQuery<E> criteriaQuery = builder.createQuery(this.entityClass);
			final Root<E> root = criteriaQuery.from(this.entityClass);

			final List<Predicate> restrictions = new ArrayList<>();
			if(searchParam != null) {
				searchParam.getSearchMap().forEach((key, value) ->{
					if(value instanceof List<?>)
					{
						restrictions.add(root.get(key).in(value));
						System.out.println(key);
						System.out.println(value);
					}
					else
						restrictions.add(builder.equal(root.get(key), value));
				});
			}
			criteriaQuery.where(builder.and(restrictions.toArray(new Predicate[restrictions.size()])));
			final CriteriaQuery<E> select = criteriaQuery.select(root);
			final TypedQuery<E> typedQuery = getCurrentSession().createQuery(select);
			final List<E> result = typedQuery.getResultList();
			return result;
		} catch (final Exception e) {
			throw new SQLException(e.getMessage(), e);
		}
	}

	@Override
	public List<E> findAllByWithOrderBy(final SearchParam searchParam, final String orderByColumnName, final boolean asc) throws SQLException {
		try {
			final CriteriaBuilder builder = getCurrentSession().getCriteriaBuilder();
			final CriteriaQuery<E> criteriaQuery = builder.createQuery(this.entityClass);
			final Root<E> root = criteriaQuery.from(this.entityClass);

			final List<Predicate> restrictions = new ArrayList<>();
			if(searchParam != null) {
				searchParam.getSearchMap().forEach((key, value) ->{
					restrictions.add(builder.equal(root.get(key), value));
				});
			}
			criteriaQuery.where(builder.and(restrictions.toArray(new Predicate[restrictions.size()])));
			if(StringUtils.isNotBlank(orderByColumnName)) {
				if(asc) {
					criteriaQuery.orderBy(builder.asc(root.get(orderByColumnName)));
				} else {
					criteriaQuery.orderBy(builder.desc(root.get(orderByColumnName)));
				}
			}
			final CriteriaQuery<E> select = criteriaQuery.select(root);
			final TypedQuery<E> typedQuery = getCurrentSession().createQuery(select);
			final List<E> result = typedQuery.getResultList();
			return result;
		} catch (final Exception e) {
			throw new SQLException(e.getMessage(), e);
		}
	}

	@Override
	public List<E> findAllWithPagination(final SearchParam searchParam, final int pageNo, final int pageSize) throws SQLException {
		try {
			final CriteriaBuilder builder = getCurrentSession().getCriteriaBuilder();
			final CriteriaQuery<E> criteriaQuery = builder.createQuery(this.entityClass);

			final Root<E> root = criteriaQuery.from(this.entityClass);

			final List<Predicate> restrictions = new ArrayList<>();
			if(searchParam != null) {
				searchParam.getSearchMap().forEach((key, value) ->{
					restrictions.add(builder.equal(root.get(key), value));
				});
			}
			criteriaQuery.where(builder.and(restrictions.toArray(new Predicate[restrictions.size()])));
			final CriteriaQuery<E> select = criteriaQuery.select(root);
			final TypedQuery<E> typedQuery = getCurrentSession().createQuery(select);
			typedQuery.setFirstResult((pageNo-1)*pageSize);
			typedQuery.setMaxResults(pageSize);
			final List<E> result = typedQuery.getResultList();
			return result;
		} catch (final Exception e) {
			throw new SQLException(e.getMessage(), e);
		}
	}

	@Override
	public List<E> findAllWithPagination(final SearchParam searchParam, final int pageNo, final int pageSize, final String orderByColumnName) throws SQLException {
		try {
			final CriteriaBuilder builder = getCurrentSession().getCriteriaBuilder();
			final CriteriaQuery<E> criteriaQuery = builder.createQuery(this.entityClass);

			final Root<E> root = criteriaQuery.from(this.entityClass);

			final List<Predicate> restrictions = new ArrayList<>();
			if(searchParam != null) {
				searchParam.getSearchMap().forEach((key, value) ->{
					restrictions.add(builder.equal(root.get(key), value));
				});
			}
			criteriaQuery.where(builder.and(restrictions.toArray(new Predicate[restrictions.size()])));
			if(StringUtils.isNotBlank(orderByColumnName))
				criteriaQuery.orderBy(builder.asc(root.get(orderByColumnName)));
			final CriteriaQuery<E> select = criteriaQuery.select(root);
			final TypedQuery<E> typedQuery = getCurrentSession().createQuery(select);
			typedQuery.setFirstResult((pageNo-1)*pageSize);
			typedQuery.setMaxResults(pageSize);

			final List<E> result = typedQuery.getResultList();
			return result;
		} catch (final Exception e) {
			throw new SQLException(e.getMessage(), e);
		}
	}
}


