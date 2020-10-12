package com.study.question.dao;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.study.exceptions.DaoException;
import com.study.question.entity.QuestionSolutions;
import com.study.question.entity.QuestionnaireModel;

@Repository
public class DefaultQuestionSolutionsDao extends GenericDAOImpl<QuestionSolutions> implements QuestionSolutionsDao {

	@Override
	public void add(QuestionSolutions image) throws DaoException {
		try {
			saveOrUpdate(image);
		} catch (SQLException e) {
			throw new DaoException(e.getMessage());
		}
	}
	
	@Override
	public void remove(SearchParam searchParam) throws DaoException {
		try {
			String hql = "delete from QuestionSolutions where question = :qid";
			Query query = getCurrentSession().createQuery(hql);
			query.setParameter("qid", ((QuestionnaireModel)searchParam.getSearchMap().get("question")));
			System.out.println(query.executeUpdate());
		} catch (Exception e) {
			e.printStackTrace();
			throw new DaoException(e.getMessage());
		}
	}
	
	@Override
	public List<QuestionSolutions> listSolutionImages(SearchParam searchParam) throws DaoException {
		try {
			return findAllBy(searchParam);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException(e.getMessage());
		}
	}

}
