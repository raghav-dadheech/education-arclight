package com.study.question.dao;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;


public interface GenericDAO<E> {

	void save(E entity) throws Exception;

	void saveOrUpdate(E entity) throws Exception;

	void update(E entity)throws Exception;

	void remove(E entity) throws Exception;

	boolean deleteById(final Serializable id) throws Exception;

	E findById(Serializable id) throws Exception;

	List<E> findAll()throws Exception;

	E findBySomething(final String column, final Object value) throws Exception;

	E findBy(SearchParam searchParam) throws Exception;

	List<E> findAllBy(SearchParam searchParam) throws Exception;

	List<E> findAllWithPagination(final SearchParam searchParam, int pageNo, int pageSize) throws Exception;

	List<E> findAllWithPagination(SearchParam searchParam, int pageNo,
			int pageSize, String orderByColumnName) throws SQLException;

	void merge(E entity) throws SQLException;

	List<E> findAllByWithOrderBy(SearchParam searchParam,
			String orderByColumnName, boolean asc) throws SQLException;
}
